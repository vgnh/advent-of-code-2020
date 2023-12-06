package day15

import (
	"advent-of-code-2020/utils"
	"strings"
)

const filename = "./inputs/day15.txt"

var starting = utils.MapToInt(strings.Split(utils.ReadString(filename), ","))

func numberSpoken(limit int) int {
	prevOccurrence := make([]int, limit)
	// prevOccurrence := make(map[int]int)
	turn := 0
	for _, i := range starting {
		turn++
		prevOccurrence[i] = turn
	}

	curr := starting[len(starting)-1]
	next := 0
	for turn < limit {
		turn++
		curr = next
		next = 0
		// Uncomment when using array
		if prevOccurrence[curr] != 0 {
			next = turn - prevOccurrence[curr]
		}
		// Uncomment when using map
		// if value, ok := prevOccurrence[curr]; ok {
		// 	next = turn - value
		// }
		prevOccurrence[curr] = turn
	}

	return curr
}

func part01() int {
	return numberSpoken(2020)
}

func part02() int {
	return numberSpoken(30_000_000)
}

func Main() (int, func() int, func() int) {
	return 15, part01, part02
}
