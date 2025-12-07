package me.vainio.aoc.year2025.day05;

import me.vainio.aoc.cache.AocCache;

import java.util.List;

public class Solver {
    private static final int YEAR = 2025;
    private static final int DAY = 5;

    private final List<Range> ranges;
    private final List<Long> ids;

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
        String[] parts = input.split("\n\n");

        ranges = parts[0].lines().map(line -> {
            String[] range = line.split("-");
            long start = Long.parseLong(range[0]);
            long end = Long.parseLong(range[1]);
            return new Range(start, end);
        }).toList();

        ids = parts[1].lines().map(Long::parseLong).toList();
    }

    public String solvePart1() {
        long count = ids.stream()
                .filter(id -> ranges.stream()
                        .anyMatch(range -> range.contains(id)))
                .count();
        return String.valueOf(count);
    }

    public String solvePart2() {
        // FIXME: Implement solution for part 2
        // ranges overlap
        return "";
    }
}
