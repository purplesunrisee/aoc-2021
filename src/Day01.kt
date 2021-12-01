fun main() {
    fun part1(data: List<Int>): Int {
        var previous = data[0]
        return data.fold(0) { acc: Int, el: Int ->
            val result = if(previous < el) { acc + 1 } else { acc }
            previous = el
            result
        }
    }

    fun part2(data: List<Int>): Int {
        return part1((0..data.size - 3).map { data.subList(it, it + 3).sum() })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val data = testInput.map(String::toInt)
    check(part1(data) == 7)
    check(part2(data) == 5)

    val input = readInput("Day01")
    val data2 = input.map(String::toInt)
    println(part1(data2))
    println(part2(data2))
}