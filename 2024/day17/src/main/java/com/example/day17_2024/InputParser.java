package com.example.day17_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputParser {

    private long a = 0;
    private long b = 0;
    private long c = 0;
    
    private List<Integer> instructions = new ArrayList<>();

    public long getA() {
        return this.a;
    }

    public long getB() {
        return this.b;
    }

    public long getC() {
        return this.c;
    }

    public List<Integer> getInstructions() {
        return this.instructions;
    }

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String content = reader.lines().collect(Collectors.joining());

            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(content);

            matcher.find();
            this.a = Long.parseLong(matcher.group());

            matcher.find();
            this.b = Long.parseLong(matcher.group());

            matcher.find();
            this.c = Long.parseLong(matcher.group());

            while(matcher.find()) {
                instructions.add(Integer.parseInt(matcher.group()));
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } 
    }
}
