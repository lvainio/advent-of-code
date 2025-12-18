package me.vainio.aoc.year2024.day12;

import java.util.HashSet;
import java.util.stream.IntStream;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 12;

  public record Node(int row, int col) {}

  public record Counts(int area, int perimeter) {
    public Counts add(Counts other) {
      return new Counts(this.area + other.area, this.perimeter + other.perimeter);
    }
  }

  private final char[][] grid;

  public static void main(final String[] args) {
    final AocCache cache = new AocCache();

    final String input = cache.getInput(YEAR, DAY);
    final Solver solver = new Solver(input);

    final String part1 = solver.solvePart1();
    final String part2 = solver.solvePart2();

    System.out.println(part1);
    System.out.println(part2);

    cache.saveAnswer(YEAR, DAY, 1, part1);
    cache.saveAnswer(YEAR, DAY, 2, part2);
  }

  public Solver(final String input) {
    this.grid = input.lines().map(String::toCharArray).toArray(char[][]::new);
  }

  public String solvePart1() {
    final int numRows = grid.length;
    final int numCols = grid[0].length;
    HashSet<Node> visited = new HashSet<>();
    int total =
        IntStream.range(0, numRows)
            .map(
                row ->
                    IntStream.range(0, numCols)
                        .reduce(
                            0,
                            (sum, col) -> {
                              Node node = new Node(row, col);
                              if (visited.contains(node)) {
                                return sum;
                              }
                              Counts result =
                                  searchRegionPart1(grid, node, grid[row][col], visited);
                              return sum + (result.area() * result.perimeter());
                            }))
            .sum();
    return Integer.toString(total);
  }

  public String solvePart2() {
    final int numRows = grid.length;
    final int numCols = grid[0].length;
    HashSet<Node> visited = new HashSet<>();
    int total =
        IntStream.range(0, numRows)
            .map(
                row ->
                    IntStream.range(0, numCols)
                        .reduce(
                            0,
                            (sum, col) -> {
                              Node node = new Node(row, col);
                              if (visited.contains(node)) {
                                return sum;
                              }
                              Counts result =
                                  searchRegionPart2(grid, node, grid[row][col], visited);
                              return sum + (result.area() * result.perimeter());
                            }))
            .sum();
    return Integer.toString(total);
  }

  public static Counts searchRegionPart1(
      char[][] grid, Node node, char plantType, HashSet<Node> visited) {
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
        .add(searchRegionPart1(grid, new Node(row - 1, col), plantType, visited))
        .add(searchRegionPart1(grid, new Node(row + 1, col), plantType, visited))
        .add(searchRegionPart1(grid, new Node(row, col - 1), plantType, visited))
        .add(searchRegionPart1(grid, new Node(row, col + 1), plantType, visited));
  }

  public static Counts searchRegionPart2(
      char[][] grid, Node node, char plantType, HashSet<Node> visited) {
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
        .add(searchRegionPart2(grid, new Node(row - 1, col), plantType, visited))
        .add(searchRegionPart2(grid, new Node(row + 1, col), plantType, visited))
        .add(searchRegionPart2(grid, new Node(row, col - 1), plantType, visited))
        .add(searchRegionPart2(grid, new Node(row, col + 1), plantType, visited));
  }

  public static int countWallsOfNode(char[][] grid, Node node, char plantType) {
    int row = node.row();
    int col = node.col();

    boolean isNorthEastCornerConvex =
        !isNodeType(grid, row - 1, col, plantType) && !isNodeType(grid, row, col + 1, plantType);
    boolean isNorthWestCornerConvex =
        !isNodeType(grid, row - 1, col, plantType) && !isNodeType(grid, row, col - 1, plantType);
    boolean isSouthEastCornerConvex =
        !isNodeType(grid, row + 1, col, plantType) && !isNodeType(grid, row, col + 1, plantType);
    boolean isSouthWestCornerConvex =
        !isNodeType(grid, row + 1, col, plantType) && !isNodeType(grid, row, col - 1, plantType);

    boolean isNorthEastCornerConcave =
        isNodeType(grid, row - 1, col, plantType)
            && isNodeType(grid, row, col + 1, plantType)
            && !isNodeType(grid, row - 1, col + 1, plantType);
    boolean isNorthWestCornerConcave =
        isNodeType(grid, row - 1, col, plantType)
            && isNodeType(grid, row, col - 1, plantType)
            && !isNodeType(grid, row - 1, col - 1, plantType);
    boolean isSouthEastCornerConcave =
        isNodeType(grid, row + 1, col, plantType)
            && isNodeType(grid, row, col + 1, plantType)
            && !isNodeType(grid, row + 1, col + 1, plantType);
    boolean isSouthWestCornerConcave =
        isNodeType(grid, row + 1, col, plantType)
            && isNodeType(grid, row, col - 1, plantType)
            && !isNodeType(grid, row + 1, col - 1, plantType);

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
