package day6

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    val input = object {}.javaClass.getResourceAsStream("input2").reader().readLines()
    val times = input[0].split(':')[1].trim().split(Regex("\\s+")).map { it.toLong() }
    val distances = input[1].split(':')[1].trim().split(Regex("\\s+")).map { it.toLong() }
    val races = times.zip(distances)

    println(races)
    val total = races.map { pair ->
        val time = pair.first
        val distance = pair.second

        val lhs = time / 2.0
        val rhs = sqrt(time * time - 4.0 * (distance + .001)) / 2.0
        val lowX = ceil(lhs - rhs).toInt()
        val highX = floor(lhs + rhs).toInt()
        val variations = highX - lowX + 1
        println("$time, $distance, $lowX, $highX, $variations")
        variations
    }.reduce { acc, i ->
        acc * i
    }

    println(total)
}