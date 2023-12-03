package day2

import kotlin.math.max

val DEBUG = false
val PART1 = true
val PART2 = true
fun main() {
    val fileName = if (DEBUG) "test" else "input"


    val maxColors = mapOf("red" to 12, "green" to 13, "blue" to 14)

    if (PART1) {
        val sum = object {}.javaClass.getResourceAsStream(fileName).reader().readLines().sumOf { s ->
            if (DEBUG) println()
            var split = s.split(':', ';')
            val id = Regex("Game (\\d+)").find(split[0])?.groupValues?.last()?.toInt() ?: 0
            split = split.subList(1, split.size)
            for (draw in split) {
                val hand = draw.split(',')
                if (DEBUG) print(hand)
                for (cubes in hand) {
                    val parsed = Regex("(\\d+) (\\w+)").find(cubes)
                    val value = parsed?.groupValues?.get(1)?.toInt() ?: 0
                    val color = parsed?.groupValues?.get(2) ?: ""
                    if (DEBUG) print("$value $color ${maxColors[color]} ")
                    if (value > maxColors.getOrDefault(color, 0)) {
                        return@sumOf 0
                    }

                }
            }
            id

        }
        println(sum)
    }


    if (DEBUG) println("PART 2")
    if (PART2) {
        val sum = object {}.javaClass.getResourceAsStream(fileName).reader().readLines().sumOf { s ->
            val maxes = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            var split = s.split(':', ';')
            val id = Regex("Game (\\d+)").find(split[0])?.groupValues?.last()?.toInt() ?: 0
            split = split.subList(1, split.size)
            for (draw in split) {
                val hand = draw.split(',')
                for (cubes in hand) {
                    val parsed = Regex("(\\d+) (\\w+)").find(cubes)
                    val value = parsed?.groupValues?.get(1)?.toInt() ?: 0
                    val color = parsed?.groupValues?.get(2) ?: ""
                    maxes[color] = max(maxes[color]!!, value)
                }
            }
            maxes.values.reduce { acc, i -> acc * i }
        }
        println(sum)
    }


}