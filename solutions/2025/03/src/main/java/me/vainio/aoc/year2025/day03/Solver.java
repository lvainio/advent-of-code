package me.vainio.aoc.year2025.day03;

import me.vainio.aoc.cache.AocCache;

import java.util.List;

public class Solver {
    private static final int YEAR = 2025;
    private static final int DAY = 3;

    private final List<List<Integer>> battery;

    public static void main(final String[] args) {  
        final AocCache cache = new AocCache();

        final String input = cache.getInput(YEAR, DAY);
        final Solver solver = new Solver(input);

        final String part1 = solver.solvePart1();
        final String part2 = solver.solvePart2();

        System.out.println(part1);
        System.out.println(part2);

        cache.saveAnswer(YEAR, DAY, 1, part1);
        cache.saveAnswer(YEAR, DAY, 2, part2);
    }

    public Solver(final String input) {
        battery = input.lines()
                        .map(String::chars)
                        .map(ints -> ints
                                .mapToObj(Character::getNumericValue)
                                .toList())
                        .toList();
    }

    public String solvePart1() {
        return findMax(2);
    }

    public String solvePart2() {
        return findMax(12);
    }

    private String findMax(int numDigits) {
        long sum = 0L;
        for (List<Integer> bank : battery) {
            int index = -1;
            StringBuilder sb = new StringBuilder();
            for (int digit = 0; digit < numDigits; digit++) {
                int maxDigit = 0;
                for (int i = index + 1; i <= bank.size() - numDigits + digit; i++) {
                    int currDigit = bank.get(i);
                    if (currDigit > maxDigit) {
                        maxDigit = currDigit;
                        index = i;
                    }
                }
                sb.append(maxDigit);
            }
            sum += Long.parseLong(sb.toString());
        }
        return String.valueOf(sum);
    }
}