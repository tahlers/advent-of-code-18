package adventofcode18

fun d2_checksum(input: String): Int {
    val lines = input.split("\n")
    val charFreqResults: List<Pair<Int, Int>> = lines.map(::charFreq)
    val pairCount = charFreqResults.map { it.first }.sum()
    val tripleCount = charFreqResults.map {it.second}.sum()
    return pairCount * tripleCount


}

fun charFreq(line: String): Pair<Int, Int> {
    val charCountMap: Map<Char, Int> = line.asSequence().groupingBy { it }.eachCount()
    val containsPair = charCountMap.values.contains(2)
    val containsTriple = charCountMap.values.contains(3)
    return Pair(if (containsPair) 1 else 0, if (containsTriple) 1 else 0 )
}

fun d2_findId(input: String): String {
    val lines = input.split("\n")
    return findSimilar(lines[0], lines.drop(1))
}

tailrec fun findSimilar(line: String, toCheck: List<String>): String {
    if (toCheck.isEmpty()) return ""
    val firstOrNull = toCheck.firstOrNull { compare(line, it).length == line.length - 1 }
    return when (firstOrNull) {

        null -> findSimilar(toCheck[0], toCheck.drop(1))
        else -> compare(line, firstOrNull)
    }
}

fun compare(line1: String, line2:String): String {
    val zipped: Sequence<Pair<Char, Char>> = line1.asSequence().zip(line2.asSequence())
    val joined = zipped.filter { it.first == it.second }.map { it.first }.joinToString(separator = "")
    return joined
}