
package adventofcode18

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals


class Day9Test {

    @Test
    fun testScore(){
        assertEquals(32, Day9.gameScore(9, 25))
    }

    @Test
    fun testOtherScoreExamples(){
        assertEquals(8317, Day9.gameScore(10, 1618))
        assertEquals(146373, Day9.gameScore(13, 7999))
        assertEquals(2764, Day9.gameScore(17, 1104))
        assertEquals(54718, Day9.gameScore(21, 6111))
        assertEquals(37305, Day9.gameScore(30, 5807))
    }

    @Test
    fun testScoreReal(){
        assertEquals(422980, Day9.gameScore(405, 70953))
    }

    @Test
    @Ignore //does not complete in sensible time
    fun testScoreReal2(){
        assertEquals(422980, Day9.gameScore(405, 7095300))
    }

    @Test
    fun testScoreZipper(){
        assertEquals(32, Day9.gameScoreCircularZipper(9, 25))
    }

    @Test
    fun testOtherScoreExamplesZipper(){
        assertEquals(2764, Day9.gameScoreCircularZipper(17, 1104))
        assertEquals(8317, Day9.gameScoreCircularZipper(10, 1618))
        assertEquals(146373, Day9.gameScoreCircularZipper(13, 7999))
        assertEquals(54718, Day9.gameScoreCircularZipper(21, 6111))
        assertEquals(37305, Day9.gameScoreCircularZipper(30, 5807))
    }

    @Test
    fun testScoreRealZipper(){
        assertEquals(422980, Day9.gameScoreCircularZipper(405, 70953))
    }

    @Test
    fun testScoreReal2Zipper(){
        assertEquals(3552041936, Day9.gameScoreCircularZipper(405, 7095300))
    }

}
