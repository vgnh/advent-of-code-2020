package day09

import (
	"advent-of-code-2020/utils"
	"slices"
)

const filename = "./inputs/day09.txt"

var xmas = utils.MapToInt(utils.ReadLines(filename))

var invalid = make(chan int, 1)

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
			invalid <- n
			return n
		}
	}
	invalid <- -1
	return -1
}

func part02() int {
	invalid := <-invalid
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

func Main() (int, func() int, func() int) {
	return 9, part01, part02
}
