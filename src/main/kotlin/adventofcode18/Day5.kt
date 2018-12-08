package adventofcode18

object Day5 {

    fun reduceSize(input: String): Int {
        return reduce(input).length
    }

    private fun canReact(char1: Char, char2: Char): Boolean =
            char1.toLowerCase() == char2.toLowerCase() && (char1.isUpperCase() != char2.isUpperCase())

    fun reduce(input: String): String {
        return input.fold("")  { acc: String, c: Char ->
            if (acc.isNotEmpty() && canReact(acc.last(), c)) acc.dropLast(1) else acc + c
        }
    }

    fun reduceSizeOptimal(input: String): Int? {
        val polymers = input.toLowerCase().toSet()
        return polymers
                .map { p -> reduceSize(input.filter { it.toLowerCase() != p }) }
                .min()
    }

}