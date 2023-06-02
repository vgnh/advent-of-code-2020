package day11

import (
	"advent-of-code-2020/utils"
	"fmt"
)

const filename = "./day11/input.txt"

var layout = func() [][]rune {
	strs := utils.ReadLines(filename)
	layout := make([][]rune, len(strs))
	for i := range layout {
		chars := []rune(strs[i])
		layout[i] = make([]rune, len(strs[0]))
		for j := range layout[i] {
			layout[i][j] = chars[j]
		}
	}
	return layout
}()

func part01(part int) int {
	layout := utils.Copy2d(layout)
	rows := len(layout)
	cols := len(layout[0])

	layout2 := utils.Copy2d(layout)
	for {
		stateChange := false
		for i := 0; i < rows; i++ {
			for j := 0; j < cols; j++ {
				if layout[i][j] == 'L' && !adjacentOccupied(part, layout, i, j) {
					stateChange = true
					layout2[i][j] = '#'
				} else if layout[i][j] == '#' {
					howMany := 4
					if part != 1 {
						howMany = 5
					}
					if occupiedBy(howMany, layout, i, j) {
						stateChange = true
						layout2[i][j] = 'L'
					}
				}

			}
		}
		if !stateChange {
			break
		} else {
			layout = utils.Copy2d(layout2)
		}
	}
	return occupiedCount(layout2)
}

func part02() int {
	return part01(2)
}

func adjacentOccupied(part int, layout [][]rune, row int, col int) bool {
	pos := [][2]int{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}}
	if part == 1 {
		for i := range pos {
			x := row + pos[i][0]
			y := col + pos[i][1]
			if isValid(layout, x, y) && layout[x][y] == '#' {
				return true
			}
		}
	} else {
		for i := range pos {
			x := row
			y := col

			for {
				x += pos[i][0]
				y += pos[i][1]
				if isValid(layout, x, y) {
					if layout[x][y] == '#' {
						return true
					} else if layout[x][y] == 'L' {
						break
					}
				} else {
					break
				}
			}
		}
	}
	return false
}

func isValid(layout [][]rune, x, y int) bool {
	return (x >= 0 && x < len(layout)) && (y >= 0 && y < len(layout[0]))
}

func occupiedBy(howMany int, layout [][]rune, row, col int) bool {
	occupied := 0
	pos := [][2]int{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}}
	if howMany == 4 {
		// part01()
		for i := range pos {
			x := row + pos[i][0]
			y := col + pos[i][1]
			if isValid(layout, x, y) && layout[x][y] == '#' {
				occupied++
			}
		}
	} else {
		// part02()
		for i := range pos {
			x := row
			y := col
			for {
				x += pos[i][0]
				y += pos[i][1]
				if isValid(layout, x, y) {
					if layout[x][y] == '#' {
						occupied++
						break
					} else if layout[x][y] == 'L' {
						break
					}
				} else {
					break
				}
			}
		}
	}
	return occupied >= howMany
}

func occupiedCount(layout [][]rune) int {
	occupied := 0
	for i := range layout {
		for j := range layout[0] {
			if layout[i][j] == '#' {
				occupied++
			}
		}
	}
	return occupied
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 11")
	fmt.Println(part01(1))
	fmt.Println(part02())
}
