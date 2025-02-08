package com.example.day05_2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class InputParser {

    private List<String> strings;

    public List<String> getStrings() {
        return this.strings;
    }

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            this.strings = reader.lines().toList();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
