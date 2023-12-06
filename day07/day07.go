package day07

import (
	"advent-of-code-2020/utils"
	// "regexp"
	"strconv"
	"strings"
)

const filename = "./inputs/day07.txt"

var ruleMap = func() map[string]string {
	ruleMap := make(map[string]string)
	for _, str := range utils.ReadLines(filename) {
		temp := strings.Split(strings.ReplaceAll(strings.ReplaceAll(str[:len(str)-1], " bags", ""), " bag", ""), " contain ")
		ruleMap[temp[0]] = temp[1]
	}
	return ruleMap
}()

func part01() int {
	bagCount := 0
	for key := range ruleMap {
		if hasShinyGold(key) {
			bagCount++
		}
	}
	return bagCount
}

func hasShinyGold(str string) bool {
	if strings.Contains(ruleMap[str], "shiny gold") {
		return true
	}
	for _, value := range strings.Split(ruleMap[str], ", ") {
		if value != "no other" {
			if !hasShinyGold(value[2:]) {
				continue
			}
			return true
		}
	}
	return false
}

func totalBags(bagColor string) int {
	total := 0
	for _, str := range strings.Split(ruleMap[bagColor], ", ") {
		if str != "no other" {
			num, _ := strconv.Atoi(str[:1])
			total += num + num*totalBags(str[2:])
		} else {
			break
		}
	}
	return total
}

func part02() int {
	return totalBags("shiny gold")
}

func Main() (int, func() int, func() int) {
	return 7, part01, part02
}

// Alternate but slower part01
/* func part01() int {
	var bagsWithSG []string
	queue := utils.NewDeque[string]()
	queue.Enqueue("shiny gold")
	for !queue.Empty() {
		bag, _ := queue.Dequeue()

		var newKeys []string
		for key, value := range ruleMap {
			if regexp.MustCompile(".*" + bag + ".*").MatchString(value) {
				newKeys = append(newKeys, key)
			}
		}
		for _, str := range newKeys {
			if !utils.Contains(bagsWithSG, str) {
				bagsWithSG = append(bagsWithSG, str)
				queue.Enqueue(str)
			}
		}
	}
	return len(bagsWithSG)
} */
