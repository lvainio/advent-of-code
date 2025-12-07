package me.vainio.aoc.year2025.day04;

import me.vainio.aoc.cache.AocCache;
import me.vainio.aoc.util.Grid;

public class Solver {
    private static final int YEAR = 2025;
    private static final int DAY = 4;

    private static final char PAPER = '@';

    private final Grid<Character> grid;

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
        grid = Grid.ofChars(input);
    }

    public String solvePart1() {
        int count = 0;
        for (int row = 0; row < grid.rows(); row++) {
            for (int col = 0; col < grid.cols(); col++) {
                if (grid.countAdjacent(row, col, PAPER) < 4 && grid.get(row, col) == PAPER) {
                    count++;
                }
            }
        }
        return String.valueOf(count);
    }

    public String solvePart2() {
        Grid<Character> grid = new Grid<>(this.grid);
        int sum = 0;
        while (true) {
            int removed = removePapers(grid);
            if (removed == 0) {
                break;
            }
            sum += removed;
        }
        return String.valueOf(sum);
    }

    private int removePapers(Grid<Character> grid) {
        int count = 0;
        for (int row = 0; row < grid.rows(); row++) {
            for (int col = 0; col < grid.cols(); col++) {
                if (grid.get(row, col) == PAPER && grid.countAdjacent(row, col, PAPER) < 4) {
                    count++;
                    grid.set(row, col, '.');
                }
            }
        }
        return count;
    }
}