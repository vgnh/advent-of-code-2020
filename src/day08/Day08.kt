package day08

import java.io.File

const val FILENAME = "src/day08/Input.txt"

val instructions = File(FILENAME).readLines().toTypedArray()

fun part01(runningPart02: Boolean = false): Int {
    var accumulator = 0
    var reachedEnd = false
    val executed = BooleanArray(instructions.size)
    var i = 0
    while (i < instructions.size) {
        if (i == instructions.size - 1)
            reachedEnd = true
        if (executed[i])
            break
        when {
            instructions[i].contains("nop") -> {
                executed[i] = true
                i++
            }
            instructions[i].contains("acc") -> {
                accumulator += instructions[i].substring(4).toInt()
                executed[i] = true
                i++
            }
            instructions[i].contains("jmp") -> {
                val jumpTo = i + instructions[i].substring(4).toInt()
                executed[i] = true
                i = jumpTo
            }
        }
    }
    // If running only part01, return accumulator as it is
    if (!runningPart02)
        return accumulator
    else {
        // If running again for part02, return accumulator only if the final instruction
        // is reached. Else return -1, as final instruction has not been reached.
        if (reachedEnd)
            return accumulator
        else
            return -1
    }
}

fun part02(): Int {
    for (i in instructions.indices) {
        if (instructions[i][0] != 'a') {
            val beforeReplace = instructions[i]
            if (instructions[i][0] == 'n')
                instructions[i] = instructions[i].replace("nop", "jmp")
            else if (instructions[i][0] == 'j')
                instructions[i] = instructions[i].replace("jmp", "nop")

            val accumulator = part01(runningPart02 = true)
            if (accumulator > 0)
                return accumulator
            else
                instructions[i] = beforeReplace
        }
    }
    return -1
}

fun main() {
    println("Advent of Code 2020, Day 08")
    println(part01())
    println(part02())
}
