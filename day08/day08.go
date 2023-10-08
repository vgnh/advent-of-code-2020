package day08

import (
	"advent-of-code-2020/utils"
	"fmt"
	"strconv"
	"strings"
)

const filename = "./day08/input.txt"

var instructions = utils.ReadLines(filename)

func part01(runningPart02 bool) int {
	accumulator := 0
	reachedEnd := false
	executed := make([]bool, len(instructions))
	i := 0
	for i < len(instructions) {
		if i == len(instructions)-1 {
			reachedEnd = true
		}
		if executed[i] {
			break
		}
		switch {
		case strings.Contains(instructions[i], "nop"):
			executed[i] = true
			i++
		case strings.Contains(instructions[i], "acc"):
			num, _ := strconv.Atoi(instructions[i][4:])
			accumulator += num
			executed[i] = true
			i++
		case strings.Contains(instructions[i], "jmp"):
			num, _ := strconv.Atoi(instructions[i][4:])
			jumpTo := i + num
			executed[i] = true
			i = jumpTo
		}
	}

	// If running only part01, return accumulator as it is
	if !runningPart02 {
		return accumulator
	}

	// If running again for part02, return accumulator only if the final instruction
	// is reached, else, return -1, as final instruction has not been reached.
	if reachedEnd {
		return accumulator
	} else {
		return -1
	}
}

func part02() int {
	for i := range instructions {
		instruction := []rune(instructions[i])
		if instruction[0] != 'a' {
			beforeReplace := instructions[i]
			if instruction[0] == 'n' {
				instructions[i] = strings.ReplaceAll(instructions[i], "nop", "jmp")
			} else if instruction[0] == 'j' {
				instructions[i] = strings.ReplaceAll(instructions[i], "jmp", "nop")
			}

			accumulator := part01(true)
			if accumulator > 0 {
				return accumulator
			} else {
				instructions[i] = beforeReplace
			}
		}
	}
	return -1
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 08")
	fmt.Println(part01(false))
	fmt.Println(part02())
}
