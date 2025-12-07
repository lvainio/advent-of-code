package me.vainio.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public final class Grid<T> {
    private final int rows;
    private final int cols;
    private List<List<T>> grid;

    public Grid(Grid<T> other) {
        this.rows = other.rows;
        this.cols = other.cols;
        this.grid = new ArrayList<>(other.rows);
        for (int row = 0; row < other.rows; row++) {
            this.grid.add(new ArrayList<>(other.grid.get(row)));
        }
    }

    private Grid(final String input, final Function<Character, T> mapper) {
        if (input == null) {
            throw new IllegalArgumentException("Input must not be null");
        }
        this.grid = input.lines()
                .map(line -> {
                    List<T> row = new ArrayList<>();
                    line.chars().forEach(c -> row.add(mapper.apply((char) c)));
                    return row;
                })
                .toList();
        this.rows = grid.size();
        this.cols = grid.getFirst().size();
    }

    public static Grid<Character> ofChars(String input) {
        return new Grid<>(input, c -> c);
    }

    public static Grid<Integer> ofDigits(String input) {
        final boolean allDigits =  input.lines()
                .flatMapToInt(String::chars)
                .allMatch(Character::isDigit);
        if (!allDigits) {
            throw new IllegalArgumentException("Input contains non-digit characters");
        }
        return new Grid<>(input, Character::getNumericValue);
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public T get(int row, int col) {
        if (!isInBounds(row, col)) {
            throw new IllegalArgumentException("Row or column out of bounds");
        }
        return grid.get(row).get(col);
    }

    public void set(int row, int col, T value) {
        if (!isInBounds(row, col)) {
            throw new IllegalArgumentException("Row or column out of bounds");
        }
        Objects.requireNonNull(value);
        grid.get(row).set(col, value);
    }

    /**
     * Counts how many adjacent cells (including diagonals) match the given value.
     *
     * @param row   The row index of the cell to check around.
     * @param col   The column index of the cell to check around.
     * @param value The value to count in adjacent cells.
     * @return The number of adjacent cells matching the given value.
     * @throws IllegalArgumentException if row or column is out of bounds.
     * @throws NullPointerException     if value is null.
     */
    public int countAdjacent(int row, int col, T value) {
        if (!isInBounds(row, col)) {
            throw new IllegalArgumentException("Row or column out of bounds");
        }
        Objects.requireNonNull(value);

        int count = 0;
        for (Direction direction : Direction.values()) {
            int dr = direction.getDr();
            int dc = direction.getDc();
            int adjacentRow = row + dr;
            int adjacentCol = col + dc;
            if (isInBounds(adjacentRow, adjacentCol) && grid.get(adjacentRow).get(adjacentCol).equals(value)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if the given row and column indices are within the bounds of the grid.
     *
     * @param row The row index to check.
     * @param col The column index to check.
     * @return true if the indices are within bounds, false otherwise.
     */
    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}
