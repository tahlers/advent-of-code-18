
package adventofcode18

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull



class Day1Test {

    @Test
    fun testExample1(){
        val example1 = """
        +1
        -2
        +3
        +1
        """.trimIndent()

        assertEquals(3, freqencyAdjust(example1))
    }


    @Test
    fun testExample2(){
        val example2 = """
            +1
            +1
            +1
        """.trimIndent()

        assertEquals(3, freqencyAdjust(example2))
    }

    @Test
    fun testExample3(){
        val example3 = """
            +1
            +1
            -2
        """.trimIndent()

        assertEquals(0, freqencyAdjust(example3))
    }

    @Test
    fun testExample4(){
        val example4 = """
            -1
            -2
            -3
        """.trimIndent()

        assertEquals(-6, freqencyAdjust(example4))
    }

    @Test
    fun testRealSample(){
        val sample = this.javaClass.getResource("/day1_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(411, freqencyAdjust(sample))
    }

    @Test
    fun repeatTest1(){
        val sample = """
            +1
            -1
        """.trimIndent()

        assertEquals(0, freqFirstRepeat(sample))
    }

    @Test
    fun repeatTest2(){
        val sample = """
            +3
            +3
            +4
            -2
            -4
        """.trimIndent()

        assertEquals(10, freqFirstRepeat(sample))
    }

    @Test
    fun repeatTest3(){
        val sample = """
            -6
            +3
            +8
            +5
            -6
        """.trimIndent()

        assertEquals(5, freqFirstRepeat(sample))
    }

    @Test
    fun repeatTest4(){
        val sample = """
            +7
            +7
            -2
            -7
            -4
        """.trimIndent()

        assertEquals(14, freqFirstRepeat(sample))
    }

    @Test
    @Ignore
    fun repeatReal(){
        val sample = this.javaClass.getResource("/day1_1_input.txt").readText(Charsets.UTF_8)

        assertEquals(56360, freqFirstRepeat(sample))
    }

    @Test
    fun repeatRealVavr(){
        val sample = this.javaClass.getResource("/day1_1_input.txt").readText(Charsets.UTF_8)

        assertEquals(56360, freqFirstRepeatVavr(sample))
    }
}
