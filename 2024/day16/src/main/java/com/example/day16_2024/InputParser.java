package com.example.day16_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class InputParser {

    HashMap<Node, HashSet<Node>> map = new HashMap<>();
    Node startNode = null;
    Node endNode = null;

    public HashMap<Node, HashSet<Node>> getMap() {
        return this.map;
    }

    public Node getStartNode() {
        return this.startNode;
    }

    public Node getEndNode() {
        return this.endNode;
    }

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            char[][] grid = reader.lines()
                .map(s -> s.toCharArray())
                .toArray(char[][]::new);

            List<Node> nodes = new ArrayList<>();
            IntStream.range(0, grid.length).forEach(row -> 
                IntStream.range(0, grid[0].length).forEach(col -> {
                    if (isNode(row, col, grid)) {
                        nodes.add(new Node(row, col));
                        if (grid[row][col] == 'S') {
                            this.startNode = new Node(row, col);
                        }
                        if (grid[row][col] == 'E') {
                            this.endNode = new Node(row, col);
                        }
                    }
                })
            );

            for (int i = 0; i < nodes.size(); i++) {
                for (int j = i+1; j < nodes.size(); j++) {
                    if (isConnected(nodes.get(i), nodes.get(j), grid)) {
                        this.map.computeIfAbsent(nodes.get(i), _ -> new HashSet<>()).add(nodes.get(j));
                        this.map.computeIfAbsent(nodes.get(j), _ -> new HashSet<>()).add(nodes.get(i));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } 
    }

    private boolean isNode(int row, int col, char[][] grid) {
        if (grid[row][col] == '#') {
            return false;
        }

        int count = 0;
        if (grid[row][col-1] == '.') {
            count++;
        }
        if (grid[row][col+1] == '.') {
            count++;
        }
        if (grid[row+1][col] == '.') {
            count++;
        }
        if (grid[row-1][col] == '.') {
            count++;
        }

        boolean isCorner = (count == 2) && (grid[row-1][col] != grid[row+1][col]);

        return grid[row][col] == 'S' 
            || grid[row][col] == 'E'
            || count > 2
            || isCorner;
    }

    private boolean isConnected(Node n1, Node n2, char[][] grid) {
        if (n1 == n2) {
            return false;
        }

        boolean isRowAligned = n1.row() == n2.row();
        boolean isColAligned = n1.col() == n2.col();
        if (!isRowAligned && !isColAligned) {
            return false;
        }

        if (isRowAligned) {
            int row = n1.row();
            int minCol = Math.min(n1.col(), n2.col());
            int maxCol = Math.max(n1.col(), n2.col());
            for (int col = minCol+1; col < maxCol; col++) {
                if (grid[row][col] != '.') {
                    return false;
                }
            }
        }

        if (isColAligned) {
            int col = n1.col();
            int minRow = Math.min(n1.row(), n2.row());
            int maxRow = Math.max(n1.row(), n2.row());
            for (int row = minRow+1; row < maxRow; row++) {
                if (grid[row][col] != '.') {
                    return false;
                }
            }
        }

        return true;
    }
}
