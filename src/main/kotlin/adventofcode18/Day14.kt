@file:Suppress("UNUSED_PARAMETER")

package adventofcode18

import io.vavr.Tuple3
import io.vavr.collection.Stream
import io.vavr.collection.Vector
import io.vavr.kotlin.tuple

object Day14 {

    fun recipesAfter(generations: Int): String {
        val initial = "37"
        val intStream = Stream.range(1, generations * 2 + 10)
        val reduced = intStream.fold(tuple(initial, 0, 1)){acc, i -> iterate(acc._1, acc._2, acc._3)}
        return reduced._1.drop(generations).take(10)
    }

    private fun iterate(current: String, elf1: Int, elf2: Int): Tuple3<String, Int, Int> {
        val elf1Current = current[elf1].toString().toInt()
        val elf2Current = current[elf2].toString().toInt()
        val newSuffix = (elf1Current + elf2Current).toString()
        val newComplete = current + newSuffix
        val elf1NewIndex = modulo(elf1 + elf1Current + 1, newComplete.length)
        val elf2NewIndex = modulo(elf2 + elf2Current + 1, newComplete.length)
        return tuple(newComplete, elf1NewIndex, elf2NewIndex)
    }


    private fun modulo(a: Int, b: Int): Int {
        val r = a.rem(b)
        return if (r<0) r + b else r
    }


    data class Gen(val recipes: Vector<Byte>, val elf1: Int, val elf2: Int)

    private fun vectorize(xs: String): Vector<Byte> = Vector.ofAll(xs.map{it.toString().toByte()})

    fun recipesFoundFor(pattern: String): Int {
        val firstGen = Gen(vectorize("37"), 0, 1)
        val size = pattern.length
        val result = Stream.from(1).scanLeft(firstGen, ::iterateGen)
                .map { gen ->
                    gen.recipes.size() to gen.recipes.takeRight(size + 1).mkString("").indexOf(pattern)
                }
                .find { it.second >= 0 }
                .map { it.first - size + it.second -1 }

        return result.get()
    }



    private fun iterateGen(current: Gen, genCount: Int): Gen {
        val (recipes, elf1, elf2) = current
        val elf1Current = recipes[elf1].toString().toByte()
        val elf2Current = recipes[elf2].toString().toByte()
        val newComplete = recipes.appendAll(vectorize((elf1Current + elf2Current).toString()))
        val elf1NewIndex = modulo(elf1 + elf1Current + 1, newComplete.size())
        val elf2NewIndex = modulo(elf2 + elf2Current + 1, newComplete.size())
        return Gen(newComplete, elf1NewIndex, elf2NewIndex)
    }

}