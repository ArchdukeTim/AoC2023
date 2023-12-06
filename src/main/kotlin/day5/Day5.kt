package day5


data class Mapping(val dst: Long, val src: Long, val size: Long) {
    fun contains(test: Long) = test in src..<src + size
    fun map(test: Long): Long = test - src + dst
}

class Mappings {
    private val maps = mutableListOf<Mapping>()

    fun addMap(map: Mapping) {
        maps.add(map)
    }

    fun map(test: Long) = maps.firstOrNull { it.contains(test) }?.map(test) ?: test
}

fun main() {

    val seeds = mutableListOf<LongRange>()
    val seedToSoil = Mappings()
    val soilToFertilizer = Mappings()
    val fertToWater = Mappings()
    val waterToLight = Mappings()
    val lightToTemp = Mappings()
    val tempToHumidity = Mappings()
    val humidityToLocation = Mappings()

    var activeMapping = seedToSoil
    object {}.javaClass.getResourceAsStream("input").reader().forEachLine {

        if (it.contains("seeds:")) {
            seeds.addAll(it.substring(7).split(" ")
                .chunked(2) { it.first().toLong()..<it.first().toLong() + it.last().toLong() })
        } else if (it.contains("seed-to-soil")) {
            activeMapping = seedToSoil
        } else if (it.contains("soil-to-fertilizer")) {
            activeMapping = soilToFertilizer
        } else if (it.contains("fertilizer-to-water")) {
            activeMapping = fertToWater
        } else if (it.contains("water-to-light")) {
            activeMapping = waterToLight
        } else if (it.contains("light-to-temperature")) {
            activeMapping = lightToTemp
        } else if (it.contains("temperature-to-humidity")) {
            activeMapping = tempToHumidity
        } else if (it.contains("humidity-to-location")) {
            activeMapping = humidityToLocation
        } else {
            if (it.isBlank()) return@forEachLine
            val splits = it.split(" ").map { it.toLong() }
            activeMapping.addMap(Mapping(splits[0], splits[1], splits[2]))
        }
    }


    fun seedToLocation(seed: Long): Long {
        val soil = seedToSoil.map(seed)
        val fert = soilToFertilizer.map(soil)
        val water = fertToWater.map(fert)
        val light = waterToLight.map(water)
        val temp = lightToTemp.map(light)
        val humidity = tempToHumidity.map(temp)
        return humidityToLocation.map(humidity)
    }

    println(seeds.minOf { it.minOf { seedToLocation(it) } })

}