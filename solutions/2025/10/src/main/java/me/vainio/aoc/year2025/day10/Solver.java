package me.vainio.aoc.year2025.day10;

import me.vainio.aoc.cache.AocCache;

public class Solver {
    private static final int YEAR = 2025;
    private static final int DAY = 10;

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
        // FIXME: Parse input
    }

    public String solvePart1() {
        // FIXME: Implement solution for part 1
        return "";
    }

    public String solvePart2() {
        // FIXME: Implement solution for part 2
        return "";
    }
}
