package adventofcode18

import kotlin.math.abs

object Day6 {

    data class Coord(val id: Int, val x: Int, val y: Int, val isInner: Boolean)

    data class Cell(val x: Int, val y: Int, val nearest: String)

    fun largestField(input: String): Int {
        val grid = prepareGrid(input, ::nearest)
        val filtered = grid.filter { it.nearest != "-" }
        val countMap = filtered.groupingBy { it.nearest }.eachCount()
        return countMap.values.max()!!
    }


    fun allCoordField(input: String, threshold: Int): Int {
        val grid = prepareGrid(input){x, y, coords -> allDistancesBelow(x, y, threshold, coords)}
        val filtered = grid.filter { it.nearest != "-" }
        return filtered.size
    }

    private fun prepareGrid(input: String, transform: (Int, Int, HashSet<Coord>) -> Cell): List<Cell> {
        val pairs = input.lines().map {
            val (x, y) = it.split(", ")
            Pair(x.toInt(), y.toInt())
        }
        val minX = pairs.minBy { it.first }!!.first
        val minY = pairs.minBy { it.second }!!.second
        val maxX = pairs.maxBy { it.first }!!.first
        val maxY = pairs.maxBy { it.second }!!.second

        val coords = pairs.mapIndexed { i, coord ->
            val isInner = coord.first == minX || coord.first == maxX || coord.second == minY || coord.second == maxY
            Coord(i, coord.first - minX, coord.second - minY, isInner)
        }.toHashSet()

        val grid = (0..(maxX - minX)).map { x ->
            (0..(maxY - minY)).map { y ->
                transform(x, y, coords)
            }
        }.flatten()
        return grid
    }


    private fun nearest(x: Int, y: Int, coords: HashSet<Coord>): Cell {
        val sorted = coords.map { Pair(it, distance(x, y, it.x, it.y)) }
                .sortedBy { it.second }
        val first = sorted.first()
        val isLegit = !sorted.first().first.isInner && sorted[0].second != sorted[1].second
        return if (isLegit) Cell(x, y, first.first.id.toString()) else Cell(x, y, "-")
    }

    private fun allDistancesBelow(x: Int, y: Int, threshold: Int, coords:HashSet<Coord>): Cell {
        val allDistances = coords.map { distance(x, y, it.x, it.y) }.sum()
        return if (allDistances < threshold) Cell(x, y, "#") else Cell(x, y, "-")
    }

    private fun distance(x1: Int, y1: Int, x2: Int, y2: Int): Int{
        return abs(x1 - x2) + abs(y1 - y2)
    }


}