package day09

import (
	"advent-of-code-2020/utils"
	"fmt"
	"slices"
)

const filename = "./day09/input.txt"

var xmas = utils.MapToInt(utils.ReadLines(filename))

func part01() int {
	preambleSize := 25
	preamble := xmas[:preambleSize]
	remaining := xmas[preambleSize:]

	for len(remaining) > 0 {
		n := remaining[0]
		exists := false
		for _, i := range preamble {
			if slices.Contains(preamble, n-i) {
				exists = true
				preamble = append(preamble, n)
				remaining = remaining[1:]
				preamble = preamble[1:]
				break
			}
		}
		if !exists {
			fmt.Println(n)
			return n
		}
	}
	return -1
}

func part02() int {
	invalid := part01()
	for i := 2; i <= len(xmas); i++ {
		for j := 0; j <= len(xmas)-i; j++ {
			contiguous := xmas[j:(j + i)]
			if utils.Sum(contiguous) == invalid {
				return slices.Min(contiguous) + slices.Max(contiguous)
			}
		}
	}
	return -1
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 09")
	fmt.Println(part02()) // Calls part01() as well
}
