package day11

import java.io.File

const val FILENAME = "src/day11/Input.txt"

fun map(): Array<CharArray> {
    val strings = File(FILENAME).readLines()
    val map = Array<CharArray>(strings.size) { CharArray(strings[0].length) }
    for (i in map.indices) {
        for (j in map[0].indices) {
            map[i][j] = strings[i][j]
        }
    }
    return map
}

fun part01(part: Int = 1): Int {
    var map = map()
    val rows = map.size
    val cols = map[0].size

    val map2 = createCopy(map)
    while (true) {
        var stateChange = false
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (map[i][j] == 'L' && !adjacentOccupied(part, map, i, j)) {
                    stateChange = true
                    map2[i][j] = '#'
                } else if (map[i][j] == '#' && occupiedBy(if (part == 1) 4 else 5, map, i, j)) {
                    stateChange = true
                    map2[i][j] = 'L'
                }
            }
        }
        if (!stateChange)
            break
        else
            map = createCopy(map2)
    }

    return occupiedCount(map2)
}

fun part02() = part01(part = 2)

fun createCopy(src: Array<CharArray>): Array<CharArray> {
    val copy = Array<CharArray>(src.size) { CharArray(src[0].size) }
    for (i in src.indices)
        copy[i] = src[i].clone()
    return copy
}

fun adjacentOccupied(part: Int, map: Array<CharArray>, row: Int, col: Int): Boolean {
    val pos = arrayOf(
        arrayOf(-1, -1),
        arrayOf(-1, 0),
        arrayOf(-1, 1),
        arrayOf(0, -1),
        arrayOf(0, 1),
        arrayOf(1, -1),
        arrayOf(1, 0),
        arrayOf(1, 1)
    )
    if (part == 1) {
        for (i in pos.indices) {
            val x = row + pos[i][0]
            val y = col + pos[i][1]
            if (isValid(map, x, y) && map[x][y] == '#')
                return true
        }
    } else {
        for (i in pos.indices) {
            var x = row
            var y = col
            while (true) {
                x += pos[i][0]
                y += pos[i][1]
                if (isValid(map, x, y)) {
                    if (map[x][y] == '#')
                        return true
                    else if (map[x][y] == 'L')
                        break
                } else
                    break
            }
        }
    }
    return false
}

fun isValid(map: Array<CharArray>, x: Int, y: Int) = (x >= 0 && x < map.size) && (y >= 0 && y < map[0].size)

fun occupiedBy(howMany: Int, map: Array<CharArray>, row: Int, col: Int): Boolean {
    var occupied = 0
    val pos = arrayOf(
        arrayOf(-1, -1),
        arrayOf(-1, 0),
        arrayOf(-1, 1),
        arrayOf(0, -1),
        arrayOf(0, 1),
        arrayOf(1, -1),
        arrayOf(1, 0),
        arrayOf(1, 1)
    )
    if (howMany == 4) {
        // part01()
        for (i in pos.indices) {
            val x = row + pos[i][0]
            val y = col + pos[i][1]
            if (isValid(map, x, y) && map[x][y] == '#')
                occupied++
        }
    } else {
        // part02()
        for (i in pos.indices) {
            var x = row
            var y = col
            while (true) {
                x += pos[i][0]
                y += pos[i][1]
                if (isValid(map, x, y)) {
                    if (map[x][y] == '#') {
                        occupied++
                        break
                    } else if (map[x][y] == 'L')
                        break
                } else
                    break
            }
        }
    }
    return occupied >= howMany
}

fun occupiedCount(map: Array<CharArray>): Int {
    var occupied = 0
    for (i in map.indices) {
        for (j in map[0].indices) {
            if (map[i][j] == '#')
                occupied++
        }
    }
    return occupied
}

fun main() {
    println("Advent of Code 2020, Day 11")
    println(part01())
    println(part02())
}
