package me.vainio.aoc.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    @Test
    void copyConstructorThrowsOnNull() {
        assertThrows(NullPointerException.class, () -> new Grid<Character>(null));
    }

    @Test
    void copyConstructorCreatesIdenticalGrid() {
        final Grid<Character> original = Grid.ofChars("ab\ncd");
        final Grid<Character> copy = new Grid<>(original);

        assertEquals(original.numRows(), copy.numRows());
        assertEquals(original.numCols(), copy.numCols());
        assertEquals(original, copy);
    }

    @Test
    void modifyingCopyDoesNotAffectOriginal() {
        final Grid<Character> original = Grid.ofChars("ab\ncd");
        final Grid<Character> copy = new Grid<>(original);

        copy.set(0, 0, 'x');
        assertNotEquals(original.get(0, 0), copy.get(0, 0));
    }

    @Test
    void ofCharsThrowsOnNull() {
        assertThrows(NullPointerException.class, () -> Grid.ofChars(null));
    }

    @Test
    void ofCharsThrowsOnEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> Grid.ofChars(""));
    }

    @Test
    void ofCharsThrowsOnUnevenRows() {
        assertThrows(IllegalArgumentException.class, () -> Grid.ofChars("ab\nc"));
    }

    @Test
    void ofCharsCreatesCorrectGrid() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertEquals(2, grid.numRows());
        assertEquals(2, grid.numCols());
        assertEquals('a', grid.get(0, 0));
        assertEquals('b', grid.get(0, 1));
        assertEquals('c', grid.get(1, 0));
        assertEquals('d', grid.get(1, 1));
    }

    @Test
    void ofCharsWorksWithSingleCharacter() {
        final Grid<Character> grid = Grid.ofChars("x");

        assertEquals(1, grid.numRows());
        assertEquals(1, grid.numCols());
        assertEquals('x', grid.get(0, 0));
    }

    @Test
    void ofDigitsThrowsOnNull() {
        assertThrows(NullPointerException.class, () -> Grid.ofDigits(null));
    }

    @Test
    void ofDigitsThrowsOnEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> Grid.ofDigits(""));
    }

    @Test
    void ofDigitsThrowsOnUnevenRows() {
        assertThrows(IllegalArgumentException.class, () -> Grid.ofDigits("12\n3"));
    }

    @Test
    void ofDigitsThrowsOnNonDigitCharacters() {
        assertThrows(IllegalArgumentException.class, () -> Grid.ofDigits("1a\n23"));
    }

    @Test
    void ofDigitsCreatesCorrectGrid() {
        final Grid<Integer> grid = Grid.ofDigits("12\n34");

        assertEquals(2, grid.numRows());
        assertEquals(2, grid.numCols());
        assertEquals(1, grid.get(0, 0));
        assertEquals(2, grid.get(0, 1));
        assertEquals(3, grid.get(1, 0));
        assertEquals(4, grid.get(1, 1));
    }

    @Test
    void ofDigitsWorksWithSingleDigit() {
        final Grid<Integer> grid = Grid.ofDigits("7");

        assertEquals(1, grid.numRows());
        assertEquals(1, grid.numCols());
        assertEquals(7, grid.get(0, 0));
    }

    @Test
    void subGridThrowsOnEmptyRowRange() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(IllegalArgumentException.class, () -> grid.subGrid(1, 1, 0, 2));
        assertThrows(IllegalArgumentException.class, () -> grid.subGrid(1, 0, 0, 2));
        assertThrows(IllegalArgumentException.class, () -> grid.subGrid(10, -100, 0, 2));
    }

    @Test
    void subGridThrowsOnEmptyColRange() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(IllegalArgumentException.class, () -> grid.subGrid(0, 2, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> grid.subGrid(0, 2, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> grid.subGrid(0, 2, 10, -100));
    }

    @Test
    void subGridThrowsWhenFromIndicesAreOutOfBounds() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(IndexOutOfBoundsException.class, () -> grid.subGrid(-1, 1, 0, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.subGrid(0, 2, -1, 2));
    }

    @Test
    void subGridThrowsWhenToIndicesAreOutOfBounds() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(IndexOutOfBoundsException.class, () -> grid.subGrid(0, 3, 0, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.subGrid(0, 2, 0, 3));
    }

    @Test
    void subGridCanReturnEntireGrid() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");
        final Grid<Character> subGrid = grid.subGrid(0, 2, 0, 2);

        assertEquals(grid, subGrid);
    }

    @Test
    void subGridReturnsCorrectSubGrid() {
        final Grid<Character> grid = Grid.ofChars(
                "abcd\n" +
                "efgh\n" +
                "ijkl\n" +
                "mnop"
        );

        final Grid<Character> actual1 = grid.subGrid(1, 3, 1, 4);
        final Grid<Character> expected1 = Grid.ofChars(
                "fgh\n" +
                "jkl"
        );
        final Grid<Character> actual2 = grid.subGrid(2, 3, 1, 4);
        final Grid<Character> expected2 = Grid.ofChars(
                "jkl"
        );
        final Grid<Character> actual3 = grid.subGrid(0, 4, 0, 2);
        final Grid<Character> expected3 = Grid.ofChars(
                "ab\n" +
                "ef\n" +
                "ij\n" +
                "mn"
        );

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
    }

    @Test
    void numRowsReturnsCorrectValue() {
        final Grid<Character> grid = Grid.ofChars("abc\ndef\nghi");

        assertEquals(3, grid.numRows());
    }

    @Test
    void numColsReturnsCorrectValue() {
        final Grid<Character> grid = Grid.ofChars("abc\ndef\nghi");

        assertEquals(3, grid.numCols());
    }

    @Test
    void isInBoundsReturnsCorrectValues() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertTrue(grid.isInBounds(0, 0));
        assertTrue(grid.isInBounds(1, 1));
        assertTrue(grid.isInBounds(0, 1));
        assertTrue(grid.isInBounds(1, 0));
        assertFalse(grid.isInBounds(-1, 0));
        assertFalse(grid.isInBounds(0, -1));
        assertFalse(grid.isInBounds(2, 0));
        assertFalse(grid.isInBounds(0, 2));
    }

    @Test
    void isInboundsReturnsCorrectValuesWithLocation() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertTrue(grid.isInBounds(new Location(0, 0)));
        assertTrue(grid.isInBounds(new Location(1, 1)));
        assertTrue(grid.isInBounds(new Location(0, 1)));
        assertTrue(grid.isInBounds(new Location(1, 0)));
        assertFalse(grid.isInBounds(new Location(-1, 0)));
        assertFalse(grid.isInBounds(new Location(0, -1)));
        assertFalse(grid.isInBounds(new Location(2, 0)));
        assertFalse(grid.isInBounds(new Location(0, 2)));
    }

    @Test
    void isInboundsThrowsOnNullLocation() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(NullPointerException.class, () -> grid.isInBounds(null));
    }

    @Test
    void getThrowsOnOutOfBounds() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(2, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(0, 2));
    }

    @Test
    void getReturnsCorrectValues() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertEquals('a', grid.get(0, 0));
        assertEquals('b', grid.get(0, 1));
        assertEquals('c', grid.get(1, 0));
        assertEquals('d', grid.get(1, 1));
    }

    @Test
    void getWithLocationThrowsOnNull() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(NullPointerException.class, () -> grid.get(null));
    }

    @Test
    void getWithLocationThrowsOnOutOfBounds() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(new Location(-1, 0)));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(new Location(0, -1)));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(new Location(2, 0)));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(new Location(0, 2)));
    }

    @Test
    void getWithLocationReturnsCorrectValues() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertEquals('a', grid.get(new Location(0, 0)));
        assertEquals('b', grid.get(new Location(0, 1)));
        assertEquals('c', grid.get(new Location(1, 0)));
        assertEquals('d', grid.get(new Location(1, 1)));
    }

    @Test
    void setThrowsOnNullValue() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(NullPointerException.class, () -> grid.set(0, 0, null));
    }

    @Test
    void setThrowsOnOutOfBounds() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(IndexOutOfBoundsException.class, () -> grid.set(-1, 0, 'x'));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.set(0, -1, 'x'));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.set(2, 0, 'x'));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.set(0, 2, 'x'));
    }

    @Test
    void setUpdatesValue() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        grid.set(0, 1, 'x');
        assertEquals('x', grid.get(0, 1));
    }

    @Test
    void getRowThrowsOnOutOfBounds() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(IndexOutOfBoundsException.class, () -> grid.getRow(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getRow(2));
    }

    @Test
    void getRowReturnsCorrectRow() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertEquals(List.of('a', 'b'), grid.getRow(0));
        assertEquals(List.of('c', 'd'), grid.getRow(1));
    }

    @Test
    void modifyingReturnedRowDoesNotAffectGrid() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        List<Character> row = grid.getRow(0);
        row.set(0, 'x');

        assertEquals('a', grid.get(0, 0));
    }

    @Test
    void getFirstRowReturnsCorrectRow() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertEquals(List.of('a', 'b'), grid.getFirstRow());
    }

    @Test
    void getLastRowReturnsCorrectRow() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertEquals(List.of('c', 'd'), grid.getLastRow());
    }

    @Test
    void getColThrowsOnOutOfBounds() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(IndexOutOfBoundsException.class, () -> grid.getCol(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getCol(2));
    }

    @Test
    void getColReturnsCorrectCol() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertEquals(List.of('a', 'c'), grid.getCol(0));
        assertEquals(List.of('b', 'd'), grid.getCol(1));
    }

    @Test
    void modifyingReturnedColDoesNotAffectGrid() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        List<Character> col = grid.getCol(0);
        col.set(0, 'x');

        assertEquals('a', grid.get(0, 0));
    }

    @Test
    void getFirstColReturnsCorrectCol() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertEquals(List.of('a', 'c'), grid.getFirstCol());
    }

    @Test
    void getLastColReturnsCorrectCol() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertEquals(List.of('b', 'd'), grid.getLastCol());
    }

    @Test
    void getRowsReturnsAllRows() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        final List<List<Character>> rows = grid.getRows();
        assertEquals(2, rows.size());
        assertEquals(List.of('a', 'b'), rows.get(0));
        assertEquals(List.of('c', 'd'), rows.get(1));
    }

    @Test
    void getColsReturnsAllCols() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        final List<List<Character>> cols = grid.getCols();
        assertEquals(2, cols.size());
        assertEquals(List.of('a', 'c'), cols.get(0));
        assertEquals(List.of('b', 'd'), cols.get(1));
    }

    @Test
    void streamRowsReturnsAllRows() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        final List<List<Character>> rows = grid.streamRows().toList();
        assertEquals(2, rows.size());
        assertEquals(List.of('a', 'b'), rows.get(0));
        assertEquals(List.of('c', 'd'), rows.get(1));
    }

    @Test
    void streamColsReturnsAllCols() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");

        final List<List<Character>> cols = grid.streamCols().toList();
        assertEquals(2, cols.size());
        assertEquals(List.of('a', 'c'), cols.get(0));
        assertEquals(List.of('b', 'd'), cols.get(1));
    }

    @Test
    void countAdjacentThrowsOnOutOfBounds() {
        Grid<Integer> grid = Grid.ofDigits("12\n34");

        assertThrows(IndexOutOfBoundsException.class, () -> grid.countAdjacent(-1, 0, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.countAdjacent(0, -1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.countAdjacent(2, 0, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.countAdjacent(0, 2, 1));
    }

    @Test
    void countAdjacentThrowsOnNullValue() {
        Grid<Character> grid = Grid.ofChars("ab\ncd");

        assertThrows(NullPointerException.class, () -> grid.countAdjacent(0, 0, null));
    }

    @Test
    void countAdjacentCountsCorrectly() {
        Grid<Integer> grid = Grid.ofDigits(
                "111\n" +
                        "101\n" +
                        "111"
        );

        assertEquals(2, grid.countAdjacent(0, 0, 1));
        assertEquals(4, grid.countAdjacent(0, 1, 1));
        assertEquals(2, grid.countAdjacent(0, 2, 1));
        assertEquals(4, grid.countAdjacent(1, 0, 1));
        assertEquals(8, grid.countAdjacent(1, 1, 1));
        assertEquals(4, grid.countAdjacent(1, 2, 1));
        assertEquals(2, grid.countAdjacent(2, 0, 1));
        assertEquals(4, grid.countAdjacent(2, 1, 1));
        assertEquals(2, grid.countAdjacent(2, 2, 1));
    }

    @Test
    void transposeCreatesCorrectGrid() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");
        final Grid<Character> expected = Grid.ofChars("ac\nbd");
        final Grid<Character> actual = grid.transpose();

        assertEquals(expected, actual);
    }

    @Test
    void transposeTwiceReturnsOriginalGrid() {
        final Grid<Character> grid = Grid.ofChars("ab\ncd");
        final Grid<Character> actual = grid.transpose().transpose();

        assertEquals(grid, actual);
    }

    @Test
    void findFirstReturnsEmptyOnNoMatch() {
        Grid<Character> grid = Grid.ofChars("""
            abc
            def
            """);

        assertTrue(grid.findFirst('x').isEmpty());
    }

    @Test
    void findFirstReturnsCorrectLocation() {
        Grid<Character> grid = Grid.ofChars("""
            abc
            def
            """);

        final Location actual = grid.findFirst('e').orElseThrow();
        final Location expected = new Location(1, 1);
        assertEquals(expected, actual);
    }

    @Test
    void findFirstReturnsFirstMatch() {
        Grid<Character> grid = Grid.ofChars("""
            aba
            bab
            """);

        final Location actual = grid.findFirst('b').orElseThrow();
        final Location expected = new Location(0, 1);
        assertEquals(expected, actual);
    }

    @Test
    void gridEqualsItself() {
        Grid<Character> grid = Grid.ofChars("""
            abc
            def
            """);

        assertEquals(grid, grid);
    }

    @Test
    void gridNotEqualToNull() {
        Grid<Character> grid = Grid.ofChars("""
            abc
            def
            """);

        assertNotEquals(null, grid);
    }

    @Test
    void differentGridsShouldNotBeEqual() {
        Grid<Character> g1 = Grid.ofChars("""
            abc
            def
            """);
        Grid<Character> g2 = Grid.ofChars("""
            abc
            deg
            """);

        assertNotEquals(g1, g2);
    }

    @Test
    void equalGridsHaveEqualHashCodes() {
        Grid<Character> g1 = Grid.ofChars("""
            abc
            def
            """);
        Grid<Character> g2 = Grid.ofChars("""
            abc
            def
            """);

        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }

    @Test
    void differentGridsShouldHaveDifferentHashCodes() {
        Grid<Character> g1 = Grid.ofChars("""
            abc
            def
            """);
        Grid<Character> g2 = Grid.ofChars("""
            abc
            deg
            """);

        assertNotEquals(g1, g2);
        assertNotEquals(g1.hashCode(), g2.hashCode());
    }

    @Test
    void toStringReturnsCorrectRepresentation() {
        Grid<Character> grid = Grid.ofChars("""
            abc
            def
            ghi
            """);

        String expected = "abc\n" +
                          "def\n" +
                          "ghi";
        assertEquals(expected, grid.toString());
    }
}