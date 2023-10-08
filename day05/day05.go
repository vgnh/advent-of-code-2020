package day05

import (
	"advent-of-code-2020/utils"
	"fmt"
	"math"
	// "sort"
	// "strconv"
	// "strings"
)

const filename = "./day05/input.txt"

var seatList = utils.ReadLines(filename)

var occupiedList = make([]bool, ((127*8)+7)+1) // 0-1023, total = 1024, init to false

func part01() int {
	highestSeatId := 0
	for _, seat := range seatList {
		low := 0
		up := 127
		for _, ch := range seat[:7] {
			if ch == 'F' {
				up = int(math.Floor(float64(low+up) / 2.0))
			} else if ch == 'B' {
				low = int(math.Ceil(float64(low+up) / 2.0))
			}
		}
		row := up
		if seat[6] == 'F' {
			row = low
		}

		low = 0
		up = 7
		for _, ch := range seat[7:] {
			if ch == 'L' {
				up = int(math.Floor(float64(low+up) / 2.0))
			} else if ch == 'R' {
				low = int(math.Ceil(float64(low+up) / 2.0))
			}
		}
		col := up
		if seat[9] == 'L' {
			col = low
		}

		seatId := (row * 8) + col
		occupiedList[seatId] = true // for part02
		if seatId > highestSeatId {
			highestSeatId = seatId
		}
	}
	return highestSeatId
}

func part02() int {
	skippedInitSeats := false
	for i := range occupiedList {
		if occupiedList[i] && skippedInitSeats == false {
			skippedInitSeats = true
		}
		if skippedInitSeats && occupiedList[i] == false {
			return i
		}
	}
	return -1
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 05")
	fmt.Println(part01())
	fmt.Println(part02())
}

// Alternate solution
/* var seatList = func() []int {
	lines := utils.ReadLines(filename)
	seatList := make([]int, len(lines))
	for i, str := range lines {
		num64, _ := strconv.ParseInt(strings.ReplaceAll(strings.ReplaceAll(strings.ReplaceAll(strings.ReplaceAll(str, "F", "0"), "B", "1"), "L", "0"), "R", "1"), 2, 64)
		seatList[i] = int(num64)
	}
	return seatList
}()

func part01() int {
	return utils.Max(seatList)
}

func part02() int {
	sort.Ints(seatList)
	for i := 0; i < len(seatList)-1; i++ {
		if seatList[i+1]-seatList[i] > 1 {
			return seatList[i] + 1
		}
	}
	return -1
} */
