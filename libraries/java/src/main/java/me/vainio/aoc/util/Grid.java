package me.vainio.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Grid<T> {
    private final int numRows;
    private final int numCols;
    private final List<List<T>> grid;

    /**
     * Creates a copy of the given grid.
     *
     * @param other The grid to copy.
     * @throws NullPointerException if other is null.
     */
    public Grid(final Grid<T> other) {
        Objects.requireNonNull(other, "Other grid must not be null");

        this.numRows = other.numRows;
        this.numCols = other.numCols;
        this.grid = new ArrayList<>(other.numRows);
        for (List<T> row : other.grid) {
            this.grid.add(new ArrayList<>(row));
        }
    }

    /**
     * Creates a Grid from the given input string using the provided mapper function.
     *
     * @param input The input string representing the grid.
     * @param mapper A function to map characters to the desired type T.
     * @throws NullPointerException if input or mapper is null.
     * @throws IllegalArgumentException if input is empty or rows have different lengths.
     */
    private Grid(final String input, final Function<Character, T> mapper) {
        Objects.requireNonNull(input, "Input string must not be null");
        Objects.requireNonNull(mapper, "Mapper function must not be null");
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input string must not be empty");
        }
        final List<String> lines = input.lines().toList();
        final int expectedLength = lines.getFirst().length();
        if (lines.stream().anyMatch(line -> line.length() != expectedLength)) {
            throw new IllegalArgumentException("All lines must have the same length");
        }

        this.grid = input.lines()
                .map(line -> line.chars()
                        .mapToObj(c -> mapper.apply((char) c))
                        .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));
        this.numRows = grid.size();
        this.numCols = grid.getFirst().size();
    }

    /**
     * Creates a grid from a list of lists.
     *
     * @param grid The list of lists representing the rows of the grid.
     * @throws NullPointerException if grid is null.
     */
    private Grid(final List<List<T>> grid) {
        Objects.requireNonNull(grid, "Grid must not be null");

        this.numRows = grid.size();
        this.numCols = grid.getFirst().size();
        this.grid = new ArrayList<>(numRows);
        for (List<T> row : grid) {
            this.grid.add(new ArrayList<>(row));
        }
    }

    /**
     * Creates a Grid of Characters from the given input string.
     *
     * @param input The input string representing the grid.
     * @return A Grid of Characters.
     * @throws NullPointerException if input is null.
     * @throws IllegalArgumentException if input is empty or rows have different lengths.
     */
    public static Grid<Character> ofChars(final String input) {
        Objects.requireNonNull(input, "Input string must not be null");

        return new Grid<>(input, c -> c);
    }

    /**
     * Creates a Grid of Integers from the given input string.
     *
     * @param input The input string representing the grid.
     * @return A Grid of Integers.
     * @throws NullPointerException if input is null.
     * @throws IllegalArgumentException if the input contains non-digits, is empty, or rows have different lengths.
     */
    public static Grid<Integer> ofDigits(final String input) {
        Objects.requireNonNull(input, "Input string must not be null");
        final boolean allDigits =  input.lines()
                .flatMapToInt(String::chars)
                .allMatch(Character::isDigit);
        if (!allDigits) {
            throw new IllegalArgumentException("Input contains non-digit characters");
        }

        return new Grid<>(input, Character::getNumericValue);
    }

    /**
     * Returns a new Grid representing a sub-grid of this grid.
     *
     * @param fromRow the starting row index (inclusive)
     * @param toRow the ending row index (exclusive)
     * @param fromCol the starting column index (inclusive)
     * @param toCol the ending column index (exclusive)
     * @return a new Grid containing the specified sub-grid
     * @throws IllegalArgumentException if range is empty or invalid
     * @throws IndexOutOfBoundsException if the specified range is out of bounds of the grid
     */
    public Grid<T> subGrid(
            final int fromRow,
            final int toRow,
            final int fromCol,
            final int toCol
    ) {
        if (fromRow >= toRow) {
            throw new IllegalArgumentException("fromRow must be less than toRow");
        }
        if (fromCol >= toCol) {
            throw new IllegalArgumentException("fromCol must be less than toCol");
        }
        if (!isInBounds(fromRow, fromCol) || !isInBounds(toRow-1, toCol-1)) {
            throw new IndexOutOfBoundsException("Sub-grid range is out of bounds");
        }

        final List<List<T>> sub = new ArrayList<>(toRow - fromRow);
        for (int row = fromRow; row < toRow; row++) {
            final List<T> newRow = new ArrayList<>(toCol - fromCol);
            for (int col = fromCol; col < toCol; col++) {
                newRow.add(grid.get(row).get(col));
            }
            sub.add(newRow);
        }
        return new Grid<>(sub);
    }

    /**
     * Returns the number of rows in the grid.
     *
     * @return The number of rows.
     */
    public int numRows() {
        return numRows;
    }

    /**
     * Returns the number of columns in the grid.
     *
     * @return The number of columns.
     */
    public int numCols() {
        return numCols;
    }

    /**
     * Checks if the given row and column are within the bounds of the grid.
     *
     * @param row The row index to check.
     * @param col The column index to check.
     * @return true if the indices are within bounds, false otherwise.
     */
    public boolean isInBounds(final int row, final int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }

    /**
     * Checks if the given location is within the bounds of the grid.
     *
     * @param location The location to check.
     * @return true if the location is within bounds, false otherwise.
     * @throws NullPointerException if location is null.
     */
    public boolean isInBounds(final Location location) {
        Objects.requireNonNull(location, "Location must not be null");

        return isInBounds(location.row(), location.col());
    }

    /**
     * Returns the value at the specified row and column.
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
     * Returns the value at the specified location.
     *
     * @param location The location object containing row and column indices.
     * @return The value at the specified location.
     * @throws IndexOutOfBoundsException if location is out of bounds.
     * @throws NullPointerException if location is null.
     */
    public T get(final Location location) {
        Objects.requireNonNull(location, "Location must not be null");
        if (!isInBounds(location.row(), location.col())) {
            throw new IndexOutOfBoundsException("Row or column out of bounds");
        }

        return get(location.row(), location.col());
    }

    /**
     * Sets the value at the specified row and column.
     *
     * @param row The row index.
     * @param col The column index.
     * @param value The value to set.
     * @throws IndexOutOfBoundsException if row or column is out of bounds.
     * @throws NullPointerException if value is null.
     */
    public void set(final int row, final int col, T value) {
        if (!isInBounds(row, col)) {
            throw new IndexOutOfBoundsException("Row or column out of bounds");
        }
        Objects.requireNonNull(value);

        grid.get(row).set(col, value);
    }

    /**
     * Returns a copy of the row at the specified index.
     *
     * @param row The row index.
     * @return A list representing the row.
     * @throws IndexOutOfBoundsException if row is out of bounds.
     */
    public List<T> getRow(final int row) {
        if (row < 0 || row >= numRows) {
            throw new IndexOutOfBoundsException("Row out of bounds: " + row);
        }

        return new ArrayList<>(grid.get(row));
    }

    /**
     * Returns a copy of the first row.
     *
     * @return A list representing the first row.
     */
    public List<T> getFirstRow() {
        return getRow(0);
    }

    /**
     * Returns a copy of the last row.
     *
     * @return A list representing the last row.
     */
    public List<T> getLastRow() {
        return getRow(numRows - 1);
    }

    /**
     * Returns a copy of the column at the specified index.
     *
     * @param col The column index.
     * @return A list representing the column.
     * @throws IndexOutOfBoundsException if column is out of bounds.
     */
    public List<T> getCol(final int col) {
        if (col < 0 || col >= numCols) {
            throw new IndexOutOfBoundsException("Column out of bounds: " + col);
        }

        List<T> column = new ArrayList<>(numRows);
        for (int row = 0; row < numRows; row++) {
            column.add(grid.get(row).get(col));
        }
        return column;
    }

    /**
     * Returns a copy of the first column.
     *
     * @return A list representing the first column.
     */
    public List<T> getFirstCol() {
        return getCol(0);
    }

    /**
     * Returns a copy of the last column.
     *
     * @return A list representing the last column.
     */
    public List<T> getLastCol() {
        return getCol(numCols - 1);
    }

    /**
     * Returns a copy of all rows in the grid.
     *
     * @return A list of lists representing the rows.
     */
    public List<List<T>> getRows() {
        List<List<T>> rows = new ArrayList<>(numRows);
        for (List<T> row : grid) {
            rows.add(new ArrayList<>(row));
        }
        return rows;
    }

    /**
     * Returns a copy of all columns in the grid.
     *
     * @return A list of lists representing the columns.
     */
    public List<List<T>> getCols() {
        List<List<T>> cols = new ArrayList<>(numCols);
        for (int col = 0; col < numCols; col++) {
            cols.add(getCol(col));
        }
        return cols;
    }

    /**
     * Returns a stream of rows in the grid.
     *
     * @return A stream of lists representing the rows.
     */
    public Stream<List<T>> streamRows() {
        return grid.stream().map(ArrayList::new);
    }

    /**
     * Returns a stream of columns in the grid.
     *
     * @return A stream of lists representing the columns.
     */
    public Stream<List<T>> streamCols() {
        return getCols().stream();
    }

    /**
     * Counts how many adjacent cells match the given value.
     *
     * The count includes the eight possible directions around the specified cell,
     * but not the cell itself.
     *
     * @param row The row index of the cell to check around.
     * @param col The column index of the cell to check around.
     * @param value The value to count in adjacent cells.
     * @return The number of adjacent cells matching the given value.
     * @throws IndexOutOfBoundsException if row or column is out of bounds.
     * @throws NullPointerException     if value is null.
     */
    public int countAdjacent(final int row, final int col, T value) {
        if (!isInBounds(row, col)) {
            throw new IndexOutOfBoundsException("Row or column out of bounds");
        }
        Objects.requireNonNull(value);

        int count = 0;
        for (Direction direction : Direction.values()) {
            final int adjacentRow = row + direction.getDr();
            final int adjacentCol = col + direction.getDc();
            if (isInBounds(adjacentRow, adjacentCol) && grid.get(adjacentRow).get(adjacentCol).equals(value)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns a new Grid that is the transpose of this grid.
     *
     * @return a new transposed Grid.
     */
    public Grid<T> transpose() {
        return new Grid<>(getCols());
    }

    /**
     * Finds the first occurrence of the specified value in the grid.
     *
     * @param value The value to search for.
     * @return An Optional containing the Location of the first occurrence, or empty if not found.
     * @throws NullPointerException if value is null.
     */
    public Optional<Location> findFirst(T value) {
        Objects.requireNonNull(value, "Value must not be null");

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (grid.get(row).get(col).equals(value)) {
                    return Optional.of(new Location(row, col));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grid<?> other)) {
            return false;
        }
        if (this.numRows != other.numRows || this.numCols != other.numCols) {
            return false;
        }
        return Objects.equals(this.grid, other.grid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.numRows, this.numCols, this.grid);
    }

    @Override
    public String toString() {
        return grid.stream()
                .map(row -> row.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining()))
                .collect(Collectors.joining("\n"));
    }
}
