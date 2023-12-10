package day9

fun main() {
    val input = object {}.javaClass.getResourceAsStream("input").reader().readLines()
    val pt1 = input.sumOf {
        getNextInt(it.split(" ").map { it.toInt() })
    }
    println(pt1)
    val pt2 = input.sumOf {
        getPreviousInt(it.split(" ").map { it.toInt() })
    }
    println(pt2)
}

fun getNextInt(list: List<Int>): Int {
    return if (list.all { it == 0 }) 0
    else list.last() + getNextInt(list.windowed(2).map { it.last() - it.first() })
}

fun getPreviousInt(list: List<Int>): Int {
    return if (list.all { it == 0 }) 0
    else list.first() - getPreviousInt(list.windowed(2).map { it.last() - it.first() })
}