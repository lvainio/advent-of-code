package com.example.day08_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InputReader {

    private HashMap<Character, ArrayList<Node>> antennas;
    private int numRows;
    private int numCols;

    public InputReader() {
        HashMap<Character, ArrayList<Node>> antennas = new HashMap<>();

        InputStream inputStream = InputReader.class.getClassLoader().getResourceAsStream("input.txt");
        if (inputStream == null) {
            System.out.println("Error: file not found!");
            System.exit(1);
        } else {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append(System.lineSeparator());
                }
                String fileContent = content.toString();
                String[] lines = fileContent.split("\r?\n");
                char[][] grid = Arrays.stream(lines)
                      .map(String::toCharArray)  
                      .toArray(char[][]::new);
                this.numRows = grid.length;
                this.numCols = grid[0].length;
                for (int row = 0; row < grid.length; row++) {
                    for (int col = 0; col < grid[0].length; col++) {
                        if (grid[row][col] != '.') {
                            antennas
                                .computeIfAbsent(grid[row][col], k -> new ArrayList<>())
                                .add(new Node(row, col));
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading the file: " + e.getMessage());
            }
        }
        this.antennas = antennas;
    }

    public HashMap<Character, ArrayList<Node>> getAntennas() {
        return this.antennas;
    }

    public int getNumRows() {
        return this.numRows;
    }

    public int getNumCols() {
        return this.numCols;
    }
}
