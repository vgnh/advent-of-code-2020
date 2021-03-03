package day05

import java.io.File
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

const val FILENAME = "src/day05/Input.txt"

val seatList = File(FILENAME).readLines().map { it.trim() }

val occupiedList = BooleanArray(((127 * 8) + 7) + 1) // 0-1023, total = 1024, init to false

fun part01(): Int {
    var highestSeatID = 0
    for (seat in seatList) {
        var lRange = 0
        var uRange = 127
        for (ch in seat.substring(0, 7)) {
            if (ch == 'F')
                uRange = floor((lRange + uRange) / 2.0).toInt()
            else if (ch == 'B')
                lRange = ceil((lRange + uRange) / 2.0).toInt()
        }
        val row = if (seat[6] == 'F') lRange else uRange

        lRange = 0
        uRange = 7
        for (ch in seat.substring(7)) {
            if (ch == 'L')
                uRange = floor((lRange + uRange) / 2.0).toInt()
            else if (ch == 'R')
                lRange = ceil((lRange + uRange) / 2.0).toInt()
        }
        val col = if (seat[9] == 'L') lRange else uRange

        val seatID = (row * 8) + col
        occupiedList[seatID] = true // for part02
        if (seatID > highestSeatID)
            highestSeatID = seatID
    }
    return highestSeatID
}

fun part02(): Int {
    var skippedInitSeats = false
    for (i in occupiedList.indices) {
        if (occupiedList[i] && skippedInitSeats == false)
            skippedInitSeats = true
        if (skippedInitSeats && occupiedList[i] == false)
            return i
    }
    return -1
}

fun main() {
    println("Advent of Code 2020, Day 05")
    println(part01())
    println(part02())
}

// Alternate solution
/*val seatList = File(FILENAME).readLines().map {
    it.trim().replace('F', '0').replace('B', '1').replace('L', '0').replace('R', '1').toInt(2)
}.toTypedArray()

fun part01() = seatList.maxOrNull()

fun part02(): Int {
    Arrays.sort(seatList)
    for (i in 0 until seatList.size - 1) {
        if (seatList[i + 1] - seatList[i] > 1)
            return seatList[i] + 1
    }
    return -1
}*/
