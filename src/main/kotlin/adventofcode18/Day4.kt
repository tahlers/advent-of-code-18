package adventofcode18

import io.vavr.Tuple3
import io.vavr.collection.List
import io.vavr.collection.Map
import io.vavr.kotlin.toVavrList
import io.vavr.kotlin.tuple
import java.time.LocalDate

object Day4 {

    data class Shift(val guard: Int, val date: LocalDate,  val minutes: List<MinuteState>)

    data class MinuteState(val min: Int, val state: Boolean)

    fun findGuardStrategy(input: String, useStrategy2: Boolean = false): Int {
        val shifts = parseLines(input)
        val shiftsByGuard: Map<Int, List<Shift>>? = shifts.groupBy { it.guard }
        val maxMinuteByGuard = shiftsByGuard!!.map{ entry ->
            val guard = entry._1
            tuple(guard, maxAsleepMinute(entry._2.map { it.minutes }))
        }
        val maxBy = maxMinuteByGuard.maxBy { it ->
            if (useStrategy2) it._2!!._3 else it._2!!._2 }
        return maxBy!!.map { it._1 * it._2!!._1 }.getOrElse(0)

    }


    private fun parseLines(input: String): List<Shift> {
        val lines: List<String> = input.split("\n").sorted().toVavrList()

        val slideBy = lines.slideBy { extractDate(it) }.toList()

        return slideBy.map { events ->
            val headEvent = events.head()
            val date = extractDate(headEvent)
            val guardId: Int = headEvent.substringAfter("Guard #").substringBefore(" begins").toInt()
            val minutes = extractNext(0, events.tail(), true)
            Shift(guardId, date, minutes)

        }
    }

    private fun maxAsleepMinute(minutes: List<List<MinuteState>>): Tuple3<Int, Int, Int>? {
        val minutesList = minutes.flatten().filter { !it.state }.toVavrList()
        val minutesAsleep = minutesList.size()
        val maxMinute = minutesList.groupBy { it.min }.maxBy { it -> it._2.size() }
        val maxTuple = maxMinute.map { tuple(it._1, minutesAsleep, it._2.size()) }
        return maxTuple.getOrElse(tuple(0, 0, 0))

    }



    private fun extractNext(currentMinute: Int, events: List<String>, isCurrentlyAwake: Boolean): List<MinuteState> {
        return if (events.isEmpty) {
            (currentMinute..59).map { MinuteState(it, isCurrentlyAwake) }.toVavrList()
        } else {
            val nextEvent: String = events.head()
            val nextMinute = nextEvent.substring(15, 17).toInt()
            val nextLeg = (currentMinute..(nextMinute-1)).map { MinuteState(it, isCurrentlyAwake) }
            nextLeg.toVavrList().appendAll(extractNext(nextMinute, events.tail(), !isCurrentlyAwake))
        }

    }

    fun extractDate(line: String): LocalDate {
        val (year, month, day) = line.substring(1, 11).split("-")
        val date = LocalDate.of(year.toInt(), month.toInt(), day.toInt())
        return if (line.substring(12, 14) == "23") date.plusDays(1) else date

    }

}