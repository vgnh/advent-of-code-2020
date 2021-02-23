package day02

import java.io.File

const val FILENAME = "src/day02/Input.txt"

val passwordPolicies = File(FILENAME).readLines()

class Policy(str: String) {
    val min: Int
    val max: Int
    val letter: Char
    val password: String

    init {
        val policyAndPass = str.split(": ")
        password = policyAndPass[1]
        letter = policyAndPass[0].split(" ")[1].single()
        min = policyAndPass[0].split(" ")[0].split("-")[0].toInt()
        max = policyAndPass[0].split(" ")[0].split("-")[1].toInt()
    }
}

fun part01(): Int {
    var validCount = 0
    for (str in passwordPolicies) {
        val policy = Policy(str)
        val letterCount = policy.password.count { policy.letter == it }
        if (letterCount >= policy.min && letterCount <= policy.max)
            validCount++
    }
    return validCount
}

fun part02(): Int {
    var validCount = 0
    for (str in passwordPolicies) {
        val policy = Policy(str)
        if ((policy.letter == policy.password[policy.min - 1]) xor (policy.letter == policy.password[policy.max - 1]))
            validCount++
    }
    return validCount
}

fun main() {
    println("Advent of Code 2020, Day 02")
    println(part01())
    println(part02())
}
