package day3

import kotlin.math.abs

fun main() {
    val numbers = mutableListOf<MatchResult>()
    val symbols = mutableListOf<MatchResult>()

    val reader = object {}.javaClass.getResourceAsStream("input").reader()
    val lines = reader.readLines()
    val lineSize = lines[0].length
    val text = lines.joinToString("")
    Regex("\\d+").findAll(text).forEach {
        println(it.value)
        numbers.add(it)
    }
    Regex("[^\\d\\.]").findAll(text).forEach {
        println(it.value)
        symbols.add(it)
    }

    val sum = numbers.sumOf {
        val numberRow = it.range.first / lineSize
        val numberColStart = it.range.first % lineSize
        val numberColEnd = it.range.last % lineSize
        println("($numberRow, $numberColStart, $numberColEnd)")
        for (symbol in symbols) {
            val symbolRow = symbol.range.first / lineSize
            val symbolCol = symbol.range.first % lineSize
            if (abs(symbolRow - numberRow) <= 1 && symbolCol in (numberColStart - 1..numberColEnd + 1)) return@sumOf it.value.toInt()
        }
        0

    }
    println(sum)

    val sumGearRatios = symbols.filter { it.value == "*" }.sumOf { gear ->

        val gearRow = gear.range.first / lineSize
        val gearCol = gear.range.first % lineSize

        val touchingNums = numbers.filter {
            val numRow = it.range.first / lineSize
            val numColStart = it.range.first % lineSize
            val numColEnd = it.range.last % lineSize
            abs(gearRow - numRow) <= 1 && gearCol in (numColStart - 1..numColEnd + 1)
        }

        if (touchingNums.size == 2) {
            return@sumOf touchingNums.foldRight(1) { it, acc -> it.value.toInt() * acc }
        }
        0L
    }
    println(sumGearRatios)

    reader.close()
}