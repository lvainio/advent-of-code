package me.vainio.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public final class Grid<T> {
    private final int rows;
    private final int cols;
    private final List<List<T>> grid;

    private Grid(final List<List<T>> grid) {
        this.rows = grid.size();
        this.cols = grid.getFirst().size();
        this.grid = grid;
    }

    /**
     * Creates a copy of the given grid.
     *
     * @param other The grid to copy.
     */
    public Grid(final Grid<T> other) {
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

    /**
     * Creates a Grid from the given input string using the provided mapper function.
     *
     * @param input  The input string representing the grid.
     * @param mapper A function to map characters to the desired type T.
     * @param <T>    The type of elements in the grid.
     * @return A Grid of type T.
     */
    public static <T> Grid<T> from(final String input, final Function<Character, T> mapper) {
        return new Grid<>(input, mapper);
    }

    /**
     * Creates a Grid of Characters from the given input string.
     *
     * @param input The input string representing the grid.
     * @return A Grid of Characters.
     */
    public static Grid<Character> ofChars(final String input) {
        return new Grid<>(input, c -> c);
    }

    /**
     * Returns a new Grid that is the transpose of this grid.
     *
     * @return a new transposed Grid.
     */
    public Grid<T> transpose() {
        List<List<T>> transposed = new ArrayList<>(cols);
        for (int col = 0; col < cols; col++) {
            List<T> newRow = new ArrayList<>(rows);
            for (int row = 0; row < rows; row++) {
                newRow.add(grid.get(row).get(col));
            }
            transposed.add(newRow);
        }
        return new Grid<>(transposed);
    }

    /**
     * Creates a Grid of Integers from the given input string.
     *
     * @param input The input string representing the grid.
     * @return A Grid of Integers.
     * @throws IllegalArgumentException if the input contains non-digit characters.
     */
    public static Grid<Integer> ofDigits(final String input) {
        final boolean allDigits =  input.lines()
                .flatMapToInt(String::chars)
                .allMatch(Character::isDigit);
        if (!allDigits) {
            throw new IllegalArgumentException("Input contains non-digit characters");
        }
        return new Grid<>(input, Character::getNumericValue);
    }

    /**
     * Returns the number of rows in the grid.
     * @return The number of rows.
     */
    public int rows() {
        return rows;
    }

    /**
     * Returns the number of columns in the grid.
     * @return The number of columns.
     */
    public int cols() {
        return cols;
    }

    /**
     * Gets the value at the specified row and column.
     *
     * @param row The row index.
     * @param col The column index.
     * @return The value at the specified position.
     * @throws IndexOutOfBoundsException if row or column is out of bounds.
     */
    public T get(final int row, final int col) {
        if (!isInBounds(row, col)) {
            throw new IndexOutOfBoundsException("Row or column out of bounds");
        }
        return grid.get(row).get(col);
    }

    /**
     * Gets the entire row at the specified index.
     *
     * @param row The row index.
     * @return A list representing the row.
     * @throws IndexOutOfBoundsException if row is out of bounds.
     */
    public List<T> getRow(final int row) {
        if (row < 0 || row >= rows) {
            throw new IndexOutOfBoundsException("Row out of bounds: " + row);
        }
        return grid.get(row);
    }

    /**
     * Sets the value at the specified row and column.
     *
     * @param row   The row index.
     * @param col   The column index.
     * @param value The value to set.
     * @throws IndexOutOfBoundsException if row or column is out of bounds.
     * @throws NullPointerException      if value is null.
     */
    public void set(final int row, final int col, T value) {
        if (!isInBounds(row, col)) {
            throw new IndexOutOfBoundsException("Row or column out of bounds");
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
    public int countAdjacent(final int row, final int col, T value) {
        if (!isInBounds(row, col)) {
            throw new IllegalArgumentException("Row or column out of bounds");
        }
        Objects.requireNonNull(value);

        int count = 0;
        for (Direction direction : Direction.values()) {
            final int dr = direction.getDr();
            final int dc = direction.getDc();
            final int adjacentRow = row + dr;
            final int adjacentCol = col + dc;
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
    public boolean isInBounds(final int row, final int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(rows * (cols + 1));
        for (int r = 0; r < rows; r++) {
            List<T> row = grid.get(r);
            for (int c = 0; c < cols; c++) {
                T value = row.get(c);
                sb.append(value != null ? value.toString() : "null");
            }
            if (r < rows - 1) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }
}
