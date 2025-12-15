package me.vainio.aoc.year2024.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 5;

  private final HashMap<Integer, HashSet<Integer>> orderingRulesMap;
  private final List<List<Integer>> updates;

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
    final String[] content = input.split("\r?\n\r?\n");

    final List<List<Integer>> orderingRules =
        Arrays.stream(content[0].split("\r?\n"))
            .map(
                line ->
                    Arrays.stream(line.split("\\|"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
            .toList();

    this.orderingRulesMap = new HashMap<>();
    for (List<Integer> tuple : orderingRules) {
      int x = tuple.get(0);
      int y = tuple.get(1);
      orderingRulesMap.computeIfAbsent(x, k -> new HashSet<>()).add(y);
    }

    this.updates =
        Arrays.stream(content[1].split("\r?\n"))
            .map(
                line ->
                    Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
            .collect(Collectors.toList());
  }

  public String solvePart1() {
    int sum =
        this.updates.stream()
            .filter(this::isUpdateCorrectlyOrdered)
            .map(update -> update.get(update.size() / 2))
            .reduce(0, Integer::sum);
    return String.valueOf(sum);
  }

  public String solvePart2() {
    int sum =
        this.updates.stream()
            .filter(update -> !isUpdateCorrectlyOrdered(update))
            .map(this::orderUpdate)
            .map(update -> update.get(update.size() / 2))
            .reduce(0, Integer::sum);
    return String.valueOf(sum);
  }

  private boolean isUpdateCorrectlyOrdered(List<Integer> update) {
    for (int i = 0; i < update.size(); i++) {
      for (int j = i + 1; j < update.size(); j++) {
        Set<Integer> rulesForJ = this.orderingRulesMap.get(update.get(j));
        if (rulesForJ != null && rulesForJ.contains(update.get(i))) {
          return false;
        }
      }
    }
    return true;
  }

  private List<Integer> orderUpdate(List<Integer> update) {
    List<Integer> updateOrdered = new ArrayList<>(update);
    updateOrdered.sort(
        (num1, num2) -> {
          if (orderingRulesMap.get(num1) != null && orderingRulesMap.get(num1).contains(num2)) {
            return -1;
          } else if (orderingRulesMap.get(num2) != null
              && orderingRulesMap.get(num2).contains(num1)) {
            return 1;
          }
          return 0;
        });
    return updateOrdered;
  }
}
