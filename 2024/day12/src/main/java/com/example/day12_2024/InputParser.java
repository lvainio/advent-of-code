package com.example.day12_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputParser {

    private char[][] grid = null;

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            this.grid = reader.lines()
                .map(line -> line.toCharArray())
                .toArray(char[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public char[][] getGrid() {
        return this.grid;
    }
}
