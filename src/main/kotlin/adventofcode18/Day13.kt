package adventofcode18

import adventofcode18.Day13.Connection.*
import adventofcode18.Day13.Turn.LEFT_TURN
import io.vavr.Tuple2
import io.vavr.collection.Set
import io.vavr.collection.SortedSet
import io.vavr.collection.TreeSet
import io.vavr.kotlin.hashSet
import io.vavr.kotlin.tuple

import kotlin.collections.map

object Day13 {

    enum class Turn {
        LEFT_TURN, NO_TURN, RIGHT_TURN;

        fun nextTurn(): Turn = when (this) {
            LEFT_TURN -> NO_TURN
            NO_TURN -> RIGHT_TURN
            RIGHT_TURN -> LEFT_TURN
        }

        fun turn(current: Connection): Connection {
            return when {
                this ==  NO_TURN -> current
                this == LEFT_TURN -> when  (current) {

                    LEFT -> DOWN
                    RIGHT -> UP
                    UP -> LEFT
                    DOWN -> RIGHT
                }
                else -> when (current) {

                    LEFT -> UP
                    RIGHT -> DOWN
                    UP -> RIGHT
                    DOWN -> LEFT
                }
            }
        }
    }

    enum class Connection {
        LEFT, RIGHT, UP(), DOWN();

        fun opposite(): Connection{
            return when (this) {
                LEFT -> RIGHT
                RIGHT -> LEFT
                UP -> DOWN
                DOWN -> UP
            }
        }
    }

    data class Position(val x: Int, val y: Int) : Comparable<Position>{

        fun move(connection: Connection): Position {
            return when (connection) {
                LEFT -> Position(x -1, y)
                RIGHT -> Position(x + 1, y)
                UP -> Position(x, y - 1)
                DOWN -> Position(x, y + 1)
            }
        }

        override fun compareTo(other: Position): Int {
            return COMPARATOR.compare(this, other)
        }
        companion object {
            private val COMPARATOR =
                    Comparator.comparingInt<Position> { it.y }
                            .thenComparingInt { it.x }
        }

    }

    data class TrackCell(val pos: Position, val connections: Set<Connection>) {
        fun isCrossRoad() = this.connections.length()  == 4
    }

    data class Cart(val pos: Position,
                    val direction: Connection,
                    val nextTurn: Turn = LEFT_TURN,
                    val collided: Boolean = false,
                    val id: String) : Comparable<Cart> {

        override fun compareTo(other: Cart): Int {
            return COMPARATOR.compare(this, other)
        }
        companion object {
            private val COMPARATOR =
                    Comparator.comparing<Cart, Position> { it.pos }
                            .thenComparing { c -> c.direction }
                            .thenComparing { c -> c.collided}
                            .thenComparing { c -> c.nextTurn}
        }
    }

    fun firstCrashLocation(trackInput: String): String {
        val (cells, carts) = parseField(trackInput)
        val cellMap = cells.map { it.pos to it }.toMap()
        val cartMovements = generateSequence(tuple(1, carts)) { moveCarts(it, cellMap) }
        val collisionConfig = cartMovements
                .map { it to detectCollision(it._2) }
                .dropWhile { it.second == null }.first()
        val collisionPosition = collisionConfig.second
        return "${collisionPosition?.x},${collisionPosition?.y}"
    }

    private fun detectCollision(carts: SortedSet<Cart>): Position? {
        return carts.filter {it.collided}.map { it.pos }.firstOrNull()
    }

    private fun moveCarts(current: Tuple2<Int, SortedSet<Cart>>, cells: Map<Position, TrackCell>): Tuple2<Int, SortedSet<Cart>> {

        val newCarts = current._2.fold(current._2) { acc, cart ->
            if (acc.exists() { it.id == cart.id && it.collided }) acc else {
                val removeCurrent = acc.filter { it.id != cart.id }
                val movedCart = moveCart(cart, cells, removeCurrent.filter{!it.collided}.map{it.pos})
                val updated = if (movedCart.collided) {
                    println("Collision in gen ${current._1} as pos ${movedCart.pos} ")
                    val collided = removeCurrent.find { it.pos == movedCart.pos && !it.collided }.get()
                    val updatedColided = collided.copy(collided = true)
                    removeCurrent.remove(collided).add(updatedColided)
                } else {
                    removeCurrent
                }.add (movedCart)
                updated
            }

        }
        if (newCarts.filter { !it.collided }.size().rem(2) == 0){
            println("Illegal count in gen ${current._1}")
        }
        return tuple(current._1 + 1, newCarts)
    }

    private fun moveCart(cart: Cart, cells: Map<Position, TrackCell>, otherCarts: Set<Position>): Cart {
        return if (cart.collided ) cart else {
            val newPos = cart.pos.move(cart.direction)
            val newCell = cells[newPos]
            val newDirection = moveOnCellNewDirection(cart.direction, newCell!!)
            val isCollided = otherCarts.any { it == newPos}
            val newCart = if (newCell.isCrossRoad()) Cart(newPos, cart.nextTurn.turn(newDirection), cart.nextTurn.nextTurn(), isCollided, cart.id)
                else Cart(newPos, newDirection, cart.nextTurn, isCollided, cart.id)
            newCart
        }
    }

    private fun moveOnCellNewDirection(incoming: Connection, cell: TrackCell): Connection {
        val opp  = incoming.opposite()
        val remaining = cell.connections  - opp
        return if (cell.isCrossRoad()) incoming else remaining.first()
    }

    private fun parseField(input: String): Pair<List<TrackCell>, SortedSet<Cart>>  {
        val linesWithIndex = input.lines().withIndex()
        val positionsWithChars = linesWithIndex.flatMap { lineWithIndex ->
            val line = " " + lineWithIndex.value
            line.withIndex().map { charWithIndex ->
                val position = Position(charWithIndex.index - 1, lineWithIndex.index)
                Pair(position, charWithIndex.value)
            }
        }
        val cellsWithCarts = positionsWithChars.zipWithNext { a, b ->
            parseCell(b.second, b.first, a.second)
        }.filter { it.first.connections.nonEmpty() }
        val carts = TreeSet.ofAll(cellsWithCarts.map { it.second }.filterNotNull())
        val cells = cellsWithCarts.map {it.first}

        return cells to carts

    }

    private fun parseCell(c: Char, pos: Position, leftChar: Char): Pair<TrackCell, Cart?>{
        return when (c) {
            '-' -> TrackCell(pos, hashSet(LEFT, RIGHT)) to null
            '|' -> TrackCell(pos, hashSet(UP, DOWN)) to null
            '+' -> TrackCell(pos, hashSet(LEFT, RIGHT, UP, DOWN)) to null
            '/' -> if (leftIsConnected(leftChar)) TrackCell(pos, hashSet(UP, LEFT)) to null
                        else TrackCell(pos, hashSet(RIGHT, DOWN)) to null
            '\\' -> if (leftIsConnected(leftChar) ) TrackCell(pos, hashSet(DOWN, LEFT)) to null
                        else TrackCell(pos, hashSet(UP, RIGHT)) to null
            '>' -> TrackCell(pos, hashSet(LEFT, RIGHT)) to Cart(pos, RIGHT, id = "$c:${pos.x},${pos.y}")
            '<' -> TrackCell(pos, hashSet(LEFT, RIGHT)) to Cart(pos, LEFT, id = "$c:${pos.x},${pos.y}")
            '^' -> TrackCell(pos, hashSet(UP, DOWN)) to Cart(pos, UP, id = "$c:${pos.x},${pos.y}")
            'v' -> TrackCell(pos, hashSet(UP, DOWN)) to Cart(pos, DOWN, id = "$c:${pos.x},${pos.y}")
            else -> TrackCell(pos, hashSet()) to null
        }
    }



    private fun leftIsConnected(left: Char): Boolean {
        return when (left) {
            '-' -> true
            '+' -> true
            '>' -> true
            else -> false
        }
    }

    fun lastSurvivor(input: String): String {
        val (cells, carts) = parseField(input)
        val cellMap = cells.map { it.pos to it }.toMap()
        val cartMovements = generateSequence(tuple(1, carts)) { moveCarts(it, cellMap) }
        val finalCartConfig = cartMovements.dropWhile { !hasLoneSurvivor(it._2)}.first()
        val survivor = finalCartConfig._2.filter { !it.collided }.firstOrNull()
        println("${survivor?.pos?.x},${survivor?.pos?.y}:${finalCartConfig._1}:$survivor:${cellMap[survivor!!.pos]}")
        return "${survivor.pos.x},${survivor.pos.y}"
    }

    private fun hasLoneSurvivor(carts: Iterable<Cart>): Boolean {
        return carts.filter { c -> !c.collided }.size == 1
    }
}