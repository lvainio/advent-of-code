package main

import (
	"fmt"
	"log"
	"os"
	"regexp"
	"unicode"
)

func main() {
	data, err := os.ReadFile("input.txt")
	if err != nil {
		log.Fatal(err)
	}

	groups := regexp.MustCompile(`\r?\n\r?\n`).Split(string(data), -1)

	part1 := 0
	part2 := 0
	for _, person := range groups {
		charCounts := make(map[rune]int)
		numPeople := 1
		for _, char := range person {
			if unicode.IsLower(char) {
				charCounts[char]++
			} else if char == '\n' {
				numPeople++
			}
		}
		part1 += len(charCounts)
		for _, count := range charCounts {
			if count == numPeople {
				part2++
			}
		}
	}
	fmt.Println("part1:", part1, "part2:", part2)
}
