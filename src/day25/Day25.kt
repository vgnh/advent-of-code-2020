package day25

import java.io.File

const val FILENAME = "src/day25/Input.txt"

val input = File(FILENAME).readLines().map { it.toInt() }.toTypedArray()

fun part01(): Long {
    var encryptionKey = 1L
    var value = 1
    while (true) {
        encryptionKey = (encryptionKey * input[1]) % 20201227
        value = (value * 7) % 20201227
        if (value == input[0])
            return encryptionKey
    }
}

fun main() {
    println("Advent of Code 2020, Day 25")
    println(part01())
}
