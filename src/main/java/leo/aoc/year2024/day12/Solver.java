package leo.aoc.year2024.day12;

import java.util.HashSet;
import java.util.stream.IntStream;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private record Node(int row, int col) {}

    private record Counts(int area, int perimeter) {
        public Counts add(Counts other) {
            return new Counts(this.area + other.area, this.perimeter + other.perimeter);
        }
    }
    
    private char[][] grid;

    public Solver(String input) {
        super(input);

        this.grid = input.lines()
                .map(line -> line.toCharArray())
                .toArray(char[][]::new);
    }

    @Override
    public String solvePart1() {
        final int numRows = grid.length;
        final int numCols = grid[0].length;
        HashSet<Node> visited = new HashSet<>();
        int total = IntStream.range(0, numRows)
            .map(row -> IntStream.range(0, numCols)
                .reduce(0, (sum, col) -> {
                    Node node = new Node(row, col);
                    if (visited.contains(node)) {
                        return sum;
                    } 
                    Counts result = searchRegionPart1(grid, node, grid[row][col], visited);
                    return sum + (result.area() * result.perimeter());
                }))
            .sum();
        return Integer.toString(total);
    }

    @Override
    public String solvePart2() {
        final int numRows = grid.length;
        final int numCols = grid[0].length;
        HashSet<Node> visited = new HashSet<>();
        int total = IntStream.range(0, numRows)
            .map(row -> IntStream.range(0, numCols)
                .reduce(0, (sum, col) -> {
                    Node node = new Node(row, col);
                    if (visited.contains(node)) {
                        return sum;
                    } 
                    Counts result = searchRegionPart2(grid, node, grid[row][col], visited);
                    return sum + (result.area() * result.perimeter());
                }))
            .sum();
        return Integer.toString(total);
    }

    public static Counts searchRegionPart1(
        char[][] grid, 
        Node node, 
        char plantType, 
        HashSet<Node> visited
    ) {
        int row = node.row();
        int col = node.col();
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            return new Counts(0, 1);
        }
        if (grid[row][col] != plantType) {
            return new Counts(0, 1);
        }
        if (visited.contains(node)) {
            return new Counts(0, 0);
        }
        visited.add(node);
        return new Counts(1, 0)
            .add(searchRegionPart1(grid, new Node(row-1, col), plantType, visited))
            .add(searchRegionPart1(grid, new Node(row+1, col), plantType, visited))
            .add(searchRegionPart1(grid, new Node(row, col-1), plantType, visited))
            .add(searchRegionPart1(grid, new Node(row, col+1), plantType, visited));
    }

    public static Counts searchRegionPart2(
        char[][] grid, 
        Node node, 
        char plantType, 
        HashSet<Node> visited
    ) {
        int row = node.row();
        int col = node.col();
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            return new Counts(0, 0);
        }
        if (grid[row][col] != plantType) {
            return new Counts(0, 0);
        }
        if (visited.contains(node)) {
            return new Counts(0, 0);
        }
        int numWalls = countWallsOfNode(grid, node, plantType); 
        visited.add(node);
        return new Counts(1, numWalls)
            .add(searchRegionPart2(grid, new Node(row-1, col), plantType, visited))
            .add(searchRegionPart2(grid, new Node(row+1, col), plantType, visited))
            .add(searchRegionPart2(grid, new Node(row, col-1), plantType, visited))
            .add(searchRegionPart2(grid, new Node(row, col+1), plantType, visited));
    }

    public static int countWallsOfNode(char[][] grid, Node node, char plantType) {
        int row = node.row();
        int col = node.col();

        boolean isNorthEastCornerConvex = !isNodeType(grid, row-1, col, plantType) 
            && !isNodeType(grid, row, col+1, plantType);
        boolean isNorthWestCornerConvex = !isNodeType(grid, row-1, col, plantType) 
            && !isNodeType(grid, row, col-1, plantType);
        boolean isSouthEastCornerConvex = !isNodeType(grid, row+1, col, plantType) 
            && !isNodeType(grid, row, col+1, plantType);
        boolean isSouthWestCornerConvex = !isNodeType(grid, row+1, col, plantType) 
            && !isNodeType(grid, row, col-1, plantType);

        boolean isNorthEastCornerConcave = isNodeType(grid, row-1, col, plantType) 
            && isNodeType(grid, row, col+1, plantType) 
            && !isNodeType(grid, row-1, col+1, plantType);
        boolean isNorthWestCornerConcave = isNodeType(grid, row-1, col, plantType) 
            && isNodeType(grid, row, col-1, plantType) 
            && !isNodeType(grid, row-1, col-1, plantType);
        boolean isSouthEastCornerConcave = isNodeType(grid, row+1, col, plantType) 
            && isNodeType(grid, row, col+1, plantType) 
            && !isNodeType(grid, row+1, col+1, plantType);
        boolean isSouthWestCornerConcave = isNodeType(grid, row+1, col, plantType) 
            && isNodeType(grid, row, col-1, plantType)
            && !isNodeType(grid, row+1, col-1, plantType);

        int count = 0;
        count += isNorthEastCornerConvex ? 1 : 0;
        count += isNorthWestCornerConvex ? 1 : 0;
        count += isSouthEastCornerConvex ? 1 : 0;
        count += isSouthWestCornerConvex ? 1 : 0;
        count += isNorthEastCornerConcave ? 1 : 0;
        count += isNorthWestCornerConcave ? 1 : 0;
        count += isSouthEastCornerConcave ? 1 : 0;
        count += isSouthWestCornerConcave ? 1 : 0;
        return count;
    }

    public static boolean isOutside(char[][] grid, int row, int col) {
        return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
    }

    public static boolean isNodeType(char[][] grid, int row, int col, char plantType) {
        return !isOutside(grid, row, col) && grid[row][col] == plantType;
    }
}
