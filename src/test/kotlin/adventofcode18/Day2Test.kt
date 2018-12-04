
package adventofcode18

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull



class Day2Test {

    @Test
    fun testChecksum(){
        val example1 = """
        abcdef
        bababc
        abbcde
        abcccd
        aabcdd
        abcdee
        ababab
        """.trimIndent()

        assertEquals(12, d2_checksum(example1))
    }


    @Test
    fun testRealSample(){
        val sample = this.javaClass.getResource("/day2_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(5904, d2_checksum(sample))
    }

    @Test
    fun testFindId(){
        val example1 = """
        abcde
        fghij
        klmno
        pqrst
        fguij
        axcye
        wvxyz
        """.trimIndent()

        assertEquals("fgij", d2_findId(example1))
    }

    @Test
    fun testFindRealSample(){
        val sample = this.javaClass.getResource("/day2_1_input.txt").readText(Charsets.UTF_8)
        assertEquals("jiwamotgsfrudclzbyzkhlrvp", d2_findId(sample))
    }

}
