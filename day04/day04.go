package day04

import (
	"advent-of-code-2020/utils"
	"regexp"
	"strings"
)

const filename = "./inputs/day04.txt"

var passportList = func() []string {
	passportList := strings.Split(utils.ReadString(filename), "\n\n")
	for i, str := range passportList {
		passportList[i] = strings.Join(strings.Split(str, "\n"), " ")
	}
	return passportList
}()

func part01() int {
	validPass := 0
	patterns := []string{".*byr:.*", ".*iyr:.*", ".*eyr:.*", ".*hgt:.*", ".*hcl:.*", ".*ecl:.*", ".*pid:.*"}
	for _, passport := range passportList {
		matchAll := true
		for _, pattern := range patterns {
			if !regexp.MustCompile(pattern).MatchString(passport) {
				matchAll = false
				break
			}
		}
		if matchAll {
			validPass++
		}
	}
	return validPass
}

func part02() int {
	validPass := 0
	// byr = "19[2-9][0-9]|200[0-2]"
	// iyr = "20(1[0-9]|20)"
	// eyr = "20(2[0-9]|30)"
	// hgt = "(1([5-8][0-9]|9[0-3])cm)|((59|6[0-9]|7[0-3])in)"
	// hcl = "#[0-9a-f]{6}"
	// ecl = "(amb|blu|brn|gry|grn|hzl|oth)"
	// pid = "[0-9]{9}"
	patterns := []string{".*byr:(19[2-9][0-9]|200[0-2]).*", ".*iyr:(20(1[0-9]|20)).*", ".*eyr:(20(2[0-9]|30)).*", ".*hgt:((1([5-8][0-9]|9[0-3])cm)|((59|6[0-9]|7[0-3])in)).*", ".*hcl:(#[0-9a-f]{6}).*", ".*ecl:(amb|blu|brn|gry|grn|hzl|oth).*", ".*pid:([0-9]{9}).*"}
	for _, passport := range passportList {
		matchAll := true
		for _, pattern := range patterns {
			if !regexp.MustCompile(pattern).MatchString(passport) {
				matchAll = false
				break
			}
		}
		if matchAll {
			validPass++
		}
	}
	return validPass
}

func Main() (int, func() int, func() int) {
	return 4, part01, part02
}
