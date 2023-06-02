package main

import (
	"advent-of-code-2020/day01"
	"advent-of-code-2020/day02"
	"advent-of-code-2020/day03"
	"advent-of-code-2020/day04"
	"advent-of-code-2020/day05"
	"advent-of-code-2020/day06"
	"advent-of-code-2020/day07"
	"advent-of-code-2020/day08"
	"advent-of-code-2020/day09"
	"advent-of-code-2020/day10"
	"advent-of-code-2020/day11"
	"advent-of-code-2020/day12"
	"advent-of-code-2020/day13"
	"advent-of-code-2020/day14"
	"advent-of-code-2020/day15"
	"advent-of-code-2020/day16"
	"advent-of-code-2020/day17"
	"advent-of-code-2020/day18"
	"advent-of-code-2020/day22"
	"advent-of-code-2020/day23"
	"advent-of-code-2020/day24"
	"advent-of-code-2020/day25"
	"fmt"
	"sync"
	"time"
)

func main() {
	start := time.Now()

	runInParallel := false

	var wg sync.WaitGroup
	for _, fun := range []func(){day01.Main, day02.Main, day03.Main, day04.Main, day05.Main, day06.Main, day07.Main, day08.Main, day09.Main, day10.Main, day11.Main, day12.Main, day13.Main, day14.Main, day15.Main, day16.Main, day17.Main, day18.Main, day22.Main, day23.Main, day24.Main, day25.Main} {
		fn := fun

		switch runInParallel {
		case true:
			wg.Add(1)
			go func() {
				fn()
				wg.Done()
			}()
		case false:
			fn()
		}
	}
	wg.Wait()

	fmt.Printf("\nTime elapsed: %v\n", time.Since(start))
}
