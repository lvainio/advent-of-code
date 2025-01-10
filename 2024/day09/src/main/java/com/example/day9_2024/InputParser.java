package com.example.day9_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

public class InputParser {

    private List<Integer> memory = null;

    public void parseInputFile(String inputFile) throws FileNotFoundException {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            throw new FileNotFoundException("Resource '" + inputFile + "' not found in the classpath.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String content = reader.readLine();
            
            List<Integer> digits = new ArrayList<>();
            for (char ch : content.toCharArray()) {
                digits.add(Character.getNumericValue(ch));
            }

            List<Integer> memory = new ArrayList<>();
            for (int i = 0; i < digits.size(); i++) {
                if (i % 2 == 0) {
                    for (int j = 0; j < digits.get(i); j++) {
                        memory.add(i / 2);
                    }
                } else {
                    for (int j = 0; j < digits.get(i); j++) {
                        memory.add(-1);
                    }
                }
            }
            this.memory = memory;

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public List<Integer> getMemory() {
        return this.memory;
    }
}
