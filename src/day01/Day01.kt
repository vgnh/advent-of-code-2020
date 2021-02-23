package day01

import java.io.File
import kotlin.math.abs

const val FILENAME = "src/day01/Input.txt"

val expenseReport = File(FILENAME).readLines().map { it.toInt() }

fun part01(): Int {
    val expenseList = mutableListOf<Int>()
    for (expense in expenseReport) {
        val diff = abs(2020 - expense)
        if (expenseList.contains(diff))
            return expense * diff
        else
            expenseList.add(expense)
    }
    return -1
}

fun part02(): Int {
    for (i in 0 until expenseReport.size - 1) {
        for (j in i + 1 until expenseReport.size) {
            val expenseOnePlusTwo = expenseReport[i] + expenseReport[j]
            val diff = 2020 - expenseOnePlusTwo
            if (!(expenseOnePlusTwo < 2020) && diff < 0)
                continue
            if (expenseReport.contains(diff))
                return diff * expenseReport[i] * expenseReport[j]
        }
    }
    return -1
}

fun main() {
    println("Advent of Code 2020, Day 01")
    println(part01())
    println(part02())
}
