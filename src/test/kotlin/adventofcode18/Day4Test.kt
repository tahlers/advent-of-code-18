
package adventofcode18

import io.vavr.kotlin.list
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals


class Day4Test {

    @Test
    fun testExtractDate(){
        assertEquals(LocalDate.of(1518, 11, 1), Day4.extractDate("[1518-11-01 00:00] Guard #10 begins shift"))
        assertEquals(LocalDate.of(1518, 11, 2), Day4.extractDate("[1518-11-01 23:55] Guard #10 begins shift"))
        assertEquals(LocalDate.of(1518, 11, 5), Day4.extractDate("[1518-11-05 00:45] falls asleep"))
    }


    @Test
    fun testGuard(){
        val example1 = """
        [1518-11-01 00:00] Guard #10 begins shift
        [1518-11-01 00:05] falls asleep
        [1518-11-01 00:25] wakes up
        [1518-11-01 00:30] falls asleep
        [1518-11-01 00:55] wakes up
        [1518-11-01 23:58] Guard #99 begins shift
        [1518-11-02 00:40] falls asleep
        [1518-11-02 00:50] wakes up
        [1518-11-03 00:05] Guard #10 begins shift
        [1518-11-03 00:24] falls asleep
        [1518-11-03 00:29] wakes up
        [1518-11-04 00:02] Guard #99 begins shift
        [1518-11-04 00:36] falls asleep
        [1518-11-04 00:46] wakes up
        [1518-11-05 00:03] Guard #99 begins shift
        [1518-11-05 00:45] falls asleep
        [1518-11-05 00:55] wakes up
        """.trimIndent()

        assertEquals(240, Day4.findGuardStrategy1(example1))
    }


    @Test
    fun testRealSample(){
        val sample = this.javaClass.getResource("/day4_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(77084, Day4.findGuardStrategy1(sample))
    }

    @Test
    fun testGuard2(){
        val example1 = """
        [1518-11-01 00:00] Guard #10 begins shift
        [1518-11-01 00:05] falls asleep
        [1518-11-01 00:25] wakes up
        [1518-11-01 00:30] falls asleep
        [1518-11-01 00:55] wakes up
        [1518-11-01 23:58] Guard #99 begins shift
        [1518-11-02 00:40] falls asleep
        [1518-11-02 00:50] wakes up
        [1518-11-03 00:05] Guard #10 begins shift
        [1518-11-03 00:24] falls asleep
        [1518-11-03 00:29] wakes up
        [1518-11-04 00:02] Guard #99 begins shift
        [1518-11-04 00:36] falls asleep
        [1518-11-04 00:46] wakes up
        [1518-11-05 00:03] Guard #99 begins shift
        [1518-11-05 00:45] falls asleep
        [1518-11-05 00:55] wakes up
        """.trimIndent()

        assertEquals(4455, Day4.findGuardStrategy2(example1))
    }


    @Test
    fun testRealSample2(){
        val sample = this.javaClass.getResource("/day4_1_input.txt").readText(Charsets.UTF_8)
        assertEquals(23047, Day4.findGuardStrategy2(sample))
    }


}
