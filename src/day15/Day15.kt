package day15

import java.io.File

const val FILENAME = "src/day15/Input.txt"

val starting = File(FILENAME).readText().split(",").map { it.toInt() }.toTypedArray()

fun part01(limit: Int = 2020): Int {
    val prevOccurrence = IntArray(limit)
    //val prevOccurrence = mutableMapOf<Int, Int>()
    var turn = 0
    for (i in starting) prevOccurrence[i] = ++turn

    var curr = starting[starting.size - 1]
    var next = 0
    while (turn < limit) {
        turn++
        curr = next
        next = if (prevOccurrence[curr] == 0) 0 else turn - prevOccurrence[curr] // Uncomment when using array
        //next = if (!prevOccurrence.containsKey(curr)) 0 else turn - prevOccurrence[curr]!! // Uncomment when using map
        prevOccurrence[curr] = turn
    }

    return curr
}

fun part02() = part01(30_000_000)

fun main() {
    println("Advent of Code 2020, Day 15")
    println(part01())
    println(part02())
}
