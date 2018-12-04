package adventofcode18

import io.vavr.Tuple2
import io.vavr.collection.CharSeq
import java.lang.Integer.max

import io.vavr.collection.List
import io.vavr.collection.Map
import io.vavr.control.Option
import io.vavr.kotlin.list
import io.vavr.kotlin.toVavrList
import io.vavr.kotlin.tuple
import java.time.LocalDate

object Day4 {

    data class Shift(val guard: Int, val date: LocalDate,  val minutes: List<MinuteState>)

    data class MinuteState(val min: Int, val state: Boolean)

    fun findGuardStrategy1(input: String): Int {
        val shifts = parseLines(input)
        val shiftsByGuard: Map<Int, List<Shift>>? = shifts.groupBy { it.guard }
        val maxMinuteByGuard = shiftsByGuard!!.map{ entry ->
            val guard = entry._1
            //val minutesLists: List<MinuteState> = entry._2.flatMap { it.minutes }
            tuple(guard, maxAsleepMinute(entry._2.map { it.minutes }))
        }
        val maxBy: Option<Tuple2<Int, Tuple2<Int, Int>?>>? = maxMinuteByGuard.maxBy { it -> it._2!!._2 }
        return maxBy!!.map { it._1  * it._2!!._1 }.getOrElse(0)

    }

    fun findGuardStrategy2(input: String): Int {
        val shifts = parseLines(input)
        val shiftsByGuard: Map<Int, List<Shift>>? = shifts.groupBy { it.guard }
        val maxMinuteByGuard = shiftsByGuard!!.map{ entry ->
            val guard = entry._1
            //val minutesLists: List<MinuteState> = entry._2.flatMap { it.minutes }
            tuple(guard, maxAsleepMinute2(entry._2.map { it.minutes }))
        }
        val maxBy: Option<Tuple2<Int, Tuple2<Int, Int>?>>? = maxMinuteByGuard.maxBy { it -> it._2!!._2 }
        return maxBy!!.map { it._1  * it._2!!._1 }.getOrElse(0)

    }



    fun parseLines(input: String): List<Shift> {
        val lines: List<String> = input.split("\n").sorted().toVavrList()

        val slideBy = lines.slideBy { extractDate(it) }.toList()

        val shifts: List<Shift> = slideBy.map { events ->
            val headEvent = events.head()
            val date = extractDate(headEvent)
            val guardId: Int = headEvent.substringAfter("Guard #").substringBefore(" begins").toInt()
            val minutes = extractNext(0, events.tail(), true)
            Shift(guardId, date, minutes)

        }

        return shifts
    }

    fun maxAsleepMinute(minutes: List<List<MinuteState>>): Tuple2<Int, Int>? {
        val size = minutes.size().toDouble()
        val minutesList = minutes.flatten().filter { !it.state }.toVavrList()
        val minutesAsleep = minutesList.size()
        val maxMinute: Option<Tuple2<Int, List<MinuteState>>> = minutesList.groupBy { it.min }.maxBy { it -> it._2.size() }
        val maxTuple: Option<Tuple2<Int, Int>> = maxMinute.map { tuple(it._1, minutesAsleep) }
        return maxTuple.getOrElse(tuple(0, 0))

    }

    fun maxAsleepMinute2(minutes: List<List<MinuteState>>): Tuple2<Int, Int>? {
        val size = minutes.size().toDouble()
        val minutesList = minutes.flatten().filter { !it.state }.toVavrList()
        val minutesAsleep = minutesList.size()
        val maxMinute: Option<Tuple2<Int, List<MinuteState>>> = minutesList.groupBy { it.min }.maxBy { it -> it._2.size() }
        val maxTuple: Option<Tuple2<Int, Int>> = maxMinute.map { tuple(it._1, it._2.size()) }
        return maxTuple.getOrElse(tuple(0, 0))

    }


    fun extractNext(currentMinute: Int, events: List<String>, isCurrentlyAwake: Boolean): List<MinuteState> {
        if (events.isEmpty) {
            return (currentMinute..59).map { MinuteState(it, isCurrentlyAwake) }.toVavrList()
        } else {
            val nextEvent: String = events.head()
            val nextMinute = nextEvent.substring(15, 17).toInt()
            val nextLeg = (currentMinute..(nextMinute-1)).map { MinuteState(it, isCurrentlyAwake) }
            return nextLeg.toVavrList().appendAll(extractNext(nextMinute, events.tail(), !isCurrentlyAwake))
        }

    }

    fun extractDate(line: String): LocalDate {
        val (year, month, day) = line.substring(1, 11).split("-")
        val date = LocalDate.of(year.toInt(), month.toInt(), day.toInt())
        val newDate = if (line.substring(12, 14) == "23") date.plusDays(1) else date
        return newDate

    }

}