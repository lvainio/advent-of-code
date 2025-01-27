package com.example.day15_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputParser {

    private Cell[][] map1 = null;
    private Cell[][] map2 = null;
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

            this.map1 = Arrays.stream(mapAndMoves[0].split("\r?\n"))
                            .map(line -> line.chars()                 
                                            .mapToObj(c -> Cell.fromChar((char) c))
                                            .toArray(Cell[]::new))                 
                            .toArray(Cell[][]::new); 

            this.moves = mapAndMoves[1].replaceAll("\\s+", "")
                                    .chars()        
                                    .mapToObj(c -> Move.fromChar((char) c)) 
                                    .collect(Collectors.toList()); 

            this.map2 = new Cell[this.map1.length][this.map1[0].length*2];
            for (int row = 0; row < this.map1.length; row++) {
                for (int col = 0; col < this.map1[0].length; col++) {
                    switch (this.map1[row][col]) {
                        case WALL -> {
                            this.map2[row][col*2] = Cell.WALL;
                            this.map2[row][col*2+1] = Cell.WALL;
                        }
                        case EMPTY -> {
                            this.map2[row][col*2] = Cell.EMPTY;
                            this.map2[row][col*2+1] = Cell.EMPTY;
                        }
                        case BOX -> {
                            this.map2[row][col*2] = Cell.BOXLEFT;
                            this.map2[row][col*2+1] = Cell.BOXRIGHT;
                        }
                        case ROBOT -> {
                            this.map2[row][col*2] = Cell.ROBOT;
                            this.map2[row][col*2+1] = Cell.EMPTY;
                        }
                        default -> throw new IllegalArgumentException("Invalid cell");
                    }
                }
            }
           
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } 
    }

    public Cell[][] getMap1() {
        return this.map1;
    } 

    public Cell[][] getMap2() {
        return this.map2;
    }

    public List<Move> getMoves() {
        return this.moves;
    }
}
