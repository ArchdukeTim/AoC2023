package day4

import kotlin.math.pow



fun main() {
    val sum = object {}.javaClass.getResourceAsStream("input").reader().readLines().sumOf {
        val split = Regex("Card\\s+\\d+:([\\d\\s]+)\\|([\\d\\s]+)").matchEntire(it)!!.groupValues
        val winningNums = split[1].trim().split(Regex("\\s+")).map { it.trim().toInt() }.toSet()
        val picks = split[2].trim().split(Regex("\\s+")).map { it.trim().toInt() }.toSet()

        val matches = winningNums.intersect(picks)
        if (matches.isEmpty()) 0
        2.0.pow(matches.size - 1).toInt()
    }
    println(sum)

    data class Draw(val index: Int, val winningNums: Set<Int>, val picks: Set<Int>) {
        val wins = winningNums.intersect(picks).size
    }

    val matches = object {}.javaClass.getResourceAsStream("input").reader().readLines().map {
        val split = Regex("Card\\s+(\\d+):([\\d\\s]+)\\|([\\d\\s]+)").matchEntire(it)!!.groupValues
        val index = split[1].toInt() - 1
        val winningNums = split[2].trim().split(Regex("\\s+")).map { it.trim().toInt() }.toSet()
        val picks = split[3].trim().split(Regex("\\s+")).map { it.trim().toInt() }.toSet()

        Draw(index, winningNums, picks)
    }

    fun calculateWins(draw: Draw): Int {
        var totalWins = draw.wins
        for (i in draw.index + 1..draw.index + draw.wins) {
            totalWins += calculateWins(matches[i])
        }
        return totalWins
    }

    println(matches.sumOf { calculateWins(it) } + matches.size)


}


