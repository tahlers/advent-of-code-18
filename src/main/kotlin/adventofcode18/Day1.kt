package adventofcode18

fun freqencyAdjust(input: String): Int  {
    val lines = input.split("\n")
    return lines.map{it.toInt() }.sum()

}

fun freqFirstRepeat(input: String): Int {
    val lines = input.split("\n")
    val intInput = lines.map{it.toInt() }

    return firstRepeat(0, listOf(), intInput, intInput)
}


tailrec fun firstRepeat(currentFreq: Int, seen: List<Int>, input: List<Int>, complete: List<Int>): Int {
    return if (currentFreq in seen) {
        currentFreq
    } else {
        val newInput = if (input.isEmpty()) complete else input
        val newFreq = currentFreq + newInput.first()
        firstRepeat(newFreq, seen + currentFreq, newInput.drop(1), complete  )
    }
}