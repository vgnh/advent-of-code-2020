package day13

import (
	"advent-of-code-2020/utils"
	"fmt"
	"strconv"
	"strings"
)

const filename = "./day13/input.txt"

var input = utils.ReadLines(filename)

func part01() int {
	depart, _ := strconv.Atoi(input[0])
	bus := []int{}
	for _, str := range strings.Split(input[1], ",") {
		if str != "x" {
			num, _ := strconv.Atoi(str)
			bus = append(bus, num)
		}
	}
	busToTake := 0
	leastDiff := depart
	for _, id := range bus {
		time := 0
		for time < depart {
			time += id
		}

		diff := time - depart
		if diff < leastDiff {
			leastDiff = diff
			busToTake = id
		}
	}
	return leastDiff * busToTake
}

func part02() int {
	bus := strings.Split(input[1], ",")
	time := 0
	inc, _ := strconv.Atoi(bus[0])
	for i := 1; i < len(bus); i++ {
		if bus[i] != "x" {
			newTime, _ := strconv.Atoi(bus[i])
			for {
				time += inc
				if (time+i)%newTime == 0 {
					inc *= newTime
					break
				}
			}
		}
	}
	return time
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 13")
	fmt.Println(part01())
	fmt.Println(part02())
}
