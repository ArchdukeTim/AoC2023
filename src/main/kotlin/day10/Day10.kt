package day10

import kotlin.math.min

enum class Pipe(val connections: Int) {
    LD(0b0110),
    LU(0b1010),
    UD(0b1100),
    RU(0b1001),
    LR(0b0011),
    GROUND(0b0000),
    RD(0b0101),
    START(0b1111);

    // swap up and down bits, and left and right bits
    val inverted =
        ((connections and 0b1000) shr 1) or ((connections and 0b0100) shl 1) or ((connections and 0b0010) shr 1) or ((connections and 0b0001) shl 1)

}

fun main() {
    var n = 0
    val input = object {}.javaClass.getResourceAsStream("input").reader().readLines().joinToString(separator = "") {
        n = it.length
        it
    }.map {
        when (it) {
            '7' -> Pipe.LD
            'F' -> Pipe.RD
            'J' -> Pipe.LU
            '-' -> Pipe.LR
            '|' -> Pipe.UD
            'L' -> Pipe.RU
            'S' -> Pipe.START
            else -> Pipe.GROUND
        }
    }

    val start = input.indexOf(Pipe.START)
    val visited = mutableSetOf(start)
    val toVisit = ArrayDeque<Int>(input.size)
    toVisit.add(start)
    val distances = arrayOfNulls<Int>(input.size)
    val loop = arrayOfNulls<Int>(input.size)
    distances[start] = 0

    fun connectionsOf(index: Int): List<Int> {
        val up = if (index < n) null else index - n
        val down = if (index + n < input.size) index + n else null
        val left = if (index % n > 0) index - 1 else null
        val right = if (index % n < n - 1) index + 1 else null

        val currentPipe = input[index]
        return listOfNotNull(
            up.takeIf { it != null && currentPipe.connections and input[it].inverted and 0b1000 > 0 },
            down.takeIf { it != null && currentPipe.connections and input[it].inverted and 0b0100 > 0 },
            left.takeIf { it != null && currentPipe.connections and input[it].inverted and 0b0010 > 0 },
            right.takeIf { it != null && currentPipe.connections and input[it].inverted and 0b0001 > 0 },
        )
    }
    while (toVisit.isNotEmpty()) {
        val currentIndex = toVisit.removeFirst()
        visited.add(currentIndex)
        loop[currentIndex] = 1
        val currentDistance = distances[currentIndex] ?: 0
        val connectingIndices = connectionsOf(currentIndex)
        for (index in connectingIndices) {
            distances[index] = min(distances[index] ?: (currentDistance + 1), currentDistance + 1)
            if (index !in visited) {
                toVisit.addLast(index)
            }
        }
    }

    println(distances.maxOf { it ?: 0 })

    var tilesInLoop = 0
    for (i in 0..<input.size / n) {
        var crossedUp = false
        var crossedDown = false
        for (j in 0..<n) {
            val index = i * n + j
            val currentLoopValue = loop[index] ?: 0
            if (currentLoopValue == 1 && input[index].connections and 0b1000 > 0) {
                crossedUp = !crossedUp
            }
            if (currentLoopValue == 1 && input[index].connections and 0b0100 > 0) {
                crossedDown = !crossedDown
            }
            if (crossedUp && crossedDown && currentLoopValue == 0) {
                tilesInLoop++
            }
        }
    }
    println(tilesInLoop)

}
