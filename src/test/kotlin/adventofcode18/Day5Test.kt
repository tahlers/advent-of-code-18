
package adventofcode18

import kotlin.test.Test
import kotlin.test.assertEquals


class Day5Test {


    @Test
    fun testReduceOnce(){
        assertEquals("C", Day5.reduce("C"))
        assertEquals("", Day5.reduce("cC"))
        assertEquals("c", Day5.reduce("cCc"))
        assertEquals("d", Day5.reduce("cCd"))
        assertEquals("dabCBAcaDA", Day5.reduce("dabAcCaCBAcCcaDA"))
    }


    @Test
    fun testReducedSize(){
        assertEquals(10, Day5.reduceSize("dabAcCaCBAcCcaDA"))
    }

    @Test
    fun testRealSample(){
        val sample = this.javaClass.getResource("/day5_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(11636, Day5.reduceSize(sample))
    }

    @Test
    fun testReducedSizeOptimal(){
        assertEquals(4, Day5.reduceSizeOptimal("dabAcCaCBAcCcaDA"))
    }

    @Test
    fun testRealSampleOptimal(){
        val sample = this.javaClass.getResource("/day5_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(5302, Day5.reduceSizeOptimal(sample))
    }

}
