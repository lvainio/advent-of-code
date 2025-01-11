package com.example.day11_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.List;

public class InputParser {

    private List<Long> stones = null;

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            this.stones = Arrays.stream(reader.readLine().split(" "))
                .map(Long::parseLong)
                .toList();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public List<Long> getStones() {
        return this.stones;
    }
}
