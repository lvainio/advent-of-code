package com.example.day9_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class InputParser {

    private final int FREE_SPACE = -1;
    private List<Integer> memory = null;

    public InputParser() {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream("input.txt");

        if (inputStream == null) {
            System.out.println("Error: file not found!");
            System.exit(1);
        } else {
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
                            memory.add(this.FREE_SPACE);
                        }
                    }
                }
                this.memory = memory;

            } catch (IOException e) {
                System.out.println("Error reading the file: " + e.getMessage());
            }
        }
    }

    public List<Integer> getMemory() {
        return this.memory;
    }
}
