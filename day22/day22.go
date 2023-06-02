package day22

import (
	"advent-of-code-2020/utils"
	"fmt"
	"strings"
)

const filename = "./day22/input.txt"

var input = func() [][]int {
	strs := strings.Split(utils.ReadString(filename), "\n\n")
	input := make([][]int, len(strs))
	for i, s := range strs {
		input[i] = utils.MapToInt(strings.Split(s, "\n")[1:])
	}
	return input
}()

type pair struct {
	s1, s2 string
}

func part01() int {
	player1 := make([]int, len(input[0]))
	copy(player1, input[0])
	player2 := make([]int, len(input[1]))
	copy(player2, input[1])

	for len(player1) > 0 && len(player2) > 0 {
		p1 := player1[0]
		player1 = player1[1:]
		p2 := player2[0]
		player2 = player2[1:]
		if p1 > p2 {
			player1 = append(player1, p1, p2)
		} else {
			player2 = append(player2, p2, p1)
		}
	}

	if len(player1) > 0 {
		return winnerScore(player1)
	} else {
		return winnerScore(player2)
	}
}

func winnerScore(deck []int) int {
	winnerScore := 0
	for i := range deck {
		winnerScore += deck[i] * (len(deck) - i)
	}
	return winnerScore
}

func part02() int {
	player1 := input[0]
	player2 := input[1]

	p1Win := recursiveCombat(&player1, &player2)
	if p1Win {
		return winnerScore(player1)
	} else {
		return winnerScore(player2)
	}
}

func recursiveCombat(deck1, deck2 *[]int) bool {
	history := utils.NewSet[pair]()

	for len(*deck1) > 0 && len(*deck2) > 0 {
		s1 := strings.Join(utils.MapToStr(*deck1), ",")
		s2 := strings.Join(utils.MapToStr(*deck2), ",")
		if history.Contains(pair{s1, s2}) {
			return true
		}
		history.Add(pair{s1, s2})

		p1 := (*deck1)[0]
		*deck1 = (*deck1)[1:]
		p2 := (*deck2)[0]
		*deck2 = (*deck2)[1:]
		if p1 <= len(*deck1) && p2 <= len(*deck2) {
			deck1Copy := make([]int, p1)
			copy(deck1Copy, (*deck1)[:p1])
			deck2Copy := make([]int, p2)
			copy(deck2Copy, (*deck2)[:p2])
			p1WinSubgame := recursiveCombat(&deck1Copy, &deck2Copy)
			if p1WinSubgame {
				*deck1 = append(*deck1, p1, p2)
			} else {
				*deck2 = append(*deck2, p2, p1)
			}
		} else {
			if p1 > p2 {
				*deck1 = append(*deck1, p1, p2)
			} else {
				*deck2 = append(*deck2, p2, p1)
			}
		}
	}

	// If deck1 is empty, player2 wins (returns false). If deck1 has cards, then deck2 must be empty, and player1 wins (returns true)
	return len(*deck1) > 0
}

func Main() {
	fmt.Println("Advent of Code 2020, Day 22")
	fmt.Println(part01())
	fmt.Println(part02())
}
