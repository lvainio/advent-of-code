package com.example.day10_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;

public class InputParser {

    private int[][] grid = null;

    public void parseInputFile(String inputFile) throws FileNotFoundException {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            throw new FileNotFoundException("Resource '" + inputFile + "' not found in the classpath.");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            this.grid = reader.lines()
                .map(line -> line.chars() 
                    .map(Character::getNumericValue) 
                    .toArray()) 
                .toArray(int[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public int[][] getGrid() {
        return this.grid;
    }
}
