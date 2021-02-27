package day03

import java.io.File

const val FILENAME = "src/day03/Input.txt"

val map = File(FILENAME).readLines().map { it.trim().toCharArray() }.toTypedArray()

fun part01(right: Int = 3, down: Int = 1): Int {
    var treeCount = 0
    var i = 0
    var j = 0
    while (i < map.size - 1) {
        j += right
        i += down
        if (j >= map[0].size)
            j %= map[0].size
        if (map[i][j] == '#')
            treeCount++
    }
    return treeCount
}

fun part02() = part01(1, 1) * part01() * part01(5, 1) * part01(7, 1) * part01(1, 2)

fun main() {
    println("Advent of Code 2020, Day 03")
    println(part01())
    println(part02())
}
