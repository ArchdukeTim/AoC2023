package day7


const val PART2 = true
data class Hand(val cards: List<Char>, val bid: Int) : Comparable<Hand> {

    enum class Strength {
        HIGH_CARD,
        PAIR,
        TWO_OF_A_KIND,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND,

    }

    private val strength = cards.groupingBy { it }.eachCount().let {
        if (it.size == 1) return@let Strength.FIVE_OF_A_KIND
        val jsReplaced: Map<Char, Int>
        if (PART2) {
            // Replace Js with highest non-J card
            val max = it.filter { it.key != 'J' }.maxBy { it.value }
            jsReplaced = it.toMutableMap().apply {
                replace(max.key, max.value + this.getOrDefault('J', 0))
                remove('J')
            }
        } else jsReplaced = it
        when (jsReplaced.size) {
            1 -> Strength.FIVE_OF_A_KIND
            2 -> {
                if (jsReplaced.containsValue(4)) {
                    Strength.FOUR_OF_A_KIND
                } else Strength.FULL_HOUSE
            }

            3 -> {
                if (jsReplaced.containsValue(3)) {
                    Strength.THREE_OF_A_KIND
                } else Strength.TWO_OF_A_KIND
            }

            4 -> Strength.PAIR
            else -> Strength.HIGH_CARD
        }
    }

    private val CARDS = if (PART2) "J23456789TQKA" else "23456789TJQKA"

    override fun compareTo(other: Hand): Int {
        return if (this.strength < other.strength) {
            -1
        } else if (this.strength > other.strength) 1
        // compare first non-matching cards
        else this.cards.zip(other.cards).first { it.first != it.second }
            .let { CARDS.indexOf(it.first).compareTo(CARDS.indexOf(it.second)) }
    }
}

fun main() {
    val hands = object {}.javaClass.getResourceAsStream("input")!!.reader().readLines().map {
        val split = it.split(" ")
        Hand(split[0].toCharArray().toList(), split[1].toInt())
    }
    println(hands.sorted().mapIndexed { index, hand ->
        (index + 1) * hand.bid
    }.sum())

}