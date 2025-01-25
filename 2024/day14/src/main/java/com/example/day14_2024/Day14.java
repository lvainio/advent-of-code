package com.example.day14_2024;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day14 {
    public static void main( String[] args ) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");
        List<Robot> robots = parser.getRobots();

        System.out.println("Part1: " + solvePart1(new ArrayList<Robot>(robots)));
        System.out.println("Part2: " + solvePart2(new ArrayList<Robot>(robots)));
    }

    public static int solvePart1(List<Robot> robots) {
        int width = 101;
        int height = 103;

        IntStream.range(0, 100).forEach(_ -> {
            robots.forEach(robot -> robot.step(width, height));
        });

        int[] counts = new int[4]; 
        robots.forEach(robot -> {
            if (robot.isInNwQuadrant(width, height)) {
                counts[0]++;
            } else if (robot.isInNeQuadrant(width, height)) {
                counts[1]++;
            } else if (robot.isInSwQuadrant(width, height)) {
                counts[2]++;
            } else if (robot.isInSeQuadrant(width, height)) {
                counts[3]++;
            }
        });

        return counts[0] * counts[1] * counts[2] * counts[3];
    }

    public static int solvePart2(List<Robot> robots) {
        int width = 101;
        int height = 103;

        for (int sec = 0; sec < 1_000_000; sec++) {

            char[][] grid = robotsToGrid(robots, width, height);

            if (isPossibleXmasTree(grid)) {
                System.out.println("Seconds passed: " + sec);
                printGrid(grid);
                System.out.println();
                System.out.println();
            }

            if (sec == 7371 || sec == 7373 || sec == 7372) {
                System.out.println("Seconds passed: " + sec);
                printGrid(grid);
                System.out.println();
                System.out.println();
            }
            
            for (Robot robot : robots) {
                robot.step(width, height);
            }
        }
        return 0;
    }

    public static char[][] robotsToGrid(List<Robot> robots, int width, int height) {
        char[][] grid = new char[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = '.';
            }
        }

        for (Robot robot : robots) {
            grid[robot.getY()][robot.getX()] = '#';
        }

        return grid;
    }

    public static void printGrid(char[][] grid) {
        for (int i = 0; i < grid.length; i++) { 
            for (int j = 0; j < grid[i].length; j++) { 
                System.out.print(grid[i][j]); 
            }
            System.out.println(); 
        }
    }

    public static boolean isPossibleXmasTree(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            

            String rowStr = new String(grid[row]);

            int sub = longestHashSubstring(rowStr);
            
            if (sub > 10) {
                return true;
            }

        }

        return false;
    }

    public static int longestHashSubstring(String rowStr) {
        int maxCount = 0, currentCount = 0;
    
        for (char c : rowStr.toCharArray()) {
            if (c == '#') {
                currentCount++;
                maxCount = Math.max(maxCount, currentCount);
            } else {
                currentCount = 0; // Reset the count if the character is not '#'
            }
        }
    
        return maxCount;
    }
}
