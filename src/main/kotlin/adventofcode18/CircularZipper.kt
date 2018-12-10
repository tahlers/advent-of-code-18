package adventofcode18

import io.vavr.Tuple2
import io.vavr.collection.List
import io.vavr.kotlin.list
import io.vavr.kotlin.tuple

data class CircularZipper<A>(val init: List<A>, val current: A, val tail: List<A>) {

    fun next(): CircularZipper<A> = when (tail) {
        is List.Cons -> CircularZipper(init.prepend(current), tail.head(), tail.tail())
        else -> when (val reversed = init.reverse()) {
            is List.Cons -> CircularZipper(list(current), reversed.head(), reversed.tail())
            else -> this
        }
    }

    fun prev(): CircularZipper<A> = when (init) {
        is List.Cons -> CircularZipper(init.tail(), init.head(), tail.prepend(current))
        else -> when (val tailReversed = tail.reverse()) {
            is List.Cons -> CircularZipper(tailReversed.tail(), tailReversed.head(), list(current))
            else -> this
        }
    }

    fun rotate(n: Int): CircularZipper<A> {
        return when {
            n == 0 -> this
            n > 0 -> next().rotate(n - 1)
            else -> prev().rotate(n + 1)
        }
    }

    fun inserted(elem: A): CircularZipper<A> = CircularZipper(init, elem, tail.prepend(current))

    fun removed(): Tuple2<A, CircularZipper<A>> {
        return when (tail) {
            is List.Cons  -> tuple(current, CircularZipper(init, tail.head(), tail.tail()))
            else -> tuple(current, CircularZipper(init.tail(), init.head(), tail))
        }
    }

    fun display(): String {
        return "[${init.reverse().joinToString()}, *$current*, ${tail.joinToString()}]"
    }
}