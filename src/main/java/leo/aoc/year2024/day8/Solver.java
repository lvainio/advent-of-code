package leo.aoc.year2024.day8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private static final char EMPTY_SPACE = '.';

  private record Node(int row, int col) {
    public boolean isInsideGrid(int numRows, int numCols) {
      return !(row < 0 || row >= numRows || col < 0 || col >= numCols);
    }
  }

  private final int numRows;
  private final int numCols;
  private final HashMap<Character, List<Node>> antennas;

  public Solver(String input) {
    super(input);

    char[][] grid = input.lines().map(String::toCharArray).toArray(char[][]::new);
    this.numRows = grid.length;
    this.numCols = grid[0].length;

    HashMap<Character, List<Node>> antennas = new HashMap<>();
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (grid[row][col] != EMPTY_SPACE) {
          antennas.computeIfAbsent(grid[row][col], _ -> new ArrayList<>()).add(new Node(row, col));
        }
      }
    }
    this.antennas = antennas;
  }

  @Override
  public String solvePart1() {
    HashSet<Node> antiNodesPart = new HashSet<>();
    for (List<Node> nodes : this.antennas.values()) {
      for (int i = 0; i < nodes.size(); i++) {
        for (int j = i + 1; j < nodes.size(); j++) {
          Node antenna1 = nodes.get(i);
          Node antenna2 = nodes.get(j);
          Node antiNode1 =
              new Node(
                  antenna1.row() + (antenna1.row() - antenna2.row()),
                  antenna1.col() + (antenna1.col() - antenna2.col()));
          Node antiNode2 =
              new Node(
                  antenna2.row() + (antenna2.row() - antenna1.row()),
                  antenna2.col() + (antenna2.col() - antenna1.col()));
          if (antiNode1.isInsideGrid(this.numRows, this.numCols)) {
            antiNodesPart.add(antiNode1);
          }
          if (antiNode2.isInsideGrid(this.numRows, this.numCols)) {
            antiNodesPart.add(antiNode2);
          }
        }
      }
    }
    return Integer.toString(antiNodesPart.size());
  }

  @Override
  public String solvePart2() {
    HashSet<Node> antiNodesPart = new HashSet<>();
    for (List<Node> nodes : this.antennas.values()) {
      for (int i = 0; i < nodes.size(); i++) {
        for (int j = i + 1; j < nodes.size(); j++) {
          Node antenna1 = nodes.get(i);
          Node antenna2 = nodes.get(j);
          double slope =
              (double) (antenna1.row() - antenna2.row()) / (antenna1.col() - antenna2.col());
          double intersect = (double) antenna1.row() - (slope * antenna1.col());
          for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
              double tolerance = 1e-9;
              if (Math.abs(row - (slope * col + intersect)) < tolerance) {
                antiNodesPart.add(new Node(row, col));
              }
            }
          }
        }
      }
    }
    return Integer.toString(antiNodesPart.size());
  }
}
