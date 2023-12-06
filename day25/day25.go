package day25

import (
	"advent-of-code-2020/utils"
)

const filename = "./inputs/day25.txt"

var input = utils.MapToInt(utils.ReadLines(filename))

func part01() int {
	encryptionKey := 1
	value := 1
	for {
		encryptionKey = (encryptionKey * input[1]) % 20201227
		value = (value * 7) % 20201227
		if value == input[0] {
			return encryptionKey
		}
	}
}

func Main() (int, func() int, func() int) {
	return 25, part01, func() int {
		// no part02 on day25
		return -1
	}
}
