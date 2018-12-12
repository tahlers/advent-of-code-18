
package adventofcode18

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals


class Day11Test {


    @Test
    fun testCellPowerLevel(){
        assertEquals(4, Day11.Cell(3, 5, 8).power())
        assertEquals(-5, Day11.Cell(122, 79, 57).power())
        assertEquals(0, Day11.Cell(217, 196, 39).power())
        assertEquals(4, Day11.Cell(101, 153, 71).power())
    }

    @Test
    fun testGridMaxPowerExamples(){
        assertEquals("33,45", Day11.gridMax(18))
        assertEquals("21,61", Day11.gridMax(42))
    }

    @Test
    fun testGridMaxPowerReal(){
        assertEquals("33,54", Day11.gridMax(5235))
    }

    @Test
    @Ignore //long running
    fun testGridMaxVarSize(){
        assertEquals("90,269,16", Day11.gridMaxVarSize(18))
    }

    @Test
    @Ignore //long running
    fun testGridMaxVarSizeReal(){
        assertEquals("232,289,8", Day11.gridMaxVarSize(5235))
    }
}
