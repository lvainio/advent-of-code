package com.example.day10_2024;

import java.io.FileNotFoundException;
import java.util.stream.IntStream;
import java.util.HashSet;

public class Day10 {

    private record Node(int x, int y) {}

    public static void main( String[] args ) {
        final String inputFile = "input.txt";
        InputParser parser = new InputParser();
        try {
            parser.parseInputFile(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int[][] grid = parser.getGrid();

        System.out.println("Part1: " + solvePart1(grid));
        System.out.println("Part2: " + solvePart2(grid));
    }

    public static int solvePart1(int[][] grid) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        return IntStream.range(0, numRows)
            .map(row -> IntStream.range(0, numCols)
                .reduce(0, (sum, col) -> sum + countTrailheadScore(grid, row, col, 0, new HashSet<>())))
            .sum();   
    }

    public static int solvePart2(int[][] grid) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        return IntStream.range(0, numRows)
            .map(row -> IntStream.range(0, numCols)
                .reduce(0, (sum, col) -> sum + countTrailheadRating(grid, row, col, 0)))
            .sum(); 
    }

    public static int countTrailheadScore(int[][] grid, int row, int col, int currentHeight, HashSet<Node> visited) {
        // Out of bounds
        if (row < 0 || row >= grid.length ||
            col < 0 || col >= grid[0].length) {
                return 0;
        }
        // Invalid step
        if (grid[row][col] != currentHeight) {
            return 0;
        } 
        // Top reached
        final int MAX_HEIGHT = 9;
        if (grid[row][col] == MAX_HEIGHT && !visited.contains(new Node(row, col))) {
            visited.add(new Node(row, col));
            return 1;
        }
        // Walk in each direction
        return countTrailheadScore(grid, row, col-1, currentHeight+1, visited) +
                countTrailheadScore(grid, row, col+1, currentHeight+1, visited) +
                countTrailheadScore(grid, row-1, col, currentHeight+1, visited) +
                countTrailheadScore(grid, row+1, col, currentHeight+1, visited);
    }

    public static int countTrailheadRating(int[][] grid, int row, int col, int currentHeight) {
        // Out of bounds
        if (row < 0 || row >= grid.length ||
            col < 0 || col >= grid[0].length) {
                return 0;
        }
        // Invalid step
        if (grid[row][col] != currentHeight) {
            return 0;
        } 
        // Top reached
        final int MAX_HEIGHT = 9;
        if (grid[row][col] == MAX_HEIGHT) {
            return 1;
        }
        // Walk in each direction
        return countTrailheadRating(grid, row, col-1, currentHeight+1) +
                countTrailheadRating(grid, row, col+1, currentHeight+1) +
                countTrailheadRating(grid, row-1, col, currentHeight+1) +
                countTrailheadRating(grid, row+1, col, currentHeight+1);
    }
}
