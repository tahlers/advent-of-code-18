package adventofcode18

import io.vavr.collection.Map
import io.vavr.collection.Stream.range
import io.vavr.collection.Stream.rangeClosed
import io.vavr.collection.Vector
import io.vavr.kotlin.component1
import io.vavr.kotlin.component2
import io.vavr.kotlin.hashMap
import io.vavr.kotlin.list

object Day9 {

    data class Game(val field: Vector<Int>, val currentIndex: Int, val playerScores: Map<Int, Int> )

    data class GameZipper(val field: CircularZipper<Int>, val playerScores: Map<Int, Long>)

    fun gameScore(playerCount: Int, maxMarbleNumber: Int): Int {
        val marbleStream = rangeClosed(0, maxMarbleNumber).zip(range(0, playerCount).cycle()).toList().tail()
        val initialGame = Game(Vector.of(0), 0, hashMap())
        val result = marbleStream.fold(initialGame) { game, marbleAndPlayer ->
            if (marbleAndPlayer._1.rem(23) != 0) {
                val newIndex = modulo(game.currentIndex + 2, game.field.length())
                val newField = game.field.insert(newIndex + 1, marbleAndPlayer._1)
                Game(newField, newIndex, game.playerScores)
            } else {
                val removeIndex = modulo(game.currentIndex - 7, game.field.length())
                val scoreInc = marbleAndPlayer._1 + game.field[removeIndex+1]
                val newField = game.field.removeAt(removeIndex + 1 )
                val newScore = game.playerScores.getOrElse(marbleAndPlayer._2, 0) + scoreInc
                val newScoreMap = game.playerScores.put(marbleAndPlayer._2, newScore)
                Game(newField, removeIndex , newScoreMap)
            }
        }
        return result.playerScores.values().max().getOrElse(0)
    }

    fun modulo(a: Int, b: Int): Int {
        val r = a.rem(b)
        return if (r<0) r + b else r
    }

    fun gameScoreCircularZipper(playerCount: Int, maxMarbleNumber: Int): Long {
        val marbleStream = rangeClosed(0, maxMarbleNumber).zip(range(0, playerCount).cycle()).toList().tail()
        val initialGame = GameZipper(CircularZipper(list(), 0, list()), hashMap())
        val result = marbleStream.fold(initialGame) { game, marbleAndPlayer ->
            val (marble, player) = marbleAndPlayer
            if (marble.rem(23) != 0) {
                val newField = game.field.rotate(2).inserted(marble)
                GameZipper(newField, game.playerScores)
            } else {
                val (removed, newField) = game.field.rotate(-7).removed()
                val newScore = game.playerScores.getOrElse(player, 0) + marble + removed
                val newScoreMap = game.playerScores.put(player, newScore)
                GameZipper(newField, newScoreMap)
            }
        }
        return result.playerScores.values().max().getOrElse(0)
    }

}