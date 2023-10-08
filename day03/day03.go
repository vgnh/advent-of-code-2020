package day03

import (
	"advent-of-code-2020/utils"
	"fmt"
)

const filename = "./day03/input.txt"

var terrain = func() [][]rune {
	input := utils.ReadLines(filename)
	terrain := make([][]rune, len(input))
	for i, str := range input {
		terrain[i] = []rune(str)
	}
	return terrain
}()

func treeCount(right, down int) int {
	treeCount := 0
	i := 0
	j := 0
	for i < len(terrain)-1 {
		j += right
		i += down
		if j >= len(terrain[0]) {
			j %= len(terrain[0])
		}
		if terrain[i][j] == '#' {
			treeCount++
		}
	}
	return treeCount
}

func part01() int {
	return treeCount(3, 1)
}

func part02() int {
	return treeCount(1, 1) * treeCount(3, 1) * treeCount(5, 1) * treeCount(7, 1) * treeCount(1, 2)
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 03")
	fmt.Println(part01())
	fmt.Println(part02())
}
