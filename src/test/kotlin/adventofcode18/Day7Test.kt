
package adventofcode18

import kotlin.test.Test
import kotlin.test.assertEquals


class Day7Test {


    @Test
    fun testStepOrder(){
        val sample = """
            Step C must be finished before step A can begin.
            Step C must be finished before step F can begin.
            Step A must be finished before step B can begin.
            Step A must be finished before step D can begin.
            Step B must be finished before step E can begin.
            Step D must be finished before step E can begin.
            Step F must be finished before step E can begin.
        """.trimIndent()
        assertEquals("CABDFE", Day7.stepOrder(sample))
    }

    @Test
    fun testLargestFieldReal(){
        val sample = this.javaClass.getResource("/day7_1_input.txt").readText(Charsets.UTF_8)
        assertEquals("ACBDESULXKYZIMNTFGWJVPOHRQ", Day7.stepOrder(sample))
    }

    @Test
    fun testLetterSeconds(){
        assertEquals(61, Day7.letterSeconds('A', 60) )
        assertEquals(86, Day7.letterSeconds('Z', 60) )
    }

    @Test
    fun testSecondsSpend(){
        val sample = """
            Step C must be finished before step A can begin.
            Step C must be finished before step F can begin.
            Step A must be finished before step B can begin.
            Step A must be finished before step D can begin.
            Step B must be finished before step E can begin.
            Step D must be finished before step E can begin.
            Step F must be finished before step E can begin.
        """.trimIndent()
        assertEquals(15, Day7.secondsSpend(sample, 2, 0))
    }

    @Test
    fun testSecondsSpendReal(){
        val sample = this.javaClass.getResource("/day7_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(980, Day7.secondsSpend(sample, 5, 60))
    }

//980
}
