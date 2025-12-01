package me.vainio.aoc.year2025.day01;

import me.vainio.aoc.cache.AocCache;

import java.util.List;

public class Solver {
    private static final int YEAR = 2025;
    private static final int DAY = 1;

    private static final int MOD = 100;
    private static final String LEFT = "L";
    private static final String RIGHT = "R";

    private final List<Rotation> rotations;

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
        this.rotations = input.lines().map(line -> {
            final String direction = line.substring(0, 1);
            final int clicks = Integer.parseInt(line.substring(1).trim());
            return new Rotation(direction, clicks);
        }).toList();
    }

    public String solvePart1() {
        int count = 0;
        int position = 50;
        for (Rotation rotation : rotations) {
            position = switch (rotation.direction()) {
                case LEFT -> (position - rotation.clicks() + MOD) % MOD;
                case RIGHT -> (position + rotation.clicks() + MOD) % MOD;
                default -> throw new IllegalStateException("Unexpected value: " + rotation.direction());
            };
            if (position == 0) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    public String solvePart2() {
        return "";
    }
}