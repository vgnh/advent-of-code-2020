package day24

import java.io.File

const val FILENAME = "src/day24/Input.txt"

val directions = File(FILENAME).readLines().toTypedArray()

fun part01(): Map<Pair<Int, Int>, Boolean> {
    val tile = mutableMapOf<Pair<Int, Int>, Boolean>()
    tile[Pair(0, 0)] = true

    for (direction in directions) {
        var (x, y) = Pair(0, 0)

        var i = 0
        while (i < direction.length) {
            when {
                direction[i] == 'w' -> x -= 2
                direction[i] == 'e' -> x += 2
                else -> {
                    y = if (direction[i] == 's') y - 1 else y + 1
                    x = if (direction[i + 1] == 'e') x + 1 else x - 1
                    i++
                }
            }
            i++
        }

        if (tile.containsKey(Pair(x, y)))
            tile[Pair(x, y)] = if (tile[Pair(x, y)] == true) false else true
        else
            tile[Pair(x, y)] = false
    }

    println(tile.values.count { color -> color == false })
    return tile
}

fun part02(): Int {
    var tile = part01()
    val (xList, yList) = Pair(tile.keys.map { it.first }, tile.keys.map { it.second })
    var (xMin, xMax) = Pair(xList.minOrNull()!!, xList.maxOrNull()!!)
    var (yMin, yMax) = Pair(yList.minOrNull()!!, yList.maxOrNull()!!)

    for (days in 0 until 100) {
        xMin -= 1; xMax += 1; yMin -= 1; yMax += 1

        val newTile = mutableMapOf<Pair<Int, Int>, Boolean>()
        for (j in yMax downTo yMin) {
            for (i in xMin..xMax) {
                if (!(i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0)) continue
                newTile[Pair(i, j)] = state(Pair(i, j), tile)
            }
        }

        tile = newTile
    }

    return tile.values.count { color -> color == false }
}

fun state(pair: Pair<Int, Int>, map: Map<Pair<Int, Int>, Boolean>): Boolean {
    val currentState = if (map.containsKey(pair)) map[pair]!! else true
    val (x, y) = Pair(pair.first, pair.second)

    var white = 0
    for (j in y + 1 downTo y - 1) {
        for (i in x - 2..x + 2) {
            if ((i == x && j == y) || !(i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0)) continue
            white = if (map.containsKey(Pair(i, j))) {
                if (map[Pair(i, j)] == true) white + 1 else white
            } else white + 1
        }
    }

    return if (currentState) {
        if (6 - white == 2) false else true
    } else (6 - white == 0 || 6 - white > 2)
}

fun main() {
    println("Advent of Code 2020, Day 24")
    println(part02()) // Calls part01()
}
