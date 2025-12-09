package me.vainio.aoc.year2024.day04;

import me.vainio.aoc.cache.AocCache;
import me.vainio.aoc.util.Grid;
import me.vainio.aoc.util.Direction;

public class Solver {
    private static final int YEAR = 2024;
    private static final int DAY = 4;

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
        this.grid = Grid.ofChars(input);
    }

    public String solvePart1() {
        int count = 0;
        for (int row = 0; row < grid.numRows(); row++) {
            for (int col = 0; col < grid.numCols(); col++) {
                for (Direction direction : Direction.values()) {
                    if (isXmasPart1(row, col, direction)) {
                        count++;
                    }
                }
            }
        }
        return Integer.toString(count);
    }

    private boolean isXmasPart1(final int row, final int col, final Direction dir) {
        final int dr = dir.getDr();
        final int dc = dir.getDc();
        final String pattern = "XMAS";

        for (int i = 0; i < pattern.length(); i++) {
            final int newRow = row + i * dr;
            final int newCol = col + i * dc;
            if (!grid.isInBounds(newRow, newCol) || grid.get(newRow, newCol) != pattern.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public String solvePart2() {
        int count = 0;
        for (int row = 0; row < grid.numRows(); row++) {
            for (int col = 0; col < grid.numCols(); col++) {
                if (isXmasPart2(row, col)) {
                    count++;
                }
            }
        }
        return Integer.toString(count);
    }

    private boolean isXmasPart2(final int row, final int col) {
        if (grid.get(row, col) != 'A') {
            return false;
        }
        if (row < 1 || row > grid.numRows() - 2 || col < 1 || col > grid.numCols() - 2) {
            return false;
        }

        final String downwardDiagonal = String.format("%c%c%c",
                grid.get(row-1, col-1),
                grid.get(row,   col),
                grid.get(row+1, col+1));
        final String upwardDiagonal = String.format("%c%c%c",
                grid.get(row+1, col-1),
                grid.get(row,   col),
                grid.get(row-1, col+1));

        return (downwardDiagonal.equals("MAS") || downwardDiagonal.equals("SAM")) &&
                (upwardDiagonal.equals("MAS") || upwardDiagonal.equals("SAM"));
    }
}
