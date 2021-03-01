package day09

import java.io.File

const val FILENAME = "src/day09/Input.txt"

val XMAS = File(FILENAME).readLines().map { it.toLong() }.toTypedArray()

fun part01(): Long {
    val preambleSize = 25
    val preamble = XMAS.sliceArray(0..preambleSize - 1).toMutableList()
    val remaining = XMAS.sliceArray(preambleSize..XMAS.size - 1).toMutableList()

    while (remaining.any()) {
        val n = remaining[0]
        var exists = false
        for (i in preamble) {
            if (preamble.contains(n - i)) {
                exists = true
                preamble.add(n)
                remaining.removeAt(0)
                preamble.removeAt(0)
                break
            }
        }
        if (!exists) {
            println(n)
            return n
        }
    }
    return -1
}

fun part02(): Long {
    val invalid = part01()
    for (i in 2..XMAS.size) {
        for (j in 0..XMAS.size - i) {
            val contiguous = XMAS.sliceArray(j..(j + i) - 1)
            if (contiguous.sum() == invalid)
                return contiguous.minOrNull()!! + contiguous.maxOrNull()!!
        }
    }
    return -1
}

fun main() {
    println("Advent of Code 2020, Day 09")
    println(part02()) // Calls part01() as well
}
