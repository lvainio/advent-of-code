package me.vainio.aoc.year2025.day06;

import me.vainio.aoc.cache.AocCache;
import me.vainio.aoc.util.Grid;

import java.util.Arrays;
import java.util.List;

public class Solver {
    private static final int YEAR = 2025;
    private static final int DAY = 6;

    private static final String ADD = "+";
    private static final String MUL = "*";

    private final List<List<Long>> numbers;
    private final List<String> operations;
    private final List<List<Long>> groups;

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
        final List<String> lines = input.lines().toList();

        // Parse for part 1
        this.numbers =  lines.subList(0, lines.size() - 1).stream()
                .map(line -> Arrays.stream(line.trim().split("\\s+"))
                        .map(Long::parseLong)
                        .toList())
                .toList();
        this.operations = Arrays.stream(lines.getLast().split("\\s+"))
                .map(String::trim)
                .toList();

        // Parse for part 2
        final List<String> subList = lines.subList(0, lines.size() - 1);
        final String transposed = Grid.ofChars(String.join("\n", subList))
                .transpose()
                .toString();
        this.groups =
                Arrays.stream(transposed.split("\n\\s*\n"))
                        .map(block -> Arrays.stream(block.split("\n"))
                                .map(String::trim)
                                .map(Long::parseLong)
                                .toList())
                        .toList();
    }

    public String solvePart1() {
        final int rows = numbers.size();
        final int cols = numbers.getFirst().size();
        long sum = 0;
        for (int col = 0; col < cols; col++) {
            final String op = operations.get(col);
            long acc = numbers.getFirst().get(col);
            for (int row = 1; row < rows; row++) {
                final long value = numbers.get(row).get(col);
                switch (op) {
                    case ADD -> acc += value;
                    case MUL -> acc *= value;
                    default -> throw new IllegalStateException("Unknown operation: " + op);
                }
            }
            sum += acc;
        }
        return String.valueOf(sum);
    }

    public String solvePart2() {
        long sum = 0;
        for (int i = 0; i < groups.size(); i++) {
            final List<Long> group = groups.get(i);
            final String op = operations.get(i);
            long acc = group.getFirst();
            for (Long value : group.subList(1, group.size())) {
                acc = switch (op) {
                    case ADD -> acc + value;
                    case MUL -> acc * value;
                    default -> throw new IllegalStateException("Unknown operation: " + op);
                };
            }
            sum += acc;
        }
        return Long.toString(sum);
    }
}
