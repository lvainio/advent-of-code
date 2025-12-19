package me.vainio.aoc.year2024.day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import me.vainio.aoc.cache.AocCache;
import me.vainio.aoc.util.Direction;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 20;

  public enum Cell {
    WALL,
    EMPTY,
    START,
    END;

    public static Cell charToCell(char c) {
      return switch (c) {
        case '#' -> WALL;
        case '.' -> EMPTY;
        case 'S' -> START;
        case 'E' -> END;
        default -> throw new IllegalArgumentException("Unknown character: " + c);
      };
    }
  }

  private record Node(int row, int col) {}

  private record Cheat(Node startNode, Node endNode) {
    public int getManhattanDistance() {
      return Math.abs(startNode.row() - endNode.row()) + Math.abs(startNode.col() - endNode.col());
    }
  }

  private Cell[][] grid = null;
  private Node startPosition = null;
  private Node endPosition = null;

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
            .map(line -> line.chars().mapToObj(c -> Cell.charToCell((char) c)).toArray(Cell[]::new))
            .toArray(Cell[][]::new);
    for (int row = 0; row < this.grid.length; row++) {
      for (int col = 0; col < this.grid[0].length; col++) {
        if (this.grid[row][col] == Cell.START) {
          this.startPosition = new Node(row, col);
          this.grid[row][col] = Cell.EMPTY;
        }
        if (this.grid[row][col] == Cell.END) {
          this.endPosition = new Node(row, col);
          this.grid[row][col] = Cell.EMPTY;
        }
      }
    }
  }

  public String solvePart1() {
    GridGraph gridGraph = new GridGraph(grid, this.startPosition, this.endPosition);
    return Integer.toString(gridGraph.countSavings(2));
  }

  public String solvePart2() {
    GridGraph gridGraph = new GridGraph(grid, this.startPosition, this.endPosition);
    return Integer.toString(gridGraph.countSavings(20));
  }

  public static class GridGraph {
    private final Cell[][] grid;
    private final Node startNode;
    private final Node endNode;

    public GridGraph(Cell[][] grid, Node startNode, Node endNode) {
      this.grid = grid;
      this.startNode = startNode;
      this.endNode = endNode;
    }

    private int getRaceTrackLength() {
      int count = 0;
      for (int row = 0; row < this.grid.length; row++) {
        for (int col = 0; col < this.grid[0].length; col++) {
          if (this.grid[row][col] == Cell.EMPTY) {
            count++;
          }
        }
      }
      return count - 1;
    }

    private HashMap<Node, Integer> getDistanceToEndMap() {
      HashMap<Node, Integer> distanceToEndMap = new HashMap<>();
      Queue<Node> queue = new LinkedList<>();
      HashSet<Node> visited = new HashSet<>();

      int distanceToEnd = 0;
      distanceToEndMap.put(endNode, distanceToEnd);
      queue.offer(this.endNode);
      visited.add(this.endNode);
      while (!queue.isEmpty()) {
        Node currentNode = queue.poll();
        distanceToEnd++;
        if (currentNode.equals(this.startNode)) {
          return distanceToEndMap;
        }
        for (Direction dir : Direction.getCardinalDirections()) {
          int newRow = currentNode.row() + dir.getDr();
          int newCol = currentNode.col() + dir.getDc();
          Node newNode = new Node(newRow, newCol);
          if (!visited.contains(newNode) && this.grid[newRow][newCol] == Cell.EMPTY) {
            distanceToEndMap.put(newNode, distanceToEnd);
            queue.offer(newNode);
            visited.add(newNode);
          }
        }
      }
      throw new IllegalStateException("No path found.");
    }

    private List<Cheat> getCheatsFromNode(
        HashMap<Node, Integer> distanceToEndMap, Node node, int radius) {
      List<Cheat> cheats = new ArrayList<>();

      int centerRow = node.row();
      int centerCol = node.col();

      int startRow = Math.max(centerRow - radius, 0);
      int endRow = Math.min(centerRow + radius, this.grid.length - 1);

      int startCol = Math.max(centerCol - radius, 0);
      int endCol = Math.min(centerCol + radius, this.grid[0].length - 1);

      for (int row = startRow; row <= endRow; row++) {
        for (int col = startCol; col <= endCol; col++) {
          Node centerNode = new Node(centerRow, centerCol);
          Node newNode = new Node(row, col);

          if (Math.abs(row - centerRow) + Math.abs(col - centerCol) <= radius
              && this.grid[row][col] == Cell.EMPTY
              && distanceToEndMap.get(newNode) < distanceToEndMap.get(centerNode)) {
            cheats.add(new Cheat(centerNode, newNode));
          }
        }
      }
      return cheats;
    }

    public int countSavings(int radius) {
      HashMap<Node, Integer> distanceToEndMap = getDistanceToEndMap();

      int trackLength = getRaceTrackLength();
      int count = 0;

      for (Node node : distanceToEndMap.keySet()) {
        List<Cheat> cheats = getCheatsFromNode(distanceToEndMap, node, radius);
        for (Cheat cheat : cheats) {
          Node start = cheat.startNode();
          Node end = cheat.endNode();

          int distanceToStart = trackLength - distanceToEndMap.get(start);
          int cheatDistance = cheat.getManhattanDistance();
          int distanceToEnd = distanceToEndMap.get(end);

          int distance = distanceToEnd + distanceToStart + cheatDistance;
          if (trackLength - distance >= 100) {
            count++;
          }
        }
      }
      return count;
    }
  }
}
