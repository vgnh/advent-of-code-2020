package day12

import (
	"advent-of-code-2020/utils"
	"math"
	"strconv"
)

const filename = "./inputs/day12.txt"

var instructions = utils.ReadLines(filename)

var values = map[rune][]int{
	'N': {0, 1},
	'E': {1, 0},
	'S': {0, -1},
	'W': {-1, 0},
}

func part01() int {
	pos := 1 // "NESW"[pos] is 'E'
	x := 0
	y := 0

	for _, ins := range instructions {
		num, _ := strconv.Atoi(ins[1:])
		if ins[0] == 'F' {
			x += values[[]rune("NESW")[pos]][0] * num
			y += values[[]rune("NESW")[pos]][1] * num
		} else if v, ok := values[[]rune(ins)[0]]; ok {
			x += v[0] * num
			y += v[1] * num
		} else {
			turns := num / 90
			for i := 0; i < turns; i++ {
				if ins[0] == 'R' {
					if pos+1 == 4 {
						pos = 0
					} else {
						pos++
					}
				} else if ins[0] == 'L' {
					if pos-1 == -1 {
						pos = 3
					} else {
						pos--
					}
				}
			}
		}
	}

	return int(math.Abs(float64(x)) + math.Abs(float64(y)))
}

func part02() int {
	x := 0
	y := 0
	wayX := 10
	wayY := 1

	for _, ins := range instructions {
		num, _ := strconv.Atoi(ins[1:])
		if ins[0] == 'F' {
			x += wayX * num
			y += wayY * num
		} else if v, ok := values[[]rune(ins)[0]]; ok {
			wayX += v[0] * num
			wayY += v[1] * num
		} else {
			turns := num / 90
			for i := 0; i < turns; i++ {
				if ins[0] == 'R' {
					swap := wayY
					wayY = -1 * wayX
					wayX = swap
				} else if ins[0] == 'L' {
					swap := wayX
					wayX = -1 * wayY
					wayY = swap
				}
			}
		}
	}

	return int(math.Abs(float64(x)) + math.Abs(float64(y)))
}

func Main() (int, func() int, func() int) {
	return 12, part01, part02
}
