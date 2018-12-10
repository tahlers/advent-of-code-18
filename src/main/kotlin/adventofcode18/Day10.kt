package adventofcode18

import io.vavr.collection.Set
import io.vavr.kotlin.toVavrList
import io.vavr.kotlin.tuple
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.component3
import kotlin.collections.component4
import kotlin.collections.fold
import kotlin.collections.forEach
import kotlin.collections.joinToString
import kotlin.collections.map
import kotlin.collections.minBy

object Day10 {

    data class Point(val x: Int, val y: Int, val dx: Int, val dy: Int)

    @JvmStatic
    fun main(args: Array<String>) {
        val exampleField = loadField("/day10_2_input.txt")
        printField(elapseTime(exampleField, 3), box(exampleField, 3))
        val realField = loadField("/day10_1_input.txt")

        val minSecAndBox = (10000..11000)
                .map{ tuple(it, box(realField, it))}
                .minBy { it._2.dx - it._2.x + (it._2.dy - it._2.y) }

        println("minimal bounding box at ${minSecAndBox?._1} seconds.\n\n")
        printField(elapseTime(realField, minSecAndBox!!._1), minSecAndBox._2)
    }

    fun loadField(fileName: String): Set<Point> {
        val input = this.javaClass.getResource(fileName).readText(Charsets.UTF_8)
        return input.lines().map {
            val (x, y, dx, dy) = """-?\d+""".toRegex().findAll(it).map { it.value }.toList()
            Point(x.toInt(), y.toInt(), dx.toInt(), dy.toInt())
        }.toVavrList().toSet()

    }

    fun elapseTime(field: Set<Point>, seconds: Int): Set<Point> {
        return field.map { p ->
            Point(p.x + seconds * p.dx,
                    p.y + seconds * p.dy,
                    p.dx,
                    p.dy)
        }
    }

    fun box(points: Set<Point>, seconds: Int): Point {
        val minX = points.map { p -> p.x + seconds * p.dx }.min().get()
        val maxX = points.map { p -> p.x + seconds * p.dx }.max().get()
        val minY = points.map { p -> p.y + seconds * p.dy }.min().get()
        val maxY = points.map { p -> p.y + seconds * p.dy }.max().get()
        return Point(minX, minY, maxX, maxY)

    }

    fun printField(field: Set<Point>, box: Point) {
        val fieldMap = (0..box.dy - box.y).map { y ->
            (0..box.dx - box.x).map { x ->
                ' '
            }.toVavrList().toVector()
        }.toVavrList().toVector()
        val newFieldMap = field.fold(fieldMap) { acc, p ->
            val line = acc[p.y - box.y]
            val newLine = line.update(p.x - box.x, '*')
            acc.update(p.y - box.y, newLine)
        }

        newFieldMap.forEach { lineChars -> println(lineChars.joinToString(separator = "")) }

    }


}