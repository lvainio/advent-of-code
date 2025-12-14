package me.vainio.aoc.year2025.day08;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2025;
  private static final int DAY = 8;

  private static final int NUM_CONNECTIONS = 1000;

  private final Map<Coordinate, Set<Coordinate>> graph;

  public static void main(final String[] args) {
    final AocCache cache = new AocCache();

    final String input = cache.getInput(YEAR, DAY);
    final Solver solver = new Solver(input);

    final String part1 = solver.solvePart1(NUM_CONNECTIONS);
    final String part2 = solver.solvePart2();

    System.out.println(part1);
    System.out.println(part2);

    cache.saveAnswer(YEAR, DAY, 1, part1);
    cache.saveAnswer(YEAR, DAY, 2, part2);
  }

  public Solver(final String input) {
    this.graph =
        input
            .lines()
            .map(line -> line.split(","))
            .map(
                parts ->
                    new Coordinate(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2])))
            .collect(Collectors.toMap(c -> c, c -> new HashSet<>()));
  }

  public String solvePart1(final int numConnections) {
    Map<Coordinate, Set<Coordinate>> graph = getCopyOfGraph();

    PriorityQueue<EdgeWithDistance> nShortestConnections =
        new PriorityQueue<>(numConnections, EdgeWithDistance.BY_DISTANCE.reversed());
    List<Coordinate> coordinates = graph.keySet().stream().toList();
    for (int i = 0; i < coordinates.size(); i++) {
      for (int j = i + 1; j < coordinates.size(); j++) {
        var c1 = coordinates.get(i);
        var c2 = coordinates.get(j);
        var edge = new EdgeWithDistance(c1, c2);
        nShortestConnections.add(edge);
        if (nShortestConnections.size() > numConnections) {
          nShortestConnections.poll();
        }
      }
    }

    for (EdgeWithDistance edge : nShortestConnections) {
      graph.get(edge.c1()).add(edge.c2());
      graph.get(edge.c2()).add(edge.c1());
    }

    List<Integer> componentSizes = new ArrayList<>();
    Set<Coordinate> visited = new HashSet<>();
    for (Coordinate start : graph.keySet()) {
      if (visited.contains(start)) {
        continue;
      }
      int size = 0;
      Deque<Coordinate> stack = new LinkedList<>();
      stack.push(start);
      visited.add(start);
      while (!stack.isEmpty()) {
        Coordinate current = stack.pop();
        size++;
        for (Coordinate neighbor : graph.get(current)) {
          if (!visited.contains(neighbor)) {
            visited.add(neighbor);
            stack.push(neighbor);
          }
        }
      }
      componentSizes.add(size);
    }

    componentSizes.sort(Comparator.reverseOrder());
    return String.valueOf(componentSizes.get(0) * componentSizes.get(1) * componentSizes.get(2));
  }

  public String solvePart2() {
    Map<Coordinate, Set<Coordinate>> graph = getCopyOfGraph();

    PriorityQueue<EdgeWithDistance> edges = new PriorityQueue<>(EdgeWithDistance.BY_DISTANCE);
    List<Coordinate> coordinates = graph.keySet().stream().toList();
    for (int i = 0; i < coordinates.size(); i++) {
      for (int j = i + 1; j < coordinates.size(); j++) {
        var c1 = coordinates.get(i);
        var c2 = coordinates.get(j);
        var edge = new EdgeWithDistance(c1, c2);
        edges.add(edge);
      }
    }

    while (!edges.isEmpty()) {
      EdgeWithDistance edge = edges.poll();
      graph.get(edge.c1()).add(edge.c2());
      graph.get(edge.c2()).add(edge.c1());
      if (isFullyConnected(graph)) {
        return String.valueOf(edge.c1().x() * edge.c2().x());
      }
    }

    throw new IllegalStateException("Graph could not be fully connected");
  }

  private boolean isFullyConnected(Map<Coordinate, Set<Coordinate>> graph) {
    if (graph.isEmpty()) {
      return true;
    }
    Set<Coordinate> visited = new HashSet<>();
    Deque<Coordinate> stack = new LinkedList<>();
    Coordinate start = graph.keySet().iterator().next();
    stack.push(start);
    visited.add(start);
    while (!stack.isEmpty()) {
      Coordinate current = stack.pop();
      for (Coordinate neighbor : graph.get(current)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          stack.push(neighbor);
        }
      }
    }
    return visited.size() == graph.size();
  }

  private Map<Coordinate, Set<Coordinate>> getCopyOfGraph() {
    return graph.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> new HashSet<>(e.getValue())));
  }
}
