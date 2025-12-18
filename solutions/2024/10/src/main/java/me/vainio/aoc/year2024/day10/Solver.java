package me.vainio.aoc.year2024.day10;

import java.util.HashSet;
import java.util.stream.IntStream;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 10;

  public record Node(int x, int y) {}

  private final int[][] grid;

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
    this.grid =
        input
            .lines()
            .map(line -> line.chars().map(Character::getNumericValue).toArray())
            .toArray(int[][]::new);
  }

  public String solvePart1() {
    final int numRows = grid.length;
    final int numCols = grid[0].length;
    int total =
        IntStream.range(0, numRows)
            .map(
                row ->
                    IntStream.range(0, numCols)
                        .reduce(
                            0,
                            (sum, col) ->
                                sum + countTrailheadScore(grid, row, col, 0, new HashSet<>())))
            .sum();
    return Integer.toString(total);
  }

  public String solvePart2() {
    final int numRows = grid.length;
    final int numCols = grid[0].length;
    int total =
        IntStream.range(0, numRows)
            .map(
                row ->
                    IntStream.range(0, numCols)
                        .reduce(0, (sum, col) -> sum + countTrailheadRating(grid, row, col, 0)))
            .sum();
    return Integer.toString(total);
  }

  public static int countTrailheadScore(
      int[][] grid, int row, int col, int currentHeight, HashSet<Node> visited) {
    if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
      return 0;
    }
    if (grid[row][col] != currentHeight) {
      return 0;
    }
    final int maxHeight = 9;
    if (grid[row][col] == maxHeight && !visited.contains(new Node(row, col))) {
      visited.add(new Node(row, col));
      return 1;
    }
    return countTrailheadScore(grid, row, col - 1, currentHeight + 1, visited)
        + countTrailheadScore(grid, row, col + 1, currentHeight + 1, visited)
        + countTrailheadScore(grid, row - 1, col, currentHeight + 1, visited)
        + countTrailheadScore(grid, row + 1, col, currentHeight + 1, visited);
  }

  public static int countTrailheadRating(int[][] grid, int row, int col, int currentHeight) {
    if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
      return 0;
    }
    if (grid[row][col] != currentHeight) {
      return 0;
    }
    final int maxHeight = 9;
    if (grid[row][col] == maxHeight) {
      return 1;
    }
    return countTrailheadRating(grid, row, col - 1, currentHeight + 1)
        + countTrailheadRating(grid, row, col + 1, currentHeight + 1)
        + countTrailheadRating(grid, row - 1, col, currentHeight + 1)
        + countTrailheadRating(grid, row + 1, col, currentHeight + 1);
  }
}
