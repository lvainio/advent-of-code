package com.example.day13_2024;

import java.util.List;

public class Day13 {
    public static void main( String[] args ) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");
        List<ClawMachine> clawMachines = parser.getClawMachines();

        System.out.println("Part1: " + solvePart1(clawMachines));
        System.out.println("Part2: " + solvePart2(clawMachines));
    }

    public static long solvePart1(List<ClawMachine> clawMachines) {
        return clawMachines.stream()
            .mapToLong(machine -> machine.calculateCheapestWin())
            .sum();
    }

    public static long solvePart2(List<ClawMachine> clawMachines) {
        return clawMachines.stream()
            .map(machine -> machine.addLongToPrizeCoordinates(10000000000000L))
            .mapToLong(machine -> machine.calculateCheapestWin())
            .sum();
    }
}
