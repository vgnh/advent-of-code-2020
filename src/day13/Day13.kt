package day13

import java.io.File

const val FILENAME = "src/day13/Input.txt"

val input = File(FILENAME).readLines().toTypedArray()

fun part01(): Int {
    val depart = input[0].toInt()
    val bus = input[1].split(",").filter { it != "x" }.map { it.toInt() }.toTypedArray()
    var busToTake = 0
    var leastDiff = depart
    for (id in bus) {
        var time = 0
        while (time < depart) {
            time += id
        }

        val diff = time - depart
        if (diff < leastDiff) {
            leastDiff = diff
            busToTake = id
        }
    }
    return leastDiff * busToTake
}

fun part02(): Long {
    val bus = input[1].split(",")
    var time = 0L
    var inc = bus[0].toLong()
    for (i in 1 until bus.size) {
        if (bus[i] != "x") {
            val newTime = bus[i].toInt()
            while (true) {
                time += inc
                if ((time + i) % newTime == 0L) {
                    inc *= newTime
                    break
                }
            }
        }
    }
    return time
}

fun main() {
    println("Advent of Code 2020, Day 13")
    println(part01())
    println(part02())
}
