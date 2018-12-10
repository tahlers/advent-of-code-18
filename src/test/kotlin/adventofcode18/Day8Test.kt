
package adventofcode18

import kotlin.test.Test
import kotlin.test.assertEquals


class Day8Test {

    @Test
    fun testSum(){
        val sample = """
            2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2
        """.trimIndent()
        assertEquals(138, Day8.metadataSum(sample))
    }

    @Test
    fun testSumReal(){
        val sample = this.javaClass.getResource("/day8_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(41028, Day8.metadataSum(sample))
    }

    @Test
    fun testVal(){
        val sample = """
            2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2
        """.trimIndent()
        assertEquals(66, Day8.value(sample))
    }

    @Test
    fun testValueReal(){
        val sample = this.javaClass.getResource("/day8_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(20849, Day8.value(sample))
    }

}
