package day23

import (
	"advent-of-code-2020/utils"
	"fmt"
	"slices"
	"strconv"
	"strings"
)

const filename = "./day23/input.txt"

var cups2 = func() []int {
	runes := []rune(utils.ReadString(filename))
	cups := make([]int, len(runes))
	for i, r := range runes {
		cups[i], _ = strconv.Atoi(string(r))
	}
	return cups
}()

func part01(runPart02 bool) string {
	cups := make([]int, len(cups2))
	copy(cups, cups2)

	currentCup := cups[0]
	minCup := slices.Min(cups)
	if runPart02 {
		for i := slices.Max(cups) + 1; i <= 1_000_000; i++ {
			cups = append(cups, i)
		}
	}
	maxCup := slices.Max(cups)

	// next := make(map[int]int) // Alternate, but slower method
	next := make([]int, len(cups)+1)
	for i := 0; i < len(cups)-1; i++ {
		next[cups[i]] = cups[i+1]
	}
	next[cups[len(cups)-1]] = cups[0]

	moves := 100
	if runPart02 {
		moves = 10_000_000
	}
	for i := 0; i < moves; i++ {
		var threeCups []int
		cupToInsert := currentCup
		for j := 0; j < 3; j++ {
			cupToInsert = next[cupToInsert]
			threeCups = append(threeCups, cupToInsert)
		}

		destCup := currentCup - 1
		if currentCup-1 < minCup {
			destCup = maxCup
		}
		for slices.Contains(threeCups, destCup) {
			if destCup-1 < minCup {
				destCup = maxCup
			} else {
				destCup -= 1
			}
		}

		// Remove the three cups from middle
		next[currentCup] = next[threeCups[len(threeCups)-1]]

		// Add three cups after destination cup
		nextOfDest := next[destCup]
		next[destCup] = threeCups[0]
		next[threeCups[len(threeCups)-1]] = nextOfDest

		currentCup = next[currentCup]
	}

	if runPart02 {
		return fmt.Sprint(next[1] * next[next[1]])
	} else {
		var cupOrder strings.Builder
		tempCup := next[1]
		for {
			cupOrder.WriteString(fmt.Sprint(tempCup))
			tempCup = next[tempCup]
			if tempCup == 1 {
				return cupOrder.String()
			}
		}
	}
}

func part02() string {
	return part01(true)
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 23")
	fmt.Println(part01(false))
	fmt.Println(part02())
}
