package com.example.day20_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputParser {
    private Cell[][] grid = null;
    private Node startPosition = null;
    private Node endPosition = null;

    public Cell[][] getGrid() {
        return grid;
    }

    public Node getStartPosition() {
        return startPosition;
    }

    public Node getEndPosition() {
        return endPosition;
    }

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            this.grid = reader.lines()
                            .map(line -> line.chars()
                                .mapToObj(c -> Cell.charToCell((char) c))
                                .toArray(Cell[]::new))
                            .toArray(Cell[][]::new);
            for (int row = 0; row < this.grid.length; row++) {
                for (int col = 0; col < this.grid[0].length; col++) {
                    if (this.grid[row][col] == Cell.START) {
                        this.startPosition = new Node(row, col);
                        this.grid[row][col] = Cell.EMPTY;
                    }
                    if (this.grid[row][col] == Cell.END) {
                        this.endPosition = new Node(row, col);
                        this.grid[row][col] = Cell.EMPTY;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
