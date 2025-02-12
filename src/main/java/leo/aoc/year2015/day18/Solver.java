package leo.aoc.year2015.day18;

import leo.aoc.AbstractSolver;
import leo.aoc.util.Direction;

public class Solver extends AbstractSolver {

    private static final char ON = '#';
    private static final char OFF = '.';

    public Solver(String input) {
        super(input);
    }

    @Override
    public String solvePart1() {
        char[][] grid = input.lines()
            .map(String::toCharArray)
            .toArray(char[][]::new);

        for (int i = 0; i < 100; i++) {
            grid = step(grid);
        } 

        return Integer.toString(countNumLightsOn(grid));
    }

    @Override
    public String solvePart2() {
        char[][] grid = input.lines()
            .map(String::toCharArray)
            .toArray(char[][]::new);

        grid[0][0] = ON;
        grid[0][99] = ON;
        grid[99][0] = ON;
        grid[99][99] = ON;

        for (int i = 0; i < 100; i++) {
            grid = step2(grid);
        } 

        return Integer.toString(countNumLightsOn(grid));
    }

    private int countNumLightsOn(char[][] grid) {
        int count = 0;
        for (int row = 0; row < 100; row++) {
            for (int col = 0; col < 100; col++) {
                if (grid[row][col] == ON) {
                    count++;
                }
            }
        }
        return count;
    }

    private char[][] step(char[][] grid) {
        char[][] newGrid = new char[100][100];
        for (int row = 0; row < 100; row++) {
            for (int col = 0; col < 100; col++) {
                int count = countSurroundingOnLights(row, col, grid);
                if (grid[row][col] == ON && (count == 2 || count == 3)) {
                    newGrid[row][col] = ON;
                } else if (grid[row][col] == ON) {
                    newGrid[row][col] = OFF;
                } else if (grid[row][col] == OFF && count == 3) {
                    newGrid[row][col] = ON;
                } else {
                    newGrid[row][col] = OFF;
                }
            }
        }
        return newGrid;
    }

    private char[][] step2(char[][] grid) {
        char[][] newGrid = new char[100][100];
        for (int row = 0; row < 100; row++) {
            for (int col = 0; col < 100; col++) {
                if ((row == 0 || row == 99) && (col == 0 || col == 99)) {
                    newGrid[row][col] = ON;
                    continue;
                }
                int count = countSurroundingOnLights(row, col, grid);
                if (grid[row][col] == ON && (count == 2 || count == 3)) {
                    newGrid[row][col] = ON;
                } else if (grid[row][col] == ON) {
                    newGrid[row][col] = OFF;
                } else if (grid[row][col] == OFF && count == 3) {
                    newGrid[row][col] = ON;
                } else {
                    newGrid[row][col] = OFF;
                }
            }
        }
        return newGrid;
    }

    private int countSurroundingOnLights(int row, int col, char[][] grid) {
        int count = 0;
        for (Direction dir : Direction.getAllDirections()) {
            int newRow = row + dir.getDr();
            int newCol = col + dir.getDc();
            if (newRow >= 0 
                && newRow < 100 
                && newCol >= 0 
                && newCol < 100 
                && grid[newRow][newCol] == ON) {
                    count++;
            }
        }
        return count;
    }
}
