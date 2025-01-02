public class Part2 {
    public void solve(char[][] grid) {
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (isXMAS(grid, row, col)) {
                    count++;
                }
            }
        }
        System.out.println("Part2: " + count);
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
