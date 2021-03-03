package day07

import java.io.File

const val FILENAME = "src/day07/Input.txt"

val ruleMap = File(FILENAME).readLines().map {
    it.substring(0, it.length - 1).replace(" bags", "").replace(" bag", "")
}.map {
    it.split(" contain ")[0] to it.split(" contain ")[1]
}.toMap()

fun part01(): Int {
    var bagCount = 0
    for (key in ruleMap.keys) {
        if (hasShinyGold(key))
            bagCount++
    }
    return bagCount
}

fun hasShinyGold(str: String): Boolean {
    if (ruleMap[str]!!.contains("shiny gold"))
        return true
    else {
        for (value in ruleMap[str]!!.split(", ")) {
            if (value != "no other") {
                if (!hasShinyGold(value.substring(2)))
                    continue
                else
                    return true
            }
        }
    }
    return false
}

fun part02(bagColor: String = "shiny gold"): Int {
    var totalBags = 0
    for (s in ruleMap[bagColor]!!.split(", ")) {
        if (s != "no other") {
            val num = s.substring(0, 1).toInt()
            totalBags += num + num * part02(s.substring(2))
        } else
            break
    }
    return totalBags
}

fun main() {
    println("Advent of Code 2020, Day 07")
    println(part01())
    println(part02())
}

// Alternate but slower part01
/*fun part01(): Int {
    val bagsWithSG = mutableListOf<String>()
    val queue = mutableListOf("shiny gold")
    while (queue.any()) {
        val bag = queue[0]
        queue.removeAt(0)

        val newKeys = ruleMap.filter { Regex(".*$bag.*").containsMatchIn(it.value) }.keys
        for (str in newKeys) {
            if (!bagsWithSG.contains(str)) {
                bagsWithSG.add(str)
                queue.add(str)
            }
        }
    }
    return bagsWithSG.size
}*/
