package day24

import (
	"advent-of-code-2020/utils"
	"fmt"
	"slices"
)

const filename = "./day24/input.txt"

var directions = utils.ReadLines(filename)

type pair struct {
	first, second int
}

func part01() map[pair]bool {
	tile := make(map[pair]bool)
	tile[pair{0, 0}] = true

	for _, direction := range directions {
		x, y := 0, 0

		i := 0
		for i < len(direction) {
			switch {
			case direction[i] == 'w':
				x -= 2
			case direction[i] == 'e':
				x += 2
			default:
				if direction[i] == 's' {
					y--
				} else {
					y++
				}
				if direction[i+1] == 'e' {
					x++
				} else {
					x--
				}
				i++
			}
			i++
		}

		if value, ok := tile[pair{x, y}]; ok {
			tile[pair{x, y}] = !value
		} else {
			tile[pair{x, y}] = false
		}
	}

	count := 0
	for _, color := range tile {
		if !color {
			count++
		}
	}
	fmt.Println(count)
	return tile
}

func part02() int {
	tile := part01()
	xList, yList := make([]int, len(tile)), make([]int, len(tile))
	l := 0
	for key := range tile {
		xList[l] = key.first
		yList[l] = key.second
		l++
	}
	xMin, xMax, yMin, yMax := slices.Min(xList), slices.Max(xList), slices.Min(yList), slices.Max(yList)

	for days := 0; days < 100; days++ {
		xMin--
		xMax++
		yMin--
		yMax++

		newTile := make(map[pair]bool)
		for j := yMax; j >= yMin; j-- {
			for i := xMin; i <= xMax; i++ {
				if !(i%2 == 0 && j%2 == 0 || i%2 != 0 && j%2 != 0) {
					continue
				}
				newTile[pair{i, j}] = state(&pair{i, j}, &tile)
			}
		}

		tile = newTile
	}

	count := 0
	for _, color := range tile {
		if !color {
			count++
		}
	}
	return count
}

func state(p *pair, tile *map[pair]bool) bool {
	currentState := true
	if value, ok := (*tile)[*p]; ok {
		currentState = value
	}
	x, y := p.first, p.second

	white := 0
	for j := y + 1; j >= y-1; j-- {
		for i := x - 2; i <= x+2; i++ {
			if (i == x && j == y) || !(i%2 == 0 && j%2 == 0 || i%2 != 0 && j%2 != 0) {
				continue
			}
			if value, ok := (*tile)[pair{i, j}]; ok {
				if value {
					white++
				}
			} else {
				white++
			}
		}
	}

	if currentState {
		return !(6-white == 2)
	} else {
		return (6-white == 0 || 6-white > 2)
	}
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 24")
	fmt.Println(part02()) // Calls part01()
}
