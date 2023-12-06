package day06

import (
	"advent-of-code-2020/utils"
	"strings"
)

const filename = "./inputs/day06.txt"

var groupList = func() []string {
	groupList := strings.Split(utils.ReadString(filename), "\n\n")
	for i, str := range groupList {
		groupList[i] = strings.Join(strings.Split(str, "\n"), " ")
	}
	return groupList
}()

func part01() int {
	totalYesCount := 0
	for _, group := range groupList {
		answerSet := utils.NewSet[rune]()
		for _, answer := range strings.Split(group, " ") {
			answerSet.AddAll([]rune(answer))
		}
		totalYesCount += len(answerSet)
	}
	return totalYesCount
}

func part02() int {
	totalYesCount := 0
	for _, group := range groupList {
		var answerSet utils.Set[rune]
		for _, answer := range strings.Split(group, " ") {
			if answerSet == nil {
				answerSet = utils.NewSet[rune]()
				answerSet.AddAll([]rune(answer))
			} else {
				answerSet.RetainAll([]rune(answer))
			}
		}
		totalYesCount += len(answerSet)
	}
	return totalYesCount
}

func Main() (int, func() int, func() int) {
	return 6, part01, part02
}
