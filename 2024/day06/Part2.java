
public class Part2 {
    private final char OBSTACLE = '#';
    private final char FREE_SPACE = '.';
    private final char STARTING_POSITION = '^';

    private char[][] grid;

    private Direction currentDirection;

    private int currentRow;
    private int currentCol;

    public Part2(char[][] grid) {
        // Copy grid
        this.grid = new char[grid.length][];
        for (int row = 0; row < grid.length; row++) {
            this.grid[row] = grid[row].clone();
        }
        // Locate starting position.
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == STARTING_POSITION) {
                    this.currentRow = row;
                    this.currentCol = col;
                }
            }
        }
        // Set starting position character to free space.
        this.grid[this.currentRow][this.currentCol] = FREE_SPACE;
        // Set starting direction to UP.
        this.currentDirection = Direction.UP;
    }

    public void solve() {
        int sum = 0;

        System.out.println("Part2: " + sum);
    }
}
