package com.example.day14_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class InputParser {

    private List<Robot> robots = null;

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            Pattern pattern = Pattern.compile("-?\\d+");
            this.robots = reader.lines().map(s -> {
                Matcher matcher = pattern.matcher(s);
                int x = this.getNext(matcher);
                int y = this.getNext(matcher);
                int vx = this.getNext(matcher);
                int vy = this.getNext(matcher);
                return new Robot(x, y, vx, vy);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public List<Robot> getRobots() {
        return this.robots;
    }

    private int getNext(Matcher matcher) {
        matcher.find();
        return Integer.parseInt(matcher.group());
    }
}
