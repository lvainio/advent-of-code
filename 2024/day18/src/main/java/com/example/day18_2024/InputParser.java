package com.example.day18_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputParser {

    private List<Coordinate> coordinates = null;

    public List<Coordinate> getCoordinates() {
        return this.coordinates;
    }

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            Pattern pattern = Pattern.compile("\\d+");
            this.coordinates = reader.lines().map(s -> {
                Matcher matcher = pattern.matcher(s);
                matcher.find();
                int x = Integer.parseInt(matcher.group());
                matcher.find();
                int y = Integer.parseInt(matcher.group());
                return new Coordinate(x, y);
            }).collect(Collectors.toList());
            
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } 
    }
}
