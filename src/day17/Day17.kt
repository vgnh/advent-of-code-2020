package day17

import java.io.File

const val FILENAME = "src/day17/Input.txt"

val input = File(FILENAME).readLines().toTypedArray()

fun part01(): Int {
    var cube = mutableMapOf<Triple<Int, Int, Int>, Boolean>()
    var y = input.size - 1
    for (i in input.indices) {
        var x = 0
        val z = 0
        for (j in input[i].indices) {
            val ch = input[i][j]
            cube[Triple(x, y, z)] = ch == '#'
            x++
        }
        y--
    }
    var (yMin, yMax, xMin, xMax, zMin) = arrayOf(0, input.size - 1, 0, input[0].length - 1, 0)
    var zMax = 0

    for (cycle in 0 until 6) {
        --yMin; ++yMax; --xMin; ++xMax; --zMin; ++zMax

        val newCube = mutableMapOf<Triple<Int, Int, Int>, Boolean>()
        for (j in yMax downTo yMin) {
            for (i in xMin..xMax) {
                for (k in zMin..zMax) {
                    newCube[Triple(i, j, k)] = state(Triple(i, j, k), cube)
                }
            }
        }

        cube = newCube
    }

    var active = 0
    for (key in cube.keys)
        if (cube[key] == true) active++

    return active
}

fun state(triple: Triple<Int, Int, Int>, map: Map<Triple<Int, Int, Int>, Boolean>): Boolean {
    val currentState = if (map.containsKey(triple)) map[triple] else false
    val (x, y, z) = triple
    var neighbours = 0
    for (j in y + 1 downTo y - 1) {
        for (i in x - 1..x + 1) {
            for (k in z - 1..z + 1) {
                if (i == x && j == y && k == z) continue
                neighbours = if (map.containsKey(Triple(i, j, k))) {
                    if (map[Triple(i, j, k)] == true) neighbours + 1 else neighbours
                } else neighbours
            }
        }
    }
    return if (currentState == true) (neighbours == 2 || neighbours == 3) else neighbours == 3
}

data class Tuple4<out A, out B, out C, out D>(val first: A, val second: B, val third: C, val fourth: D)
data class Tuple8<out A, out B, out C, out D, out E, out F, out G, out H>(val first: A, val second: B, val third: C, val fourth: D, val fifth: E, val sixth: F, val seventh: G, val eighth: H)

fun part02(): Int {
    var cube = mutableMapOf<Tuple4<Int, Int, Int, Int>, Boolean>()
    var y = input.size - 1
    for (i in input.indices) {
        var x = 0
        val z = 0
        val w = 0
        for (j in input[i].indices) {
            val ch = input[i][j]
            cube[Tuple4(x, y, z, w)] = ch == '#'
            x++
        }
        y--
    }
    var (yMin, yMax, xMin, xMax, zMin, zMax, wMin, wMax) = Tuple8(0, input.size - 1, 0, input[0].length - 1, 0, 0, 0, 0)

    for (cycle in 0 until 6) {
        --yMin; ++yMax; --xMin; ++xMax; --zMin; ++zMax; --wMin; ++wMax

        val newCube = mutableMapOf<Tuple4<Int, Int, Int, Int>, Boolean>()
        for (j in yMax downTo yMin) {
            for (i in xMin..xMax) {
                for (k in zMin..zMax) {
                    for (l in wMin..wMax) {
                        newCube[Tuple4(i, j, k, l)] = state2(Tuple4(i, j, k, l), cube)
                    }
                }
            }
        }

        cube = newCube
    }

    var active = 0
    for (key in cube.keys)
        if (cube[key] == true) active++

    return active
}

fun state2(tuple4: Tuple4<Int, Int, Int, Int>, map: Map<Tuple4<Int, Int, Int, Int>, Boolean>): Boolean {
    val currentState = if (map.containsKey(tuple4)) map[tuple4] else false
    val (x, y, z, w) = tuple4
    var neighbours = 0
    for (j in y + 1 downTo y - 1) {
        for (i in x - 1..x + 1) {
            for (k in z - 1..z + 1) {
                for (l in w - 1..w + 1) {
                    if (i == x && j == y && k == z && l == w) continue
                    neighbours = if (map.containsKey(Tuple4(i, j, k, l))) {
                        if (map[Tuple4(i, j, k, l)] == true) neighbours + 1 else neighbours
                    } else neighbours
                }
            }
        }
    }
    return if (currentState == true) (neighbours == 2 || neighbours == 3) else neighbours == 3
}

fun main() {
    println("Advent of Code 2020, Day 17")
    println(part01())
    println(part02())
}
