package adventofcode18

import io.vavr.kotlin.hashSet
import io.vavr.kotlin.list
import io.vavr.kotlin.toVavrList
import io.vavr.collection.List as VList
import io.vavr.collection.Set as VSet

fun freqencyAdjust(input: String): Int  {
    val lines = input.split("\n")
    return lines.map{it.toInt() }.sum()

}

fun freqFirstRepeat(input: String): Int {
    val lines = input.split("\n")
    val intInput = lines.map{it.toInt() }

    return firstRepeat(0, hashSetOf(), intInput, intInput)
}

fun freqFirstRepeatVavr(input: String): Int {
    val lines = input.split("\n")
    val intInput = lines.map{it.toInt() }

    return firstRepeatVavr(0, hashSet(), intInput.toVavrList(), intInput.toVavrList())
}


tailrec fun firstRepeat(currentFreq: Int, seen: Set<Int>, input: List<Int>, complete: List<Int>): Int {
    return if (currentFreq in seen) {
        currentFreq
    } else {
        //println("size: ${seen.size}")
        val newInput = if (input.isEmpty()) complete else input
        val newFreq = currentFreq + newInput.first()
        firstRepeat(newFreq, seen.plus(currentFreq), newInput.drop(1), complete  )
    }
}

tailrec fun firstRepeatVavr(currentFreq: Int, seen: VSet<Int>, input: VList<Int>, complete: VList<Int>): Int {
    return if (currentFreq in seen) {
        currentFreq
    } else {
        //println("size: ${seen.length()}")
        val newInput = if (input.isEmpty()) complete else input
        val newFreq = currentFreq + newInput.first()
        firstRepeatVavr(newFreq, seen.add(currentFreq), newInput.tail(), complete  )
    }
}