import java.util.HashMap;
import java.util.HashSet;

public class Part1 {
    private final char OBSTACLE = '#';
    private final char FREE_SPACE = '.';
    private final char STARTING_POSITION = '^';

    private final char[][] grid;

    private Direction currentDirection;

    private int currentRow;
    private int currentCol;

    public Part1(char[][] grid) {
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
        HashMap<Integer, HashSet<Integer>> visited = new HashMap<>();
        while (!isOutsideGrid()) {
            visited
                .computeIfAbsent(this.currentRow, k -> new HashSet<>())  
                .add(this.currentCol);
            step();
        }
        int count = 0;
        for (HashSet<Integer> set : visited.values()) {
            count += set.size();  
        }
        System.out.println("Part1: " + count);
    }

    private boolean isOutsideGrid() {
        return this.currentRow < 0 || this.currentRow >= this.grid.length ||
                this.currentCol < 0 || this.currentCol >= this.grid[0].length;
    }

    private void step() {
        if (nextIsObstacle()) {
            this.currentDirection = this.currentDirection.turnRight();
        } 
        this.currentRow = this.currentRow + this.currentDirection.getDr();
        this.currentCol = this.currentCol + this.currentDirection.getDc();
    }

    private boolean nextIsObstacle() {
        int newRow = this.currentRow + this.currentDirection.getDr();
        int newCol = this.currentCol + this.currentDirection.getDc();
        if (newRow < 0 || newRow >= this.grid.length ||
            newCol < 0 || newCol >= this.grid[0].length) {
                return false;
        }
        return grid[newRow][newCol] == OBSTACLE;
    }
}
