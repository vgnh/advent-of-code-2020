package day02

import (
	"advent-of-code-2020/utils"
	"strconv"
	"strings"
)

const filename = "./inputs/day02.txt"

var passwordPolicies = utils.ReadLines(filename)

type policy struct {
	min      int
	max      int
	letter   rune
	password string
}

func newPolicy(str string) *policy {
	policyAndPass := strings.Split(str, ": ")
	min, _ := strconv.Atoi(strings.Split(strings.Split(policyAndPass[0], " ")[0], "-")[0])
	max, _ := strconv.Atoi(strings.Split(strings.Split(policyAndPass[0], " ")[0], "-")[1])
	return &policy{
		password: policyAndPass[1],
		letter:   []rune(strings.Split(policyAndPass[0], " ")[1])[0],
		min:      min,
		max:      max,
	}
}

func part01() int {
	validCount := 0
	for _, str := range passwordPolicies {
		policy := newPolicy(str)
		letterCount := utils.Count([]rune(policy.password), policy.letter)
		if letterCount >= policy.min && letterCount <= policy.max {
			validCount++
		}
	}
	return validCount
}

func part02() int {
	validCount := 0
	for _, str := range passwordPolicies {
		policy := newPolicy(str)
		a := policy.letter == []rune(policy.password)[policy.min-1]
		b := policy.letter == []rune(policy.password)[policy.max-1]
		if (a || b) && (!a || !b) {
			validCount++
		}
	}
	return validCount
}

func Main() (int, func() int, func() int) {
	return 2, part01, part02
}
