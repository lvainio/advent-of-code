package leo.aoc.year2024.day4;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private char[][] grid;

    public Solver(String input) {
        super(input);

        this.grid = input.lines()
            .map(line -> line.toCharArray())
            .toArray(char[][]::new); 
    }
    
    @Override
    public String solvePart1() {
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (isNorthXMAS(row, col)) {
                    count++;
                }
                if (isSouthXMAS(row, col)) {
                    count++;
                }
                if (isWestXMAS(row, col)) {
                    count++;
                }
                if (isEastXMAS(row, col)) {
                    count++;
                }
                if (isNorthEastXMAS(row, col)) {
                    count++;
                }
                if (isNorthWestXMAS(row, col)) {
                    count++;
                }
                if (isSouthWestXMAS(row, col)) {
                    count++;
                }
                if (isSouthEastXMAS(row, col)) {
                    count++;
                }
            }
        }
        return Integer.toString(count);
    }

    @Override
    public String solvePart2() {
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (isXMAS(grid, row, col)) {
                    count++;
                }
            }
        }
        return Integer.toString(count);
    }

    private boolean isNorthXMAS(int row, int col) {
        if (row < 3) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row-1][col] == 'M' &&
           grid[row-2][col] == 'A' && grid[row-3][col] == 'S';
    }

    private boolean isSouthXMAS(int row, int col) {
        if (row > grid.length-4) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row+1][col] == 'M' &&
           grid[row+2][col] == 'A' && grid[row+3][col] == 'S';
    }

    private boolean isWestXMAS(int row, int col) {
        if (col < 3) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row][col-1] == 'M' &&
           grid[row][col-2] == 'A' && grid[row][col-3] == 'S';
    }

    private boolean isEastXMAS(int row, int col) {
        if (col > grid[0].length - 4) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row][col+1] == 'M' &&
        grid[row][col+2] == 'A' && grid[row][col+3] == 'S';
    }

    private boolean isNorthEastXMAS(int row, int col) {
        if (col > grid[0].length - 4 || row < 3) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row-1][col+1] == 'M' &&
        grid[row-2][col+2] == 'A' && grid[row-3][col+3] == 'S';
    }

    private boolean isNorthWestXMAS(int row, int col) {
        if (col < 3 || row < 3) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row-1][col-1] == 'M' &&
        grid[row-2][col-2] == 'A' && grid[row-3][col-3] == 'S';
    }

    private boolean isSouthWestXMAS(int row, int col) {
        if (col < 3 || row > grid.length-4) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row+1][col-1] == 'M' &&
        grid[row+2][col-2] == 'A' && grid[row+3][col-3] == 'S';
    }

    private boolean isSouthEastXMAS(int row, int col) {
        if (col > grid[0].length - 4 || row > grid.length-4) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row+1][col+1] == 'M' &&
        grid[row+2][col+2] == 'A' && grid[row+3][col+3] == 'S';
    }  

    private boolean isXMAS(char[][] grid, int row, int col) {
        if (grid[row][col] != 'A') {
            return false;
        }
        if (row < 1 || row > grid.length - 2 || col < 1 || col > grid[0].length - 2) {
            return false;
        }

        char[] downwardDiagonalChars = {grid[row-1][col-1], grid[row][col], grid[row+1][col+1]};
        String downwardDiagonal = new String(downwardDiagonalChars);

        char[] upwardDiagonalChars = {grid[row+1][col-1], grid[row][col], grid[row-1][col+1]};
        String upwardDiagonal = new String(upwardDiagonalChars);

        return (downwardDiagonal.equals("MAS") || downwardDiagonal.equals("SAM")) &&
            (upwardDiagonal.equals("MAS") || upwardDiagonal.equals("SAM"));
    }
}
