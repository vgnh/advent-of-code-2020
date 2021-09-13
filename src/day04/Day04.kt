package day04

import java.io.File

const val FILENAME = "src/day04/Input.txt"

val passportList = File(FILENAME).readText().split("\n\n").map { it.split("\n").joinToString(" ") }

fun part01(): Int {
    var validPass = 0
    val reg = Regex("(?=.*byr:.*)(?=.*iyr:.*)(?=.*eyr:.*)(?=.*hgt:.*)(?=.*hcl:.*)(?=.*ecl:.*)(?=.*pid:.*)")
    for (passport in passportList) {
        if (reg.containsMatchIn(passport))
            validPass++
    }
    return validPass
}

fun part02(): Int {
    var validPass = 0
    // byr = "19[2-9][0-9]|200[0-2]"
    // iyr = "20(1[0-9]|20)"
    // eyr = "20(2[0-9]|30)"
    // hgt = "(1([5-8][0-9]|9[0-3])cm)|((59|6[0-9]|7[0-3])in)"
    // hcl = "#[0-9a-f]{6}"
    // ecl = "(amb|blu|brn|gry|grn|hzl|oth)"
    // pid = "[0-9]{9}"
    val reg =
            Regex("(?=.*byr:(19[2-9][0-9]|200[0-2]).*)(?=.*iyr:(20(1[0-9]|20)).*)(?=.*eyr:(20(2[0-9]|30)).*)(?=.*hgt:((1([5-8][0-9]|9[0-3])cm)|((59|6[0-9]|7[0-3])in)).*)(?=.*hcl:(#[0-9a-f]{6}).*)(?=.*ecl:(amb|blu|brn|gry|grn|hzl|oth).*)(?=.*pid:([0-9]{9}).*)")
    for (passport in passportList) {
        if (reg.containsMatchIn(passport))
            validPass++
    }
    return validPass
}

fun main() {
    println("Advent of Code 2020, Day 04")
    println(part01())
    println(part02())
}
