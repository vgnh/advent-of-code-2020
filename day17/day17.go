package day17

import (
	"advent-of-code-2020/utils"
)

const filename = "./inputs/day17.txt"

var input = utils.ReadLines(filename)

type triple struct {
	first, second, third int
}

type tuple struct {
	first, second, third, fourth int
}

func part01() int {
	cube := make(map[triple]bool)
	y := len(input) - 1
	for i := range input {
		x, z := 0, 0
		for _, v := range input[i] {
			ch := v
			cube[triple{x, y, z}] = ch == '#'
			x++
		}
		y--
	}
	yMin, yMax, xMin, xMax, zMin, zMax := 0, len(input)-1, 0, len(input[0])-1, 0, 0

	for cycle := 0; cycle < 6; cycle++ {
		yMin--
		yMax++
		xMin--
		xMax++
		zMin--
		zMax++

		newCube := make(map[triple]bool)
		for j := yMax; j >= yMin; j-- {
			for i := xMin; i <= xMax; i++ {
				for k := zMin; k <= zMax; k++ {
					newCube[triple{i, j, k}] = state(triple{i, j, k}, cube)
				}
			}
		}

		cube = newCube
	}

	active := 0
	for _, value := range cube {
		if value {
			active++
		}
	}

	return active
}

func state(t triple, m map[triple]bool) bool {
	currentState := false
	if value, ok := m[t]; ok {
		currentState = value
	}
	x, y, z := t.first, t.second, t.third
	neighbours := 0
	for j := y + 1; j >= y-1; j-- {
		for i := x - 1; i <= x+1; i++ {
			for k := z - 1; k <= z+1; k++ {
				if i == x && j == y && k == z {
					continue
				}
				if value, ok := m[triple{i, j, k}]; ok && value {
					neighbours++
				}
			}
		}
	}
	if currentState {
		return (neighbours == 2 || neighbours == 3)
	}
	return neighbours == 3
}

func part02() int {
	cube := make(map[tuple]bool)
	y := len(input) - 1
	for i := range input {
		x, z, w := 0, 0, 0
		for _, v := range input[i] {
			ch := v
			cube[tuple{x, y, z, w}] = ch == '#'
			x++
		}
		y--
	}
	yMin, yMax, xMin, xMax, zMin, zMax, wMin, wMax := 0, len(input)-1, 0, len(input[0])-1, 0, 0, 0, 0

	for cycle := 0; cycle < 6; cycle++ {
		yMin--
		yMax++
		xMin--
		xMax++
		zMin--
		zMax++
		wMin--
		wMax++

		newCube := make(map[tuple]bool)
		for j := yMax; j >= yMin; j-- {
			for i := xMin; i <= xMax; i++ {
				for k := zMin; k <= zMax; k++ {
					for l := wMin; l <= wMax; l++ {
						newCube[tuple{i, j, k, l}] = state2(tuple{i, j, k, l}, cube)
					}
				}
			}
		}

		cube = newCube
	}

	active := 0
	for _, value := range cube {
		if value {
			active++
		}
	}

	return active
}

func state2(t tuple, m map[tuple]bool) bool {
	currentState := false
	if value, ok := m[t]; ok {
		currentState = value
	}
	x, y, z, w := t.first, t.second, t.third, t.fourth
	neighbours := 0
	for j := y + 1; j >= y-1; j-- {
		for i := x - 1; i <= x+1; i++ {
			for k := z - 1; k <= z+1; k++ {
				for l := w - 1; l <= w+1; l++ {
					if i == x && j == y && k == z && l == w {
						continue
					}
					if value, ok := m[tuple{i, j, k, l}]; ok && value {
						neighbours++
					}
				}
			}
		}
	}
	if currentState {
		return (neighbours == 2 || neighbours == 3)
	}
	return neighbours == 3
}

func Main() (int, func() int, func() int) {
	return 17, part01, part02
}
