
package adventofcode18

import kotlin.test.Test
import kotlin.test.assertEquals


class Day6Test {


    @Test
    fun testLargestField(){
        val sample = """
            1, 1
            1, 6
            8, 3
            3, 4
            5, 5
            8, 9
        """.trimIndent()
        assertEquals(17, Day6.largestField(sample))
    }

    @Test
    fun testLargestFieldReal(){
        val sample = this.javaClass.getResource("/day6_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(4143, Day6.largestField(sample))
    }

    @Test
    fun testallCoordField(){
        val sample = """
            1, 1
            1, 6
            8, 3
            3, 4
            5, 5
            8, 9
        """.trimIndent()
        assertEquals(16, Day6.allCoordField(sample, 32))
    }

    @Test
    fun testallCoordFieldReal(){
        val sample = this.javaClass.getResource("/day6_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(35039, Day6.allCoordField(sample, 10000))
    }

}
