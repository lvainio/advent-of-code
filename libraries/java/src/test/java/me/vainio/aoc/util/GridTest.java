package me.vainio.aoc.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    @Test
    void ofCharsCreatesCorrectDimensionsAndValues() {
        Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertEquals(2, grid.rows());
        assertEquals(2, grid.cols());
        assertEquals('a', grid.get(0, 0));
        assertEquals('b', grid.get(0, 1));
        assertEquals('c', grid.get(1, 0));
        assertEquals('d', grid.get(1, 1));
    }

    @Test
    void ofDigitsCreatesCorrectDimensionsAndValues() {
        Grid<Integer> grid = Grid.ofDigits("12\n34");

        assertEquals(2, grid.rows());
        assertEquals(2, grid.cols());
        assertEquals(1, grid.get(0, 0));
        assertEquals(2, grid.get(0, 1));
        assertEquals(3, grid.get(1, 0));
        assertEquals(4, grid.get(1, 1));
    }

    @Test
    void ofDigitsThrowsOnNonDigitInput() {
        assertThrows(IllegalArgumentException.class, () -> Grid.ofDigits("1a\n23"));
    }

    @Test
    void ofCharsThrowsOnNullInput() {
        assertThrows(IllegalArgumentException.class, () -> Grid.ofChars(null));
    }

    @Test
    void getThrowsOnOutOfBounds() {
        Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(2, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(0, 2));
    }

    @Test
    void setUpdatesValue() {
        Grid<Character> grid = Grid.ofChars("ab\ncd");

        grid.set(0, 1, 'x');

        assertEquals('x', grid.get(0, 1));
    }

    @Test
    void setThrowsOnOutOfBounds() {
        Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(IndexOutOfBoundsException.class, () -> grid.set(-1, 0, 'x'));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.set(0, -1, 'x'));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.set(2, 0, 'x'));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.set(0, 2, 'x'));
    }

    @Test
    void setThrowsOnNullValue() {
        Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(NullPointerException.class, () -> grid.set(0, 0, null));
    }

    @Test
    void isInBoundsWorksForEdgesAndOutside() {
        Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertTrue(grid.isInBounds(0, 0));
        assertTrue(grid.isInBounds(1, 1));
        assertFalse(grid.isInBounds(-1, 0));
        assertFalse(grid.isInBounds(0, -1));
        assertFalse(grid.isInBounds(2, 0));
        assertFalse(grid.isInBounds(0, 2));
    }

    @Test
    void countAdjacentCountsCorrectlyCenter() {
        Grid<Integer> grid = Grid.ofDigits(
                "111\n" +
                        "101\n" +
                        "111"
        );

        int count = grid.countAdjacent(1, 1, 1);

        assertEquals(8, count);
    }

    @Test
    void countAdjacentCountsCorrectlyCorner() {
        Grid<Integer> grid = Grid.ofDigits(
                "111\n" +
                        "101\n" +
                        "111"
        );

        int count = grid.countAdjacent(0, 0, 1);

        assertEquals(2, count);
    }

    @Test
    void countAdjacentThrowsOnOutOfBounds() {
        Grid<Integer> grid = Grid.ofDigits("12\n34");

        assertThrows(IllegalArgumentException.class, () -> grid.countAdjacent(-1, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> grid.countAdjacent(0, -1, 1));
        assertThrows(IllegalArgumentException.class, () -> grid.countAdjacent(2, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> grid.countAdjacent(0, 2, 1));
    }

    @Test
    void countAdjacentThrowsOnNullValue() {
        Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(NullPointerException.class, () -> grid.countAdjacent(0, 0, null));
    }

    @Test
    void copyConstructorCreatesDeepCopyOfRows() {
        Grid<Character> original = Grid.ofChars("ab\ncd");
        Grid<Character> copy = new Grid<>(original);

        assertEquals(original.rows(), copy.rows());
        assertEquals(original.cols(), copy.cols());
        assertEquals(original.get(0, 0), copy.get(0, 0));
        assertEquals(original.get(1, 1), copy.get(1, 1));

        original.set(0, 0, 'x');
        assertEquals('x', original.get(0, 0));
        assertEquals('a', copy.get(0, 0));

        copy.set(1, 1, 'y');
        assertEquals('d', original.get(1, 1));
        assertEquals('y', copy.get(1, 1));
    }
}
