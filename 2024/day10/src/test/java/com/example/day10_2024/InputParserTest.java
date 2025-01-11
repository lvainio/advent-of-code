package com.example.day10_2024;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class InputParserTest {

    @Test
    public void testParseInputFile() throws Exception {
        InputParser parser = new InputParser();
        String testFileName = "test_input1.txt";  

        parser.parseInputFile(testFileName);
        int[][] grid = parser.getGrid();

        assertNotNull(grid, "Grid should not be null");
        assertEquals(3, grid.length, "Grid should have 3 rows");
        assertArrayEquals(new int[]{1, 2, 3}, grid[0], "First row does not match");
        assertArrayEquals(new int[]{4, 5, 6}, grid[1], "Second row does not match");
        assertArrayEquals(new int[]{7, 8, 9}, grid[2], "Third row does not match");
    }

    @Test
    public void testFileNotFound() {
        InputParser parser = new InputParser();
        String invalidFileName = "invalid.txt";

        assertThrows(FileNotFoundException.class, () -> parser.parseInputFile(invalidFileName));
    }
}
