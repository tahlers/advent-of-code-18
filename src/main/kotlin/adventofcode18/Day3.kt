package adventofcode18

import java.lang.Integer.max

object Day3 {

    data class Claim(val xoffset: Int, val yoffset: Int, val width: Int, val height: Int, val id: Int)

    fun claims(input: String): Int {
        val lines = input.split("\n")
        val claims = lines.map { parseLine(it) }
        val fieldMap: Map<Pair<Int, Int>, Int> = claims.flatMap { createPairs(it) }
                .groupingBy { it }.eachCount()

        return fieldMap.values
                .filter { it > 1 }
                .size
    }

    fun parseLine(line: String): Claim {
        val (id, _, offset, dimension) = line.split(" ")
        val (xOffset, yOffset) = offset.substringBefore(":").split(",")
        val (width, height) = dimension.split("x")
        return Claim(xOffset.toInt(), yOffset.toInt(), width.toInt(), height.toInt(), id.substringAfter("#").toInt())
    }

    fun createPairs(claim: Claim): List<Pair<Int, Int>> {
        val xrange = claim.xoffset until (claim.xoffset + claim.width)
        return xrange.flatMap { x ->
            val yrange = claim.yoffset until (claim.yoffset + claim.height)
            yrange.map { y -> x to y }
        }
    }

    fun nonOverlappingClaimIds(input: String): Set<Int> {
        val lines = input.split("\n")
        val claims: List<Claim> = lines.map { parseLine(it) }
        // : Map<Pair<Int, Int>, Set<Claim>>
        val pToClaim: Map<Pair<Int, Int>, Set<Claim>>  = claims
                .flatMap { c -> createPairs(c).map { p -> p to c } }
                .groupingBy { it.first }
                .fold(emptySet()) { acc, elem -> acc.plus(elem.second)}

//         val claimCountMap: Map<Claim, Int> = pToClaim
//                 .values
//                 .groupingBy { it }
//                 .
//
//
              //  .fold(setOf()) { acc, elem -> acc.plus(elem)}

        //return setOf(0)

        val claimCountMap: Map<Claim, Int> = pToClaim
                .values
                .fold(mapOf()) { acc, claims ->
                    val claimsSize = claims.size
                    val newacc: Map<Claim, Int> =
                            claims.fold(acc) { acc2, claim -> acc2.plus(claim to max(acc2.getOrDefault(claim, 0), claimsSize))
                            }
                    newacc

                }
        return claimCountMap
                .filterValues { it == 1 }
                .keys
                .map { it.id }
                .toSet()
    }



}