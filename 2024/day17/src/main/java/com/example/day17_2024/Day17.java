package com.example.day17_2024;

import java.util.ArrayList;
import java.util.List;

public class Day17 {
    public static void main( String[] args ) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        long a = parser.getA();
        long b = parser.getB();
        long c = parser.getC();

        List<Integer> instructions = parser.getInstructions();

        Computer c1 = new Computer(a, b, c, new ArrayList<>(instructions));
        c1.runProgram();

        System.out.println("Part1: " + c1.getOutput().trim());

        long a2 = 0b000;
        long b2 = b;
        long c2 = c;
        long minValue = findMin(instructions, instructions.size()-1, 0, a2, b2, c2);

        System.out.println("Part2: " + minValue);
    }

    public static long findMin(List<Integer> instructions, int instructionIdx, int outputIdx, long a, long b, long c) {
        if (instructionIdx < 0) {
            return a>>3;
        }

        List<Long> validAValues = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Computer computer = new Computer(a, b, c, new ArrayList<>(instructions));
            computer.runProgram();

            List<Integer> output = computer.getOutputList();
            if (output.get(outputIdx) == instructions.get(instructionIdx)) {
                validAValues.add(a);
            }
            a++;
        }

        return validAValues.stream()
                    .mapToLong(newA -> findMin(instructions, instructionIdx-1, outputIdx, newA<<3, b, c))
                    .min()
                    .orElse(Long.MAX_VALUE);
    }
}
