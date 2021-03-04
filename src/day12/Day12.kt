package day12

import java.io.File
import kotlin.math.abs

const val FILENAME = "src/day12/Input.txt"

val instructions = File(FILENAME).readLines().toTypedArray()

val values = mapOf<Char, Array<Int>>(
    'N' to arrayOf(0, 1),
    'E' to arrayOf(1, 0),
    'S' to arrayOf(0, -1),
    'W' to arrayOf(-1, 0)
)

fun part01(): Int {
    var pos = 1 // "NESW"[pos] is 'E'
    var x = 0
    var y = 0

    for (ins in instructions) {
        val num = ins.substring(1).toInt()
        if (ins[0] == 'F') {
            x += values["NESW"[pos]]!![0] * num
            y += values["NESW"[pos]]!![1] * num
        } else if (values.keys.contains(ins[0])) {
            x += values[ins[0]]!![0] * num
            y += values[ins[0]]!![1] * num
        } else {
            val turns = num / 90
            for (i in 0 until turns) {
                if (ins[0] == 'R')
                    pos = if (pos + 1 == 4) 0 else pos + 1
                else if (ins[0] == 'L')
                    pos = if (pos - 1 == -1) 3 else pos - 1
            }
        }
    }

    return abs(x) + abs(y)
}

fun part02(): Int {
    var x = 0
    var y = 0
    var wayX = 10
    var wayY = 1

    for (ins in instructions) {
        val num = ins.substring(1).toInt()
        if (ins[0] == 'F') {
            x += wayX * num
            y += wayY * num
        } else if (values.keys.contains(ins[0])) {
            wayX += values[ins[0]]!![0] * num
            wayY += values[ins[0]]!![1] * num
        } else {
            val turns = num / 90
            for (i in 0 until turns) {
                if (ins[0] == 'R') {
                    val swap = wayY
                    wayY = -1 * wayX
                    wayX = swap
                } else if (ins[0] == 'L') {
                    val swap = wayX
                    wayX = -1 * wayY
                    wayY = swap
                }
            }
        }
    }

    return abs(x) + abs(y)
}

fun main() {
    println("Advent of Code 2020, Day 12")
    println(part01())
    println(part02())
}
