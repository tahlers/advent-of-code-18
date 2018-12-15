@file:Suppress("UNUSED_PARAMETER")

package adventofcode18

import io.vavr.collection.Stream
import io.vavr.collection.Vector

object Day14 {

    data class Gen(val recipes: Vector<Byte>, val elf1: Int, val elf2: Int)

    private fun vectorized(xs: String): Vector<Byte> = Vector.ofAll(xs.map{it.toString().toByte()})

    private val firstGen = Gen(vectorized("37"), 0, 1)

    fun recipesAfter(generations: Int): String {
        val intStream = Stream.range(1, generations * 2 + 10)
        val reduced = intStream.fold(firstGen, ::iterateGen)
        return reduced.recipes.drop(generations).mkString("").take(10)
    }


    private fun modulo(a: Int, b: Int): Int {
        val r = a.rem(b)
        return if (r<0) r + b else r
    }



    fun recipesFoundFor(pattern: String): Int {
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
        val newComplete = recipes.appendAll(vectorized((elf1Current + elf2Current).toString()))
        val elf1NewIndex = modulo(elf1 + elf1Current + 1, newComplete.size())
        val elf2NewIndex = modulo(elf2 + elf2Current + 1, newComplete.size())
        return Gen(newComplete, elf1NewIndex, elf2NewIndex)
    }

}