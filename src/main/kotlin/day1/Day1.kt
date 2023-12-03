package day1

val DEBUG = false
val PART1 = true
val PART2 = true
fun main() {
    val fileName = if (DEBUG) "test" else "input"

    if (PART1) {
        val sum = object {}.javaClass.getResourceAsStream(fileName).reader().readLines().sumOf { s ->
            val firstDigit = s.first { it.isDigit() }.digitToInt()
            val lastDigit = s.last { it.isDigit() }.digitToInt()

            if (DEBUG) println(firstDigit * 10 + lastDigit)

            firstDigit * 10 + lastDigit
        }
        if (!DEBUG) println(sum)
    }


    if (PART2) {
        // zero isn't possible but fixes index
        val nums = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val reversedNums = nums.map { it.reversed() }
        val regex =
            Regex("\\d|one|two|three|four|five|six|seven|eight|nine")
        val r_regex =
            Regex("\\d|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin")
        val sum2 = object {}.javaClass.getResourceAsStream(fileName).reader().readLines().sumOf { s ->
            if (DEBUG) print("$s ")
            val match = regex.find(s)
            val r_match = r_regex.find(s.reversed())
            val firstMatch = match?.value ?: "0"
            val lastMatch = r_match?.value ?: "0"

            if (DEBUG) {
                print("$firstMatch $lastMatch ")
            }

            val firstValue = when {
                firstMatch in nums -> nums.indexOf(firstMatch)
                else -> firstMatch.toInt()
            }
            val lastValue = when {
                lastMatch in reversedNums -> reversedNums.indexOf(lastMatch)
                else -> lastMatch.toInt()
            }

            if (DEBUG) {
                print("$firstValue $lastValue ")
            }

            if (DEBUG) {
                println(firstValue * 10 + lastValue)
            }
            firstValue * 10 + lastValue
        }
        println(sum2)
    }
}