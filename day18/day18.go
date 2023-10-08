package day18

import (
	"advent-of-code-2020/utils"
	"fmt"
	"slices"
	"strconv"
	"strings"
)

const filename = "./day18/input.txt"

var input = func() []string {
	input := utils.ReadLines(filename)
	for i := range input {
		input[i] = strings.ReplaceAll(input[i], " ", "")
	}
	return input
}()

func evaluate(str string) int {
	total := 0

	stack := utils.NewDequeFrom([]rune(str))
	for {
		ch, ok := stack.Pop()
		if !ok {
			break
		}

		if ch == '(' {
			total = evaluate(insideExpr(stack))
		} else if slices.Contains([]rune{'*', '+'}, ch) {
			if stack.Peek() == '(' {
				stack.Pop()
				if ch == '*' {
					total *= evaluate(insideExpr(stack))
				} else {
					total += evaluate(insideExpr(stack))
				}
			} else {
				r, _ := stack.Pop()
				n, _ := strconv.Atoi(string(r))
				if ch == '*' {
					total *= n
				} else {
					total += n
				}
			}
		} else {
			total, _ = strconv.Atoi(string(ch))
		}
	}

	return total
}

func insideExpr(stack *utils.Deque[rune]) string {
	var expr strings.Builder
	open := 0
	for {
		ch, _ := stack.Pop()
		if ch == '(' {
			open++
		} else if ch == ')' {
			if open != 0 {
				open--
			} else if open == 0 {
				break
			}
		}
		expr.WriteRune(ch)
	}
	return expr.String()
}

func evaluate2(str string) int {
	total := 0

	stack := utils.NewDequeFrom([]rune(str))
	for {
		ch, ok := stack.Pop()
		if !ok {
			break
		}

		if ch == '(' {
			total = evaluate2(insideExpr(stack))
		} else if slices.Contains([]rune{'*', '+'}, ch) {
			if ch == '+' {
				if stack.Peek() == '(' {
					stack.Pop()
					total += evaluate2(insideExpr(stack))
				} else {
					r, _ := stack.Pop()
					n, _ := strconv.Atoi(string(r))
					total += n
				}
			} else if ch == '*' {
				var expr strings.Builder
				for !stack.Empty() {
					r, _ := stack.Dequeue()
					expr.WriteRune(r)
				}
				total *= evaluate2(expr.String())
			}
		} else {
			total, _ = strconv.Atoi(string(ch))
		}
	}

	return total
}

func part01() int {
	sum := 0
	for _, v := range input {
		sum += evaluate(v)
	}
	return sum
}

func part02() int {
	sum := 0
	for _, v := range input {
		sum += evaluate2(v)
	}
	return sum
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 18")
	fmt.Println(part01())
	fmt.Println(part02())
}
