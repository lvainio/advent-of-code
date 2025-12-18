package me.vainio.aoc.year2024.day16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import me.vainio.aoc.cache.AocCache;
import me.vainio.aoc.util.Direction;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 16;

  private static final char START = 'S';
  private static final char END = 'E';
  private static final char EMPTY = '.';

  private record Node(int row, int col) {}

  private record NodeAndDirection(Node node, Direction direction) {}

  private record PriorityQueueNode(Node node, Direction direction, int score, HashSet<Node> path)
      implements Comparable<PriorityQueueNode> {
    @Override
    public int compareTo(PriorityQueueNode other) {
      return Integer.compare(this.score, other.score);
    }
  }

  private final char[][] grid;
  private final Node start;
  private final Node end;

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
    char[][] grid = input.lines().map(String::trim).map(String::toCharArray).toArray(char[][]::new);

    Node start = null;
    Node end = null;
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (grid[row][col] == START) {
          start = new Node(row, col);
          grid[row][col] = EMPTY;
        }
        if (grid[row][col] == END) {
          end = new Node(row, col);
          grid[row][col] = EMPTY;
        }
      }
    }
    this.grid = grid;
    this.start = start;
    this.end = end;
  }

  public String solvePart1() {
    PriorityQueue<PriorityQueueNode> pq = new PriorityQueue<>();
    HashMap<NodeAndDirection, Integer> bestScores = new HashMap<>();

    PriorityQueueNode pqStart = new PriorityQueueNode(this.start, Direction.EAST, 0, null);
    pq.add(pqStart);
    bestScores.put(new NodeAndDirection(this.start, Direction.EAST), 0);

    while (!pq.isEmpty()) {
      PriorityQueueNode currentPriorityQueueNode = pq.poll();
      Node currentNode = currentPriorityQueueNode.node();
      Direction currentDirection = currentPriorityQueueNode.direction();
      int currentScore = currentPriorityQueueNode.score();

      if (currentNode.equals(this.end)) {
        return Integer.toString(currentScore);
      }

      List<Direction> directions =
          List.of(currentDirection, currentDirection.turnLeft(90), currentDirection.turnRight(90));

      for (Direction direction : directions) {
        int newRow = currentNode.row();
        int newCol = currentNode.col();
        if (direction == currentDirection) {
          newRow += direction.getDr();
          newCol += direction.getDc();
        }
        int newScore = currentScore + (direction == currentDirection ? 1 : 1000);
        Node newNode = new Node(newRow, newCol);
        NodeAndDirection newNodeAndDirection = new NodeAndDirection(newNode, direction);
        PriorityQueueNode newPQNode = new PriorityQueueNode(newNode, direction, newScore, null);
        if (this.grid[newRow][newCol] == EMPTY
            && newScore <= bestScores.getOrDefault(newNodeAndDirection, Integer.MAX_VALUE)) {
          pq.add(newPQNode);
          bestScores.put(newNodeAndDirection, newScore);
        }
      }
    }
    throw new IllegalStateException("No path found part1!");
  }

  public String solvePart2() {
    int bestScoreToEnd = Integer.MAX_VALUE;
    HashSet<Node> bestPathNodes = new HashSet<>();

    PriorityQueue<PriorityQueueNode> pq = new PriorityQueue<>();
    HashMap<NodeAndDirection, Integer> bestScores = new HashMap<>();

    HashSet<Node> path = new HashSet<>();
    path.add(this.start);
    PriorityQueueNode pqStart = new PriorityQueueNode(this.start, Direction.EAST, 0, path);
    pq.add(pqStart);
    bestScores.put(new NodeAndDirection(this.start, Direction.EAST), 0);

    while (!pq.isEmpty()) {
      PriorityQueueNode currentPriorityQueueNode = pq.poll();
      Node currentNode = currentPriorityQueueNode.node();
      Direction currentDirection = currentPriorityQueueNode.direction();
      int currentScore = currentPriorityQueueNode.score();
      HashSet<Node> currentPath = currentPriorityQueueNode.path();

      if (currentScore > bestScoreToEnd) {
        return Integer.toString(bestPathNodes.size());
      }

      if (currentNode.equals(this.end)) {
        bestScoreToEnd = currentScore;
        bestPathNodes.addAll(currentPath);
        continue;
      }

      List<Direction> directions =
          List.of(currentDirection, currentDirection.turnLeft(90), currentDirection.turnRight(90));

      for (Direction direction : directions) {
        int newRow = currentNode.row();
        int newCol = currentNode.col();
        if (direction == currentDirection) {
          newRow += direction.getDr();
          newCol += direction.getDc();
        }
        int newScore = currentScore + (direction == currentDirection ? 1 : 1000);
        Node newNode = new Node(newRow, newCol);
        NodeAndDirection newNodeAndDirection = new NodeAndDirection(newNode, direction);
        HashSet<Node> newPath = new HashSet<>(currentPath);
        newPath.add(newNode);
        PriorityQueueNode newPQNode = new PriorityQueueNode(newNode, direction, newScore, newPath);
        if (this.grid[newRow][newCol] == EMPTY
            && newScore <= bestScores.getOrDefault(newNodeAndDirection, Integer.MAX_VALUE)) {
          pq.add(newPQNode);
          bestScores.put(newNodeAndDirection, newScore);
        }
      }
    }
    throw new IllegalStateException("No path found part2!");
  }
}
