
package adventofcode18

import kotlin.test.Test
import kotlin.test.assertEquals


class Day14Test {

    @Test
    fun testRecipesAfter(){
        assertEquals("5158916779", Day14.recipesAfter(9))
        assertEquals("0124515891", Day14.recipesAfter(5))
        assertEquals("9251071085", Day14.recipesAfter(18))
        assertEquals("5941429882", Day14.recipesAfter(2018))
    }

    @Test
    fun testRecipesAfterReal(){
        assertEquals("6126491027", Day14.recipesAfter(209231))
    }

    @Test
    fun testForRecipePattern(){
        assertEquals(9, Day14.recipesFoundFor("51589"))
        assertEquals(5, Day14.recipesFoundFor("01245"))
        assertEquals(18, Day14.recipesFoundFor("92510"))
        assertEquals(2018, Day14.recipesFoundFor("59414"))
    }

    @Test
    fun testForRecipePatternReal(){
        assertEquals(20191616, Day14.recipesFoundFor("209231"))
    }

}
