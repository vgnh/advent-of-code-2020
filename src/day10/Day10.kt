package day10

import java.io.File

const val FILENAME = "src/day10/Input.txt"

val adapters = File(FILENAME).readLines().map { it.toInt() }

fun allAdapters(): List<Int> {
    val adapters = adapters.toMutableList()
    adapters.sort()
    adapters.add(0, 0)
    adapters.add(adapters[adapters.size - 1] + 3)
    return adapters
}

fun part01(): Int {
    val adapters = allAdapters()
    var oneCount = 0
    var threeCount = 0
    for (i in 0 until adapters.size - 1) {
        if (adapters[i + 1] - adapters[i] == 1)
            oneCount++
        else if (adapters[i + 1] - adapters[i] == 3)
            threeCount++
    }
    return oneCount * threeCount
}

fun part02(_index: Int? = null, _ways: LongArray? = null, _adapters: List<Int>? = null): Long {
    val adapters = _adapters ?: allAdapters()
    val ways = _ways ?: LongArray(adapters.size)
    val index = _index ?: adapters.size - 1

    if (index == 0)
        return 1

    if (ways[index] != 0L)
        return ways[index]
    else {
        ways[index] = 0
        for (j in index - 1 downTo 0) {
            if (adapters[index] - adapters[j] <= 3)
                ways[index] += part02(j, ways, adapters)
            else
                break
        }
        return ways[index]
    }
}

fun main() {
    println("Advent of Code 2020, Day 10")
    println(part01())
    println(part02())
}

// Alternate shorter, cleaner and non-recursive solution for part02()
/*fun part02(): Long {
    val adapters = allAdapters()
    val ways = LongArray(adapters.size)
    for (index in ways.indices) {
        if (index == 0)
            ways[index] = 1
        else {
            ways[index] = 0
            for (j in index - 1 downTo 0) {
                if (adapters[index] - adapters[j] <= 3)
                    ways[index] += ways[j]
                else
                    break
            }
        }
    }
    return ways[ways.size - 1]
}*/
