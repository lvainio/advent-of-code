package com.example.day18_2024;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GridGraph {
    private static final char SAFE = '.';
    private static final char CORRUPTED = '#';

    private static final int NUM_ROWS = 71;
    private static final int NUM_COLS = 71;

    private char[][] grid;

    private Coordinate startCoordinate;
    private Coordinate endCoordinate;

    private enum Direction {
        NORTH(0, -1),
        SOUTH(0, 1),
        WEST(-1, 0),
        EAST(1, 0);

        private final int dx;
        private final int dy;

        private Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public int getDx() {
            return this.dx;
        }

        public int getDy() {
            return this.dy;
        }
    }

    public GridGraph(Coordinate startCoordinate, Coordinate endCoordinate) {
        this.grid = new char[NUM_ROWS][NUM_COLS];
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                this.grid[row][col] = SAFE;
            }
        }
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
    }

    public void corrupt(int n, List<Coordinate> coordinates) {
        for (int i = 0; i < n; i++) {
            Coordinate c = coordinates.get(i);
            this.grid[c.y()][c.x()] = CORRUPTED;
        }
    }

    public int bfs() {
        Queue<Coordinate> queue = new LinkedList<>();
        HashSet<Coordinate> visited = new HashSet<>();

        queue.offer(this.startCoordinate);
        visited.add(this.startCoordinate);

        int steps = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Coordinate current = queue.poll();
                if (current.equals(this.endCoordinate)) {
                    return steps;
                }
                for (Direction direction : Direction.values()) {
                    int newX = current.x() + direction.getDx();
                    int newY = current.y() + direction.getDy();
                    Coordinate newCoordinate = new Coordinate(newX, newY);
                    if (newX >= 0 
                        && newX < NUM_COLS 
                        && newY >= 0 
                        && newY < NUM_ROWS 
                        && this.grid[newY][newX] != CORRUPTED 
                        && !visited.contains(newCoordinate)) {
                            queue.offer(newCoordinate);
                            visited.add(newCoordinate);
                    }
                }
            } 
            steps++;
        }
        throw new IllegalStateException("No path found from start to end");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                sb.append(grid[row][col]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
