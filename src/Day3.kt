fun maxOf(a: Int, b: Int): Int {
    return if(a == b) 1
           else if(a < b) 0
           else 1
}
fun main() {
    fun part1(data: List<String>): Int {
        val ints = data.map { it.toInt(2) }
        var gamma = 0
        var epsilon = 0
        for ( i in data[0].indices ) {
            val (zeroes, ones) = ints.mostFrequentInColumn(data[0].length, i)
            gamma = gamma shl 1 or maxOf(zeroes, ones)
            epsilon = epsilon shl 1 or (maxOf(zeroes, ones) xor 1)
        }
        return gamma * epsilon
    }

    fun part2(data: List<String>): Int {
        val x = data.map { it.toInt(2) }
        return x.filterByFrequency(data[0].length) {
                zeroes, ones, mask -> if(zeroes > ones) mask else 0
        } * x.filterByFrequency(data[0].length) { zeroes, ones, mask ->
            if(zeroes > ones) 0 else mask
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day3_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day3")

    println(part1(input))
    println(part2(input))
}

fun List<Int>.filterByFrequency(size: Int, condition: (zeroes: Int, ones: Int, mask: Int) -> Int): Int {
    var candidates = this
    val mask = 0x01 shl (size - 1)
    for ( i in 0 until size ) {
        val (zeroes, ones) = candidates.mostFrequentInColumn(size, i)
        candidates = candidates.filter { it shl i and mask == condition(zeroes, ones, mask ) }
        if (candidates.size == 1) break
    }
    return candidates.first()
}

fun List<Int>.mostFrequentInColumn(size: Int, shift: Int): Pair<Int, Int> {
    val mask = 0x01 shl (size - 1)
    var zeroes = 0
    var ones = 0
    forEach { if (it shl shift and mask == 0) zeroes++ else ones++ }
    return Pair(zeroes, ones)
}