package day15

import (
	"advent-of-code-2020/utils"
	"fmt"
	"strings"
)

const filename = "./day15/input.txt"

var starting = utils.MapToInt(strings.Split(utils.ReadString(filename), ","))

func part01(limit int) int {
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

func part02() int {
	return part01(30_000_000)
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 15")
	fmt.Println(part01(2020))
	fmt.Println(part02())
}
