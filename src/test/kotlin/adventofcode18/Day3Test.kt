
package adventofcode18

import kotlin.test.Test
import kotlin.test.assertEquals


class Day3Test {

    @Test
    fun testCreatePairs(){
        assertEquals(listOf(5 to 5, 5 to 6, 6 to 5, 6 to 6), Day3.createPairs(Day3.Claim(5, 5, 2, 2, 1)))
    }

    @Test
    fun testParseLine(){
        assertEquals(Day3.Claim(555, 891, 18, 12, 1), Day3.parseLine("#1 @ 555,891: 18x12"))
    }

    @Test
    fun testClaims(){
        val example1 = """
        #1 @ 1,3: 4x4
        #2 @ 3,1: 4x4
        #3 @ 5,5: 2x2
        """.trimIndent()

        assertEquals(4, Day3.claims(example1))
    }


    @Test
    fun testRealSample(){
        val sample = this.javaClass.getResource("/day3_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(109716, Day3.claims(sample))
    }

    @Test
    fun testNonOverlapClaims(){
        val example1 = """
        #1 @ 1,3: 4x4
        #2 @ 3,1: 4x4
        #3 @ 5,5: 2x2
        """.trimIndent()

        assertEquals(setOf(3), Day3.nonOverlappingClaimIds(example1))
    }

    @Test
    fun testRealNonOverlapping(){
        val sample = this.javaClass.getResource("/day3_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(setOf(124), Day3.nonOverlappingClaimIds(sample))
    }

}
