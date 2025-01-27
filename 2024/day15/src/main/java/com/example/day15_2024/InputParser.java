package com.example.day15_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputParser {

    private Cell[][] map = null;
    private List<Move> moves = null;

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String[] mapAndMoves = reader.lines()
                                    .collect(Collectors.joining("\n"))
                                    .split("\r?\n\r?\n");

            this.map = Arrays.stream(mapAndMoves[0].split("\r?\n"))
                            .map(line -> line.chars()                 
                                            .mapToObj(c -> Cell.fromChar((char) c))
                                            .toArray(Cell[]::new))                 
                            .toArray(Cell[][]::new); 

            this.moves = mapAndMoves[1].replaceAll("\\s+", "")
                                    .chars()        
                                    .mapToObj(c -> Move.fromChar((char) c)) 
                                    .collect(Collectors.toList()); 
           
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } 
    }

    public Cell[][] getMap() {
        return this.map;
    } 

    public List<Move> getMoves() {
        return this.moves;
    }
}
