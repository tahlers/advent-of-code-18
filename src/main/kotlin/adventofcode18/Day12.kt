package adventofcode18

import kotlin.collections.fold
import kotlin.collections.joinToString
import kotlin.collections.map

object Day12 {

    fun potCalc(input: String, generations:Int): Int {
        val (initial, rules) = parseInput(input)
        val endConfiguration = nextNGen(generations, initial, rules)
        return endConfiguration.second
    }

    fun parseInput(input: String): Pair<String, Map<String, Char>>{
        val lines = input.lines()
        val initialField = lines[0].substringAfter("initial state: ")
        val rules = lines.drop(2).map {
            it.substringBefore(" => ").trim() to it.substringAfter(" => ").trim().single()

        }.toMap()
        return Pair(initialField, rules)
    }

    private fun nextNGen(generations: Int, initial: String, rules: Map<String, Char>): Pair<String, Int> {
        val offset = generations * 2 + 4
        val startField = ".".repeat(offset) + initial + ".".repeat(offset)
        val endConfiguration = (1..generations)
                .fold(startField to plantIndexSum(startField, offset)) { acc, gen ->
                    nextGen(acc.first, rules, offset)
                }
        return endConfiguration
    }

    fun nextGen(current: String, rules: Map<String, Char>, offset: Int): Pair<String, Int> {
        val next = current.take(2) + current.withIndex().drop(2).dropLast(2).map { (index, _) ->
            val sample = current.substring(index - 2, index + 3)
            rules.getOrDefault(sample, '.')
        }.joinToString(separator = "") + current.takeLast(2)
        return Pair(next, plantIndexSum(next, offset ))
    }

    fun plantIndexSum(field: String, offset: Int): Int{
        val indexes = field.withIndex().filter { it.value == '#' }.map { it.index - offset }
        return indexes.sum()
    }

    /**
     * Idea: Find stable delta increase within the first 200 generations and interpolate from there.
     */
    fun potCalcBig(input: String, generations:Long): Long{
        val (initial, rules) = parseInput(input)
        val lazyGenerations: Sequence<Pair<String, Int>> = (1..200).map { nextNGen(it, initial, rules) }.asSequence()
        val sizeInc = lazyGenerations.mapIndexed { index, pair -> Pair(index, pair.second) }
        val stableIndicatorSeq = sizeInc.zip(sizeInc.windowed(10, 1)
                .map { window  ->
                    val scores = window.map { it.second }
                    val change = scores.zipWithNext { a, b -> b - a  }
                    Pair(change.first(), change.distinct().size == 1)})
        val findFirstStable = stableIndicatorSeq.first { it.second.second }
        val firstStableGeneration = findFirstStable.first.first + 1
        val firstStableScore = findFirstStable.first.second
        val stableDelta = findFirstStable.second.first
        println("found stable conf after $firstStableGeneration generations with score $firstStableScore (delta $stableDelta)!")
        return (generations-firstStableGeneration) * stableDelta + firstStableScore
    }

}