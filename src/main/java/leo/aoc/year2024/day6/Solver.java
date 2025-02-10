package leo.aoc.year2024.day6;

import java.util.HashSet;

import leo.aoc.AbstractSolver;
import leo.aoc.util.Direction;

public class Solver extends AbstractSolver {

    private static final char STARTING_POINT = '^'; 
    private static final char EMPTY_SPACE = '.';
    private static final char OBSTACLE = '#';
    
    private record Point(int row, int col) {
        public Point copy() {
            return new Point(this.row(), this.col());
        }
    }

    private record PointAndDirection(Point p, Direction dir) {}

    private char[][] grid;

    private final Point startingPoint;

    public Solver(String input) {
        super(input);

        this.grid = input.lines()
                .map(String::toCharArray)  
                .toArray(char[][]::new);

        Point startingPoint = null;
        for (int row = 0; row < this.grid.length; row++) {
            for (int col = 0; col < this.grid[0].length; col++) {
                if (this.grid[row][col] == STARTING_POINT) {
                    startingPoint = new Point(row, col);
                    this.grid[row][col] = EMPTY_SPACE;
                }
            }
        }
        this.startingPoint = startingPoint;  
    }
    
    @Override
    public String solvePart1() {
        Direction currentDirection = Direction.NORTH;
        Point currentPosition = this.startingPoint.copy();
        HashSet<Point> visited = new HashSet<>();
        while(!isOutsideGrid(currentPosition)) {
            visited.add(currentPosition);
            while(nextIsObstacle(currentPosition, currentDirection)) {
                currentDirection = currentDirection.turnRight(90);
            }
            int newRow = currentPosition.row() + currentDirection.getDr();
            int newCol = currentPosition.col() + currentDirection.getDc();
            currentPosition = new Point(newRow, newCol);
        }
        return Integer.toString(visited.size());
    }

    @Override
    public String solvePart2() {
        int count = 0;
        for (int row = 0; row < this.grid.length; row++) {
            for (int col = 0; col < this.grid[0].length; col++) {
                if (this.grid[row][col] == EMPTY_SPACE 
                    && !this.startingPoint.equals(new Point(row, col))) {
                        Direction currentDirection = Direction.NORTH;
                        Point currentPosition = this.startingPoint.copy();
                        HashSet<PointAndDirection> visited = new HashSet<>();
                        this.grid[row][col] = OBSTACLE;
                        while(!isOutsideGrid(currentPosition)) {
                            PointAndDirection pad = 
                                new PointAndDirection(currentPosition, currentDirection);
                            if (visited.contains(pad)) {
                                count++;
                                break;
                            }
                            visited.add(pad);
                            while(nextIsObstacle(currentPosition, currentDirection)) {
                                currentDirection = currentDirection.turnRight(90);
                            }
                            int newRow = currentPosition.row() + currentDirection.getDr();
                            int newCol = currentPosition.col() + currentDirection.getDc();
                            currentPosition = new Point(newRow, newCol);
                        }
                        this.grid[row][col] = EMPTY_SPACE;
                }
            }
        }
        return Integer.toString(count);
    }

    private boolean isOutsideGrid(Point p) {
        return p.row() < 0 || p.row() >= this.grid.length 
            || p.col() < 0 || p.col() >= this.grid[0].length;
    }

    private boolean nextIsObstacle(Point p, Direction dir) {
        int newRow = p.row() + dir.getDr();
        int newCol = p.col() + dir.getDc();
        return !isOutsideGrid(new Point(newRow, newCol)) && this.grid[newRow][newCol] == OBSTACLE;
    }
}
