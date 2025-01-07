import java.util.HashMap;
import java.util.HashSet;

public class Part2 {
    private final char OBSTACLE = '#';
    private final char FREE_SPACE = '.';
    private final char STARTING_POSITION = '^';

    private final char[][] grid;

    private Direction currentDirection;

    private int currentRow;
    private int currentCol;

    private int startingRow;
    private int startingCol;

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
                    this.startingRow = row;
                    this.startingCol = col;
                }
            }
        }
        // Set starting position character to free space.
        this.grid[this.currentRow][this.currentCol] = FREE_SPACE;
        // Set starting direction to UP.
        this.currentDirection = Direction.UP;
    }

    public void solve() {
        int count = 0;
        for (int row = 0; row < this.grid.length; row++) {
            for (int col = 0; col < this.grid[0].length; col++) {
                if (this.grid[row][col] == this.FREE_SPACE && !(row == this.startingRow && col == this.startingCol)) {
                    HashMap<Integer, HashMap<Integer, HashSet<Direction>>> visited = new HashMap<>();
                    this.grid[row][col] = this.OBSTACLE;
                    while (!isOutsideGrid()) {
                        if (visited.get(this.currentRow) != null && 
                            visited.get(this.currentRow).get(this.currentCol) != null &&
                            visited.get(this.currentRow).get(this.currentCol).contains(this.currentDirection)) 
                        {   
                            count++;
                            break;
                        }
                        visited
                            .computeIfAbsent(this.currentRow, k -> new HashMap<>())  
                            .computeIfAbsent(this.currentCol, k -> new HashSet<>())
                            .add(this.currentDirection);
                        step();
                    }
                    this.grid[row][col] = FREE_SPACE;
                    this.currentRow = this.startingRow;
                    this.currentCol = this.startingCol;
                    this.currentDirection = Direction.UP;
                }
            }
        }
        System.out.println("Part2: " + count);
    }

    private boolean isOutsideGrid() {
        return this.currentRow < 0 || this.currentRow >= this.grid.length ||
                this.currentCol < 0 || this.currentCol >= this.grid[0].length;
    }

    private void step() {
        while (nextIsObstacle()) {
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
        return this.grid[newRow][newCol] == OBSTACLE;
    }
}
