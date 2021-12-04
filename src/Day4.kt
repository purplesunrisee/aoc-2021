data class Cell(val value: Int, val isMarked: Boolean = false)

data class Board(val board: List<MutableList<Cell>>) {
    fun mark(x: Int) {
        for (row in board) {
            row.replaceAll { if(it.value == x) it.copy(isMarked = true) else it }
        }
    }

    fun isWinner(): Boolean {
        return board.any { row -> row.all { it.isMarked } }
    }

    fun isWinner2(): Boolean {
        return board.any { row -> row.all { it.isMarked } }
                || columnMarked()
    }

    fun unmarkedSum() = board.flatten().sumOf { if(!it.isMarked) it.value else 0 }

    private fun columnMarked(): Boolean {
        for (column in board[0].indices) {
            var isAllMarked = true
            for (row in board.indices) {
                if(!board[row][column].isMarked) {
                    isAllMarked = false
                    continue
                }
            }
            if (isAllMarked) return true
        }
        return false
    }
}

fun processInput(data: List<String>): Pair<List<Int>, List<Board>> {
    val problem = data[0].split(",").map(String::toInt)
    val boards = data
        .drop(1)
        .chunked(6)
        .map { lines ->
            Board(lines.filter { line -> line.isNotBlank() }.map { cells ->
                cells.split(" ", " ")
                    .filter { it.isNotBlank() }
                    .map { Cell(it.toInt()) }.toMutableList()
            })
        }
    return Pair(problem, boards)
}

fun main() {
    fun part1(data: List<String>): Int {
        val (problem, boards) = processInput(data)
        for( num in problem ) {
            for (board in boards) {
                board.mark(num)
                if( board.isWinner() ) {
                    return board.unmarkedSum() * num
                }
            }
        }
        return 0
    }

    fun part2(data: List<String>): Int {
        var (problem, boards) = processInput(data)
        for( num in problem ) {
            for (board in boards) {
                board.mark(num)
                if( board.isWinner2() ) {
                    boards = boards - board
                    if(boards.isEmpty()) {
                        return board.unmarkedSum() * num
                    }
                }
            }
        }
        return 0
    }

    val testInput = readInput("Day4_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day4")
    println(part1(input))
    println(part2(input))
}