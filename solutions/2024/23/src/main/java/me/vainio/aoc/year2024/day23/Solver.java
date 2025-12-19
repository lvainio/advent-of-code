package me.vainio.aoc.year2024.day23;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 23;

  private final HashMap<String, HashSet<String>> network;

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
    HashMap<String, HashSet<String>> network = new HashMap<>();
    input
        .lines()
        .forEach(
            line -> {
              String[] edge = line.split("-");
              String n1 = edge[0].trim();
              String n2 = edge[1].trim();
              network.computeIfAbsent(n1, k -> new HashSet<>()).add(n2);
              network.computeIfAbsent(n2, k -> new HashSet<>()).add(n1);
            });
    this.network = network;
  }

  public String solvePart1() {
    Set<Set<String>> triples = findFullyConnectedTriples(network);
    int count = 0;
    for (Set<String> triple : triples) {
      for (String node : triple) {
        if (node.startsWith("t")) {
          count++;
          break;
        }
      }
    }
    return Integer.toString(count);
  }

  public String solvePart2() {
    Set<String> largestClique = findLargestClique(network, 14);
    List<String> lanParty = new ArrayList<>(largestClique);
    Collections.sort(lanParty);
    return String.join(",", lanParty);
  }

  public static Set<Set<String>> findFullyConnectedTriples(
      HashMap<String, HashSet<String>> network) {
    Set<Set<String>> triples = new HashSet<>();
    for (String node : network.keySet()) {
      for (String neighbor : network.get(node)) {
        HashSet<String> commonNeighbors = new HashSet<>(network.get(node));
        commonNeighbors.retainAll(network.get(neighbor));
        for (String commonNeighbor : commonNeighbors) {
          triples.add(Set.of(node, neighbor, commonNeighbor));
        }
      }
    }
    return triples;
  }

  public static Set<String> findLargestClique(
      HashMap<String, HashSet<String>> network, int maxCliqueSize) {
    Set<String> largestClique = new HashSet<>();

    Set<String> R = new HashSet<>();
    Set<String> P = new HashSet<>(network.keySet());
    Set<String> X = new HashSet<>();

    bronKerbosch(network, R, P, X, largestClique);

    return largestClique;
  }

  private static void bronKerbosch(
      HashMap<String, HashSet<String>> network,
      Set<String> R,
      Set<String> P,
      Set<String> X,
      Set<String> largestClique) {
    if (P.isEmpty() && X.isEmpty()) {
      if (R.size() > largestClique.size()) {
        largestClique.clear();
        largestClique.addAll(R);
      }
    }

    for (String v : new HashSet<>(P)) {
      Set<String> newR = new HashSet<>(R);
      newR.add(v);

      Set<String> newP = new HashSet<>(P);
      newP.retainAll(network.get(v));

      Set<String> newX = new HashSet<>(X);
      newX.retainAll(network.get(v));

      bronKerbosch(network, newR, newP, newX, largestClique);

      P.remove(v);
      X.add(v);
    }
  }
}
