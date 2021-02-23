package day06

import java.io.File

const val FILENAME = "src/day06/Input.txt"

val groupList = File(FILENAME).readText().split("\n\n").map { it.split("\n").joinToString(" ") }

fun part01(): Int {
    var totalYesCount = 0
    for (group in groupList) {
        val answerSet = HashSet<Char>()
        for (answer in group.split(" "))
            answerSet.addAll(answer.toList())
        totalYesCount += answerSet.size
    }
    return totalYesCount
}

fun part02(): Int {
    var totalYesCount = 0
    for (group in groupList) {
        var answerSet: HashSet<Char>? = null
        for (answer in group.split(" ")) {
            if (answerSet == null)
                answerSet = HashSet<Char>(answer.toList())
            else
                answerSet.retainAll(answer.toList())
        }
        totalYesCount += answerSet!!.size
    }
    return totalYesCount
}

fun main() {
    println("Advent of Code 2020, Day 06")
    println(part01())
    println(part02())
}
