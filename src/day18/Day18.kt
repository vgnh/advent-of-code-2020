package day18

import java.io.File
import java.util.Stack
import java.util.EmptyStackException

const val FILENAME = "src/day18/Input.txt"

val input = File(FILENAME).readLines().map { it.replace(" ", "") }.toTypedArray()

fun evaluate(str: String): Long {
    var total = 0L

    val stack = Stack<Char>()
    stack.addAll(str.reversed().toList())
    while (true) {
        var ch: Char
        try {
            ch = stack.pop()
        } catch (ese: EmptyStackException) {
            break
        }

        if (ch == '(')
            total = evaluate(insideExpr(stack))
        else if ("*+".contains(ch)) {
            if (stack.peek() == '(') {
                stack.pop()
                if (ch == '*')
                    total *= evaluate(insideExpr(stack))
                else
                    total += evaluate(insideExpr(stack))
            } else {
                if (ch == '*')
                    total *= "${stack.pop()}".toInt()
                else
                    total += "${stack.pop()}".toInt()
            }
        } else
            total = "$ch".toLong()
    }

    return total
}

fun insideExpr(stack: Stack<Char>): String {
    val expr = StringBuilder("")
    var open = 0
    while (true) {
        val ch = stack.pop()
        if (ch == '(')
            open++
        else if (ch == ')') {
            if (open != 0)
                open--
            else if (open == 0)
                break
        }
        expr.append(ch)
    }
    return expr.toString()
}

fun evaluate2(str: String): Long {
    var total = 0L

    val stack = Stack<Char>()
    stack.addAll(str.reversed().toList())
    while (true) {
        var ch: Char
        try {
            ch = stack.pop()
        } catch (ese: EmptyStackException) {
            break
        }

        if (ch == '(')
            total = evaluate2(insideExpr(stack))
        else if ("*+".contains(ch)) {
            if (ch == '+') {
                if (stack.peek() == '(') {
                    stack.pop()
                    total += evaluate2(insideExpr(stack))
                } else
                    total += "${stack.pop()}".toInt()
            } else if (ch == '*') {
                val expr = stack.joinToString("").reversed()
                stack.clear()
                total *= evaluate2(expr)
            }
        } else
            total = "$ch".toLong()
    }

    return total
}

fun part01() = input.map { evaluate(it) }.fold(0L, { sum, next -> sum + next })

fun part02() = input.map { evaluate2(it) }.fold(0L, { sum, next -> sum + next })

fun main() {
    println("Advent of Code 2020, Day 18")
    println(part01())
    println(part02())
}
