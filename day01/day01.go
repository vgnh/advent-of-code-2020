package day01

import (
	"advent-of-code-2020/utils"
	"math"
	"slices"
)

const filename = "./inputs/day01.txt"

var expenseReport = utils.MapToInt(utils.ReadLines(filename))

func part01() int {
	var expenseList []int
	for _, expense := range expenseReport {
		diff := int(math.Abs(2020 - float64(expense)))
		if slices.Contains(expenseList, diff) {
			return expense * diff
		}
		expenseList = append(expenseList, expense)
	}
	return -1
}

func part02() int {
	for i := 0; i < len(expenseReport)-1; i++ {
		for j := i + 1; j < len(expenseReport); j++ {
			expenseOnePlusTwo := expenseReport[i] + expenseReport[j]
			diff := 2020 - expenseOnePlusTwo
			if !(expenseOnePlusTwo < 2020) && (diff < 0) {
				continue
			}
			if slices.Contains(expenseReport, diff) {
				return diff * expenseReport[i] * expenseReport[j]
			}
		}
	}
	return -1
}

func Main() (int, func() int, func() int) {
	return 1, part01, part02
}
