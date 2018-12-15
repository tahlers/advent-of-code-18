
package adventofcode18

import kotlin.test.Test
import kotlin.test.assertEquals


class Day13Test {

    @Test
    fun calcCrashLocationTest(){
        val sample = """
            /->-\
            |   |  /----\
            | /-+--+-\  |
            | | |  | v  |
            \-+-/  \-+--/
              \------/
        """.trimIndent()

        assertEquals("7,3", Day13.firstCrashLocation(sample))
    }

    @Test
    fun testCrashLocationReal(){
        val input = this.javaClass.getResource("/day13_1_input.txt").readText(Charsets.UTF_8)
        assertEquals("118,112", Day13.firstCrashLocation(input))

    }

    @Test
    fun testLastSurvivor(){
        val sample = """
            />-<\
            |   |
            | /<+-\
            | | | v
            \>+</ |
              |   ^
              \<->/
        """.trimIndent()

        assertEquals("6,4", Day13.lastSurvivor(sample))
    }

    @Test
    fun testLastSurvivorReal(){
        val input = this.javaClass.getResource("/day13_1_input.txt").readText(Charsets.UTF_8)
        assertEquals("50,21", Day13.lastSurvivor(input))
    }


}
