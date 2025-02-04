package com.example.day22_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class InputParser {

    private List<Long> numbers = null;

    public List<Long> getNumbers() {
        return this.numbers;
    }
    
    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            this.numbers = reader.lines()
                        .map(line -> Long.parseLong(line))
                        .collect(Collectors.toList());
            
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
