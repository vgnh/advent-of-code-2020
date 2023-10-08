package day16

import (
	"advent-of-code-2020/utils"
	"fmt"
	"regexp"
	"slices"
	"strconv"
	"strings"
)

const filename = "./day16/input.txt"

var input = strings.Split(utils.ReadString(filename), "\n\n")

var rules = strings.Split(input[0], "\n")

var list = strings.Split(input[2], "\n")
var nearbyTickets = list[1:]

var myTicket = utils.MapToInt(strings.Split(strings.Split(input[1], "\n")[1], ","))

func part01() int {
	listOfRanges := []func(int) bool{}

	for _, rule := range rules {
		reg := regexp.MustCompile(".*: ([0-9]*)-([0-9]*) or ([0-9]*)-([0-9]*)").FindStringSubmatch(rule)
		r1, _ := strconv.Atoi(reg[1])
		r2, _ := strconv.Atoi(reg[2])
		r3, _ := strconv.Atoi(reg[3])
		r4, _ := strconv.Atoi(reg[4])
		listOfRanges = append(listOfRanges, func(num int) bool {
			return ((num >= r1) && (num <= r2)) || ((num >= r3) && (num <= r4))
		})
	}

	errorRate := 0
	for _, nearbyTicket := range nearbyTickets {
		for _, n := range utils.MapToInt(strings.Split(nearbyTicket, ",")) {
			atLeastOneWithinRange := false
			for _, fn := range listOfRanges {
				if fn(n) {
					atLeastOneWithinRange = true
					break
				}
			}
			if !atLeastOneWithinRange {
				errorRate += n
			}
		}
	}
	return errorRate
}

func part02() int {
	departure := 1

	listOfRanges := []func(int) bool{}
	for _, rule := range rules {
		reg := regexp.MustCompile(".*: ([0-9]*)-([0-9]*) or ([0-9]*)-([0-9]*)").FindStringSubmatch(rule)
		r1, _ := strconv.Atoi(reg[1])
		r2, _ := strconv.Atoi(reg[2])
		r3, _ := strconv.Atoi(reg[3])
		r4, _ := strconv.Atoi(reg[4])
		listOfRanges = append(listOfRanges, func(num int) bool {
			return ((num >= r1) && (num <= r2)) || ((num >= r3) && (num <= r4))
		})
	}
	validTicketList := [][]int{}
	for _, nearbyTicket := range nearbyTickets {
		arr := utils.MapToInt(strings.Split(nearbyTicket, ","))
		allNumbersWithinSomeRange := true
		for _, n := range arr {
			atLeastOneWithinRange := false
			for _, fn := range listOfRanges {
				if fn(n) {
					atLeastOneWithinRange = true
					break
				}
			}
			if !atLeastOneWithinRange {
				allNumbersWithinSomeRange = false
				break
			}
		}
		if allNumbersWithinSomeRange {
			validTicketList = append(validTicketList, arr)
		}
	}
	validTickets2D := make([][]int, len(validTicketList))
	for i := range validTickets2D {
		validTickets2D[i] = make([]int, len(validTicketList[i]))
		for j := range validTickets2D[i] {
			validTickets2D[i][j] = validTicketList[i][j]
		}
	}

	ruleArrMap := make(map[string][]bool)
	for _, rule := range rules {
		reg := regexp.MustCompile(".*: ([0-9]*)-([0-9]*) or ([0-9]*)-([0-9]*)").FindStringSubmatch(rule)
		r1, _ := strconv.Atoi(reg[1])
		r2, _ := strconv.Atoi(reg[2])
		r3, _ := strconv.Atoi(reg[3])
		r4, _ := strconv.Atoi(reg[4])

		colArr := make([]bool, len(validTickets2D[0]))
		for j := range validTickets2D[0] {
			valid := true
			for i := range validTickets2D {
				if !(((validTickets2D[i][j] >= r1) && (validTickets2D[i][j] <= r2)) || ((validTickets2D[i][j] >= r3) && (validTickets2D[i][j] <= r4))) {
					valid = false
					break
				}
			}
			if valid {
				colArr[j] = true
			}
		}
		ruleArrMap[reg[0]] = colArr
	}
	ruleArrMapKeys := utils.MapKeys(ruleArrMap)
	slices.SortFunc(ruleArrMapKeys, func(a, b string) int {
		return utils.Count(ruleArrMap[a], true) - utils.Count(ruleArrMap[b], true)
	})

	colIgnoreList := []int{}
	for _, key := range ruleArrMapKeys {
		arr := ruleArrMap[key]
		for _, col := range colIgnoreList {
			arr[col] = false
		}
		if utils.Count(arr, true) == 1 {
			index := slices.Index(arr, true)
			if strings.HasPrefix(key, "departure") {
				departure *= myTicket[index]
			}
			colIgnoreList = append(colIgnoreList, index)
		}
	}

	return departure
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 16")
	fmt.Println(part01())
	fmt.Println(part02())
}
