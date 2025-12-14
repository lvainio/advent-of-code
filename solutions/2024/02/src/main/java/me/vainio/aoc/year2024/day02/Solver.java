package me.vainio.aoc.year2024.day02;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 2;

  private final List<List<Integer>> reports;

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
    this.reports =
        input
            .lines()
            .map(line -> line.split("\\s+"))
            .map(nums -> List.of(nums).stream().map(Integer::parseInt).collect(Collectors.toList()))
            .collect(Collectors.toList());
  }

  public String solvePart1() {
    final long numSafe = this.reports.stream().filter(report -> isSafePart1(report)).count();
    return Long.toString(numSafe);
  }

  public String solvePart2() {
    final long numSafe = this.reports.stream().filter(report -> isSafePart2(report)).count();
    return Long.toString(numSafe);
  }

  private boolean isSafePart1(final List<Integer> report) {
    return (isAscending(report) || isDescending(report)) && hasSafeDiffs(report);
  }

  private boolean isSafePart2(final List<Integer> report) {
    if ((isAscending(report) || isDescending(report)) && hasSafeDiffs(report)) {
      return true;
    }
    for (int i = 0; i < report.size(); i++) {
      List<Integer> subReport = new ArrayList<>(report);
      subReport.remove(i);
      if ((isAscending(subReport) || isDescending(subReport)) && hasSafeDiffs(subReport)) {
        return true;
      }
    }
    return false;
  }

  private boolean isAscending(final List<Integer> report) {
    if (report.size() <= 1) {
      return true;
    }
    for (int i = 0; i < report.size() - 1; i++) {
      final int first = report.get(i);
      final int second = report.get(i + 1);
      if (first >= second) {
        return false;
      }
    }
    return true;
  }

  private boolean isDescending(final List<Integer> report) {
    if (report.size() <= 1) {
      return true;
    }
    for (int i = 0; i < report.size() - 1; i++) {
      final int first = report.get(i);
      final int second = report.get(i + 1);
      if (first <= second) {
        return false;
      }
    }
    return true;
  }

  private boolean hasSafeDiffs(final List<Integer> report) {
    if (report.size() <= 1) {
      return true;
    }
    for (int i = 0; i < report.size() - 1; i++) {
      final int first = report.get(i);
      final int second = report.get(i + 1);
      final int diff = Math.abs(first - second);
      if (diff < 1 || diff > 3) {
        return false;
      }
    }
    return true;
  }
}
