package day8

class Node(private val name: String) {
    lateinit var left: Node
    lateinit var right: Node

    val isSuccess = name.endsWith("Z")

    operator fun get(index: Int) = if (index == 0) left else right

    override fun toString() = "$name->(${left.name}, ${right.name})"
}

fun main() {
    val input = object {}.javaClass.getResourceAsStream("input").bufferedReader()
    val directions = input.readLine().toCharArray().map { direction ->
        if (direction == 'L') 0 else 1
    }

    val map = mutableMapOf<String, List<String>>()
    input.readLine()
    input.readLines().forEach {
        // AAA = (BBB, CCC)
        val groups = Regex("(\\w+) = \\((\\w+), (\\w+)\\)").matchEntire(it)!!.groups
        map[groups[1]!!.value] = listOf(groups[2]!!.value, groups[3]!!.value)
    }
    val nodes = map.keys.associateWith { Node(it) }
    map.entries.forEach {
        val parent = nodes[it.key]!!
        val left = nodes[it.value[0]]!!
        val right = nodes[it.value[1]]!!
        parent.left = left
        parent.right = right
    }


    val startNodes = nodes.filter { it.key.endsWith("A") }.values.toList()

    val maxSteps = startNodes.map {
        var steps = 0
        var directionIdx = 0
        var currentNode = it
        while (!currentNode.isSuccess) {
            currentNode = currentNode[directions[directionIdx]]
            steps += 1
            directionIdx = (directionIdx + 1) % directions.size
        }
        steps
    }
    println(maxSteps)


}