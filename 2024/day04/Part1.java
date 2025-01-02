public class Part1 {
    public void solve(char[][] grid) {
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (isNorthXMAS(grid, row, col)) {
                    count++;
                }
                if (isSouthXMAS(grid, row, col)) {
                    count++;
                }
                if (isWestXMAS(grid, row, col)) {
                    count++;
                }
                if (isEastXMAS(grid, row, col)) {
                    count++;
                }
                if (isNorthEastXMAS(grid, row, col)) {
                    count++;
                }
                if (isNorthWestXMAS(grid, row, col)) {
                    count++;
                }
                if (isSouthWestXMAS(grid, row, col)) {
                    count++;
                }
                if (isSouthEastXMAS(grid, row, col)) {
                    count++;
                }
            }
        }
        System.out.println("Part1: " + count);
    }

    private boolean isNorthXMAS(char[][] grid, int row, int col) {
        if (row < 3) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row-1][col] == 'M' &&
           grid[row-2][col] == 'A' && grid[row-3][col] == 'S';
    }

    private boolean isSouthXMAS(char[][] grid, int row, int col) {
        if (row > grid.length-4) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row+1][col] == 'M' &&
           grid[row+2][col] == 'A' && grid[row+3][col] == 'S';
    }

    private boolean isWestXMAS(char[][] grid, int row, int col) {
        if (col < 3) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row][col-1] == 'M' &&
           grid[row][col-2] == 'A' && grid[row][col-3] == 'S';
    }

    private boolean isEastXMAS(char[][] grid, int row, int col) {
        if (col > grid[0].length - 4) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row][col+1] == 'M' &&
        grid[row][col+2] == 'A' && grid[row][col+3] == 'S';
    }

    private boolean isNorthEastXMAS(char[][] grid, int row, int col) {
        if (col > grid[0].length - 4 || row < 3) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row-1][col+1] == 'M' &&
        grid[row-2][col+2] == 'A' && grid[row-3][col+3] == 'S';
    }

    private boolean isNorthWestXMAS(char[][] grid, int row, int col) {
        if (col < 3 || row < 3) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row-1][col-1] == 'M' &&
        grid[row-2][col-2] == 'A' && grid[row-3][col-3] == 'S';
    }

    private boolean isSouthWestXMAS(char[][] grid, int row, int col) {
        if (col < 3 || row > grid.length-4) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row+1][col-1] == 'M' &&
        grid[row+2][col-2] == 'A' && grid[row+3][col-3] == 'S';
    }

    private boolean isSouthEastXMAS(char[][] grid, int row, int col) {
        if (col > grid[0].length - 4 || row > grid.length-4) {
            return false;
        }
        return grid[row][col] == 'X' && grid[row+1][col+1] == 'M' &&
        grid[row+2][col+2] == 'A' && grid[row+3][col+3] == 'S';
    }
}
