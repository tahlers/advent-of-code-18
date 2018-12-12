
package adventofcode18

import kotlin.test.Test
import kotlin.test.assertEquals


class Day12Test {

    @Test
    fun calcPotsTest(){
        val sample = """initial state: #..#.#..##......###...###

            ...## => #
            ..#.. => #
            .#... => #
            .#.#. => #
            .#.## => #
            .##.. => #
            .#### => #
            #.#.# => #
            #.### => #
            ##.#. => #
            ##.## => #
            ###.. => #
            ###.# => #
            ####. => #
        """.trimIndent()

        assertEquals(325, Day12.potCalc(sample, 20))
    }

    @Test
    fun testPotCalcReal(){
        val input = this.javaClass.getResource("/day12_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(3903, Day12.potCalc(input, 20))

    }

    @Test
    fun testPotCalcRealBig(){
        val input = this.javaClass.getResource("/day12_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(3450000002268, Day12.potCalcBig(input, 50000000000L))

    }

}
