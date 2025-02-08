package com.example.day03_2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class InputParser {

    private List<Move> moves;

    public List<Move> getMoves() {
        return this.moves;
    }

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            this.moves = reader.readLine().chars().mapToObj(c -> 
                Move.fromChar((char) c)
            ).toList(); 
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
