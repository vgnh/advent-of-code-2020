package day25

import (
	"advent-of-code-2020/utils"
	"fmt"
)

const filename = "./day25/input.txt"

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

func Main() {
	fmt.Println("Advent of Code 2020, Day 25")
	fmt.Println(part01())
}
