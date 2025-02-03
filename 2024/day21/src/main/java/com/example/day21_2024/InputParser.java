package com.example.day21_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class InputParser {

    List<String> codes = null;

    public List<String> getCodes() {
        return this.codes;
    }
    
    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            this.codes = reader.lines().toList();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
