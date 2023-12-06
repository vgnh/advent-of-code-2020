package day08

import (
	"advent-of-code-2020/utils"
	"strconv"
	"strings"
)

const filename = "./inputs/day08.txt"

var instructions = utils.ReadLines(filename)

func getAccumulator(part02 bool) int {
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
	if !part02 {
		return accumulator
	}

	// If running for part02, return accumulator only if the final instruction
	// is reached, else, return -1, as final instruction has not been reached.
	if reachedEnd {
		return accumulator
	} else {
		return -1
	}
}

func part01() int {
	return getAccumulator(false)
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

			accumulator := getAccumulator(true)
			if accumulator > 0 {
				return accumulator
			} else {
				instructions[i] = beforeReplace
			}
		}
	}
	return -1
}

func Main() (int, func() int, func() int) {
	return 8, part01, part02
}
