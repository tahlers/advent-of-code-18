package adventofcode18

import io.vavr.Function4
import io.vavr.kotlin.tuple
import kotlin.math.min

object Day11 {


    data class Cell(val x: Int, val y: Int, val serial: Int) {
        fun power(): Int {
            val rackId = x + 10
            val powerLevelStart = rackId * y
            val powerGrid = powerLevelStart + serial
            val multiplyByRackId = powerGrid * rackId
            val hundreds = if (multiplyByRackId < 100) 0 else multiplyByRackId
                    .toString().takeLast(3).first()
                    .toString().toInt()
            return hundreds - 5
        }
    }

    fun gridMax(gridSerial: Int): String {
        val maxPowerSquare = (0..297).flatMap { y ->
            (0..297).map { x ->
                tuple(x, y, squarePowerLevel(x, y, 3, gridSerial))
            }
        }.maxBy { it._3 }
        return "${maxPowerSquare!!._1},${maxPowerSquare._2}"
    }

    private fun squarePowerLevel(x: Int, y: Int, size: Int, serial: Int): Int {
        val smaller = if (size==0) 0 else memoizedSquarePowerLevel.apply(x, y, size-1, serial)
        val cellsX = (y..(y + size - 2)).map{loopY -> Cell(x+size-1, loopY, serial).power()}.sum()
        val cellsY = (x..(x + size - 1)).map { loopY -> Cell(loopY, y + size -1, serial).power()}.sum()
        return smaller + cellsX + cellsY
    }

    private val memoizedSquarePowerLevel: Function4<Int, Int, Int, Int, Int> =
            Function4.of { x: Int, y: Int, size: Int, serial: Int -> squarePowerLevel(x, y, size, serial) }
                    .memoized()

    fun gridMaxVarSize(gridSerial: Int): String {
        val maxPowerSquare = (0..297).flatMap { y ->
            (0..297).flatMap { x ->
                val maxSize = min(300 - x, 300 - y)
                //println("calc $x,$y with max size $maxSize!")
                (1..maxSize).map { size ->
                    tuple(x, y, size, squarePowerLevel(x, y, size, gridSerial))

                }
            }
        }.maxBy { it._4 }
        return "${maxPowerSquare!!._1},${maxPowerSquare._2},${maxPowerSquare._3}"
    }


}