package com.example.day02_2015;

import java.util.List;

public class Day02 {
    public static void main(String[] args) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        List<Present> presents = parser.getPresents();

        // Part1
        int part1 = presents.stream().mapToInt(Present::wrappingPaperNeeded).sum();
        System.out.println(part1);

        // Part2
        int part2 = presents.stream().mapToInt(Present::ribbonNeeded).sum();
        System.out.println(part2);
    }
}
