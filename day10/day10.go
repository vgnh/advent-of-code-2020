package day10

import (
	"advent-of-code-2020/utils"
	"fmt"
	"slices"
	"sort"
)

const filename = "./day10/input.txt"

var allAdapters = func() []int {
	adapters := utils.MapToInt(utils.ReadLines(filename))
	sort.Ints(adapters)
	adapters = slices.Insert(adapters, 0, 0)
	adapters = append(adapters, adapters[len(adapters)-1]+3)
	return adapters
}()

func part01() int {
	oneCount := 0
	threeCount := 0
	for i := 0; i < len(allAdapters)-1; i++ {
		if allAdapters[i+1]-allAdapters[i] == 1 {
			oneCount++
		} else if allAdapters[i+1]-allAdapters[i] == 3 {
			threeCount++
		}
	}
	return oneCount * threeCount
}

func part02(_index int, _ways []int, _adapters []int) int {
	index, ways, adapters := _index, _ways, _adapters
	if adapters == nil {
		adapters = allAdapters
	}
	if ways == nil {
		ways = make([]int, len(adapters))
	}
	if index == -1 {
		index = len(adapters) - 1
	}

	if index == 0 {
		return 1
	}

	if ways[index] != 0 {
		return ways[index]
	}
	ways[index] = 0
	for j := index - 1; j >= 0; j-- {
		if adapters[index]-adapters[j] <= 3 {
			ways[index] += part02(j, ways, adapters)
		} else {
			break
		}
	}
	return ways[index]
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 10")
	fmt.Println(part01())
	fmt.Println(part02(-1, nil, nil))
}

// Alternate shorter, cleaner and non-recursive solution for part02()
/* func part02() int {
	ways := make([]int, len(allAdapters))
	for i := range ways {
		if i == 0 {
			ways[i] = 1
		} else {
			ways[i] = 0
			for j := i - 1; j >= 0; j-- {
				if allAdapters[i]-allAdapters[j] <= 3 {
					ways[i] += ways[j]
				} else {
					break
				}
			}
		}
	}
	return ways[len(ways)-1]
} */
