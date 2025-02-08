package com.example.day02_2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class InputParser {

    private List<Present> presents;

    public List<Present> getPresents() {
        return this.presents;
    }

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            this.presents = reader.lines().map(line -> {
                String[] nums = line.split("x");
                int value1 = Integer.parseInt(nums[0]);
                int value2 = Integer.parseInt(nums[1]);
                int value3 = Integer.parseInt(nums[2]);
                return new Present(value1, value2, value3);
            }).toList();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
