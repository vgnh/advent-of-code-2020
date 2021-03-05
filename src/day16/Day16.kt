package day16

import java.io.File

const val FILENAME = "src/day16/Input.txt"

val input = File(FILENAME).readText().split("\n\n").toTypedArray()

val rules = input[0].split("\n").toTypedArray()

val list = input[2].split("\n")
val nearbyTickets = list.subList(1, list.size).toTypedArray()

val myTicket = input[1].split("\n")[1].split(",").map { it.toInt() }.toTypedArray()

fun part01(): Int {
    val listOfRanges = mutableListOf<(Int) -> Boolean>()

    for (rule in rules) {
        val reg = Regex(".*: ([0-9]*)-([0-9]*) or ([0-9]*)-([0-9]*)").find(rule)!!.groupValues
        val (r1, r2, r3, r4) = arrayOf(reg[1].toInt(), reg[2].toInt(), reg[3].toInt(), reg[4].toInt())
        listOfRanges.add(fun(num) = (r1 <= num && num <= r2) || (r3 <= num && num <= r4))
    }

    // Convert each string of comma separated values in nearbyTickets to an int[]
    // Then with errorRate initialized to 0
    // Take each array, select those numbers in the array that do not return true for even one of the ranges
    // Then with total initialized to errorRate, add each of them to total
    // Then errorRate <- total
    // Finally once each array in the list is exhausted, return errorRate
    return nearbyTickets.map { str -> str.split(",").map(Integer::parseInt).toTypedArray() }
        .fold(0, { errorRate, arr ->
            arr.filter { num -> !listOfRanges.any { fn -> fn(num) } }.fold(errorRate, { total, next -> total + next })
        })
}

fun part02(): Long {
    var departure = 1L

    val listOfRanges = mutableListOf<(Int) -> Boolean>()
    for (rule in rules) {
        val reg = Regex(".*: ([0-9]*)-([0-9]*) or ([0-9]*)-([0-9]*)").find(rule)!!.groupValues
        val (r1, r2, r3, r4) = arrayOf(reg[1].toInt(), reg[2].toInt(), reg[3].toInt(), reg[4].toInt())
        listOfRanges.add(fun(num) = (r1 <= num && num <= r2) || (r3 <= num && num <= r4))
    }
    val validTicketList = nearbyTickets.map {
        it.split(",").map(Integer::parseInt).toTypedArray()
    }.filter { arr -> arr.all { num -> listOfRanges.any { fn -> fn(num) } } }
    val validTickets2D = Array<IntArray>(validTicketList.size) { IntArray(validTicketList[0].size) }
    for (i in validTickets2D.indices) {
        for (j in validTickets2D[0].indices) {
            validTickets2D[i][j] = validTicketList[i][j]
        }
    }

    var ruleArrMap = mutableMapOf<String, BooleanArray>()
    for (rule in rules) {
        val reg = Regex(".*: ([0-9]*)-([0-9]*) or ([0-9]*)-([0-9]*)").find(rule)!!.groupValues
        val (r1, r2, r3, r4) = arrayOf(reg[1].toInt(), reg[2].toInt(), reg[3].toInt(), reg[4].toInt())

        val colArr = BooleanArray(validTickets2D[0].size)
        for (j in validTickets2D[0].indices) {
            var valid = true
            for (i in validTickets2D.indices) {
                if (!(validTickets2D[i][j] in r1..r2 || validTickets2D[i][j] in r3..r4)) {
                    valid = false
                    break
                }
            }
            if (valid) colArr[j] = true
        }
        ruleArrMap[reg[0]] = colArr
    }
    ruleArrMap = ruleArrMap.toSortedMap(
        compareBy { key -> ruleArrMap[key]!!.count { value -> value == true } }
    ).toMutableMap()

    val colIgnoreList = mutableListOf<Int>()
    for (key in ruleArrMap.keys) {
        val arr = ruleArrMap[key]!!
        for (col in colIgnoreList) arr[col] = false
        if (arr.count { it == true } == 1) {
            val index = arr.indexOf(true)
            if (key.startsWith("departure")) departure *= myTicket[index]
            colIgnoreList.add(index)
        }
    }

    return departure
}

fun main() {
    println("Advent of Code 2020, Day 16")
    println(part01())
    println(part02())
}
