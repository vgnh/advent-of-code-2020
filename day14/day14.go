package day14

import (
	"advent-of-code-2020/utils"
	"fmt"
	"strconv"
	"strings"
)

const filename = "./day14/input.txt"

var input = utils.ReadLines(filename)

func part01() int {
	mask := strings.Split(input[0], " = ")[1]
	mem := make(map[int]int)

	for i := 1; i < len(input); i++ {
		if strings.HasPrefix(input[i], "mem") {
			temp := strings.Split(input[i], " = ")
			addr, _ := strconv.Atoi(temp[0][4 : len(temp[0])-1])
			n, _ := strconv.Atoi(temp[1])
			num := []rune(fmt.Sprintf("%036b", n))

			for j := range mask {
				if mask[j] == 'X' {
					continue
				}
				num[j] = rune(mask[j])
			}
			t, _ := strconv.ParseInt(string(num), 2, 64)
			mem[addr] = int(t)
		} else {
			mask = strings.Split(input[i], " = ")[1]
		}
	}

	return utils.Sum(utils.MapValues(mem))
}

func part02() int {
	mask := strings.Split(input[0], " = ")[1]
	mem := make(map[int]int)

	for i := 1; i < len(input); i++ {
		if strings.HasPrefix(input[i], "mem") {
			temp := strings.Split(input[i], " = ")
			n, _ := strconv.Atoi(temp[0][4 : len(temp[0])-1])
			addr := []rune(fmt.Sprintf("%036b", n))
			num, _ := strconv.Atoi(temp[1])

			for j := range mask {
				if mask[j] == '0' {
					continue
				}
				addr[j] = rune(mask[j])
			}

			validList := []string{}

			// Generate valid addresses from initial masked address
			var generateValidList func(s string)
			generateValidList = func(s string) {
				x := strings.Index(s, "X")
				if x != -1 {
					runes := []rune(s)
					runes[x] = '0'
					generateValidList(string(runes))
					runes[x] = '1'
					generateValidList(string(runes))
				} else {
					validList = append(validList, s)
				}
			}
			generateValidList(string(addr))
			// Alternate but slower way of generating valid addresses
			/*val queue = mutableListOf<String>(addr.toString())
			  while (queue.any()) {
			      val str = StringBuilder(queue[0])
			      queue.removeAt(0)

			      val x = str.toString().indexOf('X')
			      if (x != -1) {
			          str[x] = '0'
			          queue.add(str.toString())
			          str[x] = '1'
			          queue.add(str.toString())
			      } else
			          validList.add(str.toString())
			  }*/

			for _, a := range validList {
				temp, _ := strconv.ParseInt(a, 2, 64)
				mem[int(temp)] = num
			}
		} else {
			mask = strings.Split(input[i], " = ")[1]
		}
	}

	return utils.Sum(utils.MapValues(mem))
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 14")
	fmt.Println(part01())
	fmt.Println(part02())
}
