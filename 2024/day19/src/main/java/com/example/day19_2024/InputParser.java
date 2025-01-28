package com.example.day19_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputParser {

    private List<String> patterns = null;
    private List<String> designs = null;

    public List<String> getPatterns() {
        return this.patterns;
    }

    public List<String> getDesigns() {
        return this.designs;
    }

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String[] PatternsAndDesigns =
                    reader.lines().collect(Collectors.joining("\n")).split("\r?\n\r?\n");

            this.patterns = Arrays.asList(PatternsAndDesigns[0].split(", "));
            this.designs = Arrays.asList(PatternsAndDesigns[1].split("\r?\n"));

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
