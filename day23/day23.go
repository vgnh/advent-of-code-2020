package day23

import (
	"advent-of-code-2020/utils"
	"slices"
	"strconv"
	"strings"
)

const filename = "./inputs/day23.txt"

var cups2 = func() []int {
	runes := []rune(utils.ReadString(filename))
	cups := make([]int, len(runes))
	for i, r := range runes {
		cups[i], _ = strconv.Atoi(string(r))
	}
	return cups
}()

func labels(part02 bool) int {
	cups := make([]int, len(cups2))
	copy(cups, cups2)

	currentCup := cups[0]
	minCup := slices.Min(cups)
	if part02 {
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
	if part02 {
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

	if part02 {
		return next[1] * next[next[1]]
	} else {
		var cupOrder strings.Builder
		tempCup := next[1]
		for {
			cupOrder.WriteString(strconv.Itoa(tempCup))
			tempCup = next[tempCup]
			if tempCup == 1 {
				co, _ := strconv.Atoi(cupOrder.String())
				return co
			}
		}
	}
}

func part01() int {
	return labels(false)
}

func part02() int {
	return labels(true)
}

func Main() (int, func() int, func() int) {
	return 23, part01, part02
}
