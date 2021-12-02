enum class ActionType(val action: String) {
    F("forward"), U("up"), D("down");

    companion object {
        fun fromStr(action: String): ActionType = values().first { it.action == action }
    }
}

data class Action(val actionType: ActionType, val value: Int) {
    companion object {
        fun fromList(actions: List<String>): List<Action> {
            return actions.map {
                val actionType = ActionType.fromStr(it.substringBefore(" "))
                val actionValue = it.substringAfter(" ").let(String::toInt)
                Action(actionType, actionValue)
            }.toList()
        }
    }
}

class VM {
    private var depth: Int = 0
    private var horizontal: Int = 0
    private var aim: Int = 0

    private fun applyActionPart1(action: Action) {
        when(action.actionType) {
            ActionType.F -> horizontal += action.value
            ActionType.D -> depth += action.value
            ActionType.U -> depth -= action.value
        }
    }

    fun applyActionsPart1(actions: List<Action>) {
        actions.forEach { applyActionPart1(it) }
    }

    fun part1(): Int {
        return depth * horizontal
    }

    private fun applyActionPart2(action: Action) {
        when(action.actionType) {
            ActionType.F -> {
                horizontal += action.value
                depth += action.value * aim
            }
            ActionType.D -> aim += action.value
            ActionType.U -> aim -= action.value
        }
    }

    fun applyActionsPart2(actions: List<Action>) {
        actions.forEach { applyActionPart2(it) }
    }

    fun part2(): Int {
        return depth * horizontal
    }
}

fun main() {
    fun part1(data: List<String>): Int {
        val vm = VM()
        vm.applyActionsPart1(Action.fromList(data))
        return vm.part1()
    }

    fun part2(data: List<String>): Int {
        val vm = VM()
        vm.applyActionsPart2(Action.fromList(data))
        return vm.part1()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}