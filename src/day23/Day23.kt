package day23

import java.io.File

const val FILENAME = "src/day23/Input.txt"

val cups = File(FILENAME).readText().map { "$it".toInt() }

fun part01(runPart02: Boolean = false): String {
    val cups = cups.toMutableList()

    var currentCup = cups[0]
    val minCup = cups.minOrNull()
    if (runPart02) {
        for (i in cups.maxOrNull()!! + 1..1000000)
            cups.add(i)
    }
    val maxCup = cups.maxOrNull()

    //val next = mutableMapOf<Int, Int>() // Alternate, but slower method
    val next = IntArray(cups.size + 1)
    for (i in 0 until cups.size - 1)
        next[cups[i]] = cups[i + 1]
    next[cups[cups.size - 1]] = cups[0]

    val moves = if (runPart02) 10000000 else 100
    for (i in 0 until moves) {
        val threeCups = mutableListOf<Int>()
        var cupToInsert = currentCup
        for (j in 0 until 3) {
            cupToInsert = next[cupToInsert]
            threeCups.add(cupToInsert)
        }

        var destCup = if (currentCup - 1 < minCup!!) maxCup else currentCup - 1
        while (threeCups.contains(destCup))
            destCup = if (destCup!! - 1 < minCup) maxCup else destCup - 1

        // Remove the three cups from middle
        next[currentCup] = next[threeCups[threeCups.size - 1]]

        // Add three cups after destination cup
        val nextOfDest = next[destCup!!]
        next[destCup] = threeCups[0]
        next[threeCups[threeCups.size - 1]] = nextOfDest

        currentCup = next[currentCup]
    }

    if (runPart02)
        return "${next[1].toLong() * next[next[1]]}"
    else {
        val cupOrder = StringBuilder("")
        var tempCup = next[1]
        while (true) {
            cupOrder.append(tempCup)
            tempCup = next[tempCup]
            if (tempCup == 1)
                return cupOrder.toString()
        }
    }
}

fun part02() = part01(runPart02 = true)

fun main() {
    println("Advent of Code 2020, Day 23")
    println(part01())
    println(part02())
}
