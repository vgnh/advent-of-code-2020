package day22

import java.io.File

const val FILENAME = "src/day22/Input.txt"

val input = File(FILENAME).readText().split("\n\n").map {
    val list = it.split("\n")
    list.subList(1, list.size).map { str -> str.toInt() }.toList()
}.toTypedArray()

fun part01(): Int {
    val player1 = input[0].toMutableList()
    val player2 = input[1].toMutableList()

    while (player1.any() && player2.any()) {
        val p1 = player1.removeAt(0)
        val p2 = player2.removeAt(0)
        if (p1 > p2) {
            player1.add(p1)
            player1.add(p2)
        } else {
            player2.add(p2)
            player2.add(p1)
        }
    }

    return if (player1.any()) winnerScore(player1) else winnerScore(player2)
}

fun winnerScore(deck: List<Int>): Int {
    var winnerScore = 0
    for (i in deck.indices)
        winnerScore += deck[i] * (deck.size - i)
    return winnerScore
}

fun part02(): Int {
    val player1 = input[0].toMutableList()
    val player2 = input[1].toMutableList()

    val p1Win = recursiveCombat(player1, player2)
    return if (p1Win) winnerScore(player1) else winnerScore(player2)
}

fun recursiveCombat(deck1: MutableList<Int>, deck2: MutableList<Int>): Boolean {
    val history = HashSet<Pair<String, String>>()

    while (deck1.any() && deck2.any()) {
        val s1 = deck1.joinToString(",")
        val s2 = deck2.joinToString(",")
        if (history.contains(Pair(s1, s2)))
            return true
        history.add(Pair(s1, s2))

        val p1 = deck1.removeAt(0)
        val p2 = deck2.removeAt(0)
        if (p1 <= deck1.size && p2 <= deck2.size) {
            val p1WinSubgame = recursiveCombat(deck1.take(p1).toMutableList(), deck2.take(p2).toMutableList())
            if (p1WinSubgame) {
                deck1.add(p1)
                deck1.add(p2)
            } else {
                deck2.add(p2)
                deck2.add(p1)
            }
        } else {
            if (p1 > p2) {
                deck1.add(p1)
                deck1.add(p2)
            } else {
                deck2.add(p2)
                deck2.add(p1)
            }
        }
    }

    // If deck1 is empty, player2 wins (returns false). If deck1 has cards, then deck2 must be empty, and player1 wins (returns true)
    return deck1.any()
}

fun main() {
    println("Advent of Code 2020, Day 22")
    println(part01())
    println(part02())
}
