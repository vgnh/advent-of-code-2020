package day14

import java.io.File
import java.lang.StringBuilder

const val FILENAME = "src/day14/Input.txt"

val input = File(FILENAME).readLines().toTypedArray()

fun part01(): Long {
    var mask = input[0].split(" = ")[1]
    val mem = mutableMapOf<Long, Long>()

    for (i in 1 until input.size) {
        if (input[i].startsWith("mem")) {
            val temp = input[i].split(" = ")
            val addr = temp[0].substring(4, temp[0].length - 1).toLong()
            val num = StringBuilder(Integer.toBinaryString(temp[1].toInt()).padStart(36, '0'))

            for (j in mask.indices) {
                if (mask[j] == 'X') continue
                num[j] = mask[j]
            }
            mem[addr] = num.toString().toLong(2)
        } else
            mask = input[i].split(" = ")[1]
    }

    return mem.values.sum()
}

fun part02(): Long {
    var mask = input[0].split(" = ")[1]
    val mem = mutableMapOf<Long, Long>()

    for (i in 1 until input.size) {
        if (input[i].startsWith("mem")) {
            val temp = input[i].split(" = ")
            val addr = StringBuilder(
                    Integer.toBinaryString(temp[0].substring(4, temp[0].length - 1).toInt()).padStart(36, '0')
            )
            val num = temp[1].toLong()

            for (j in mask.indices) {
                if (mask[j] == '0') continue
                addr[j] = mask[j]
            }

            val validList = mutableListOf<String>()

            // Generate valid addresses from initial masked address
            fun generateValidList(s: String) {
                val x = s.indexOf('X')
                if (x != -1) {
                    val sb = StringBuilder(s)
                    sb[x] = '0'
                    generateValidList(sb.toString())
                    sb[x] = '1'
                    generateValidList(sb.toString())
                } else
                    validList.add(s)
            }
            generateValidList(addr.toString())
            // Alternate but slower way of generating valid addresses
            /*val queue = mutableListOf<String>(addr.toString())
            while (queue.any()) {
                val str = StringBuilder(queue[0])
                queue.removeAt(0)

                val x = str.toString().indexOf('X')
                if (x != -1) {
                    str[x] = '0'
                    queue.add(str.toString())
                    str[x] = '1'
                    queue.add(str.toString())
                } else
                    validList.add(str.toString())
            }*/

            for (a in validList)
                mem[a.toLong(2)] = num
        } else
            mask = input[i].split(" = ")[1]
    }

    return mem.values.sum()
}

fun main() {
    println("Advent of Code 2020, Day 14")
    println(part01())
    println(part02())
}
