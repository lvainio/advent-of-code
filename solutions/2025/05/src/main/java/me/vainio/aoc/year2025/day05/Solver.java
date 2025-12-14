package me.vainio.aoc.year2025.day05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2025;
  private static final int DAY = 5;

  private final List<Range> ranges;
  private final List<Long> ids;

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
    final String[] parts = input.split("\n\n");

    ranges =
        parts[0]
            .lines()
            .map(
                line -> {
                  String[] range = line.split("-");
                  long start = Long.parseLong(range[0]);
                  long end = Long.parseLong(range[1]);
                  return new Range(start, end);
                })
            .toList();

    ids = parts[1].lines().map(Long::parseLong).toList();
  }

  public String solvePart1() {
    final long count =
        ids.stream().filter(id -> ranges.stream().anyMatch(range -> range.contains(id))).count();
    return String.valueOf(count);
  }

  public String solvePart2() {
    List<Range> ranges = new ArrayList<>(this.ranges);
    ranges.sort(Comparator.comparingLong(Range::start));
    List<Range> mergedRanges = new ArrayList<>();

    Range current = ranges.getFirst();
    for (int i = 1; i < ranges.size(); i++) {
      final Range next = ranges.get(i);
      if (next.start() <= current.end()) {
        current = new Range(current.start(), Math.max(current.end(), next.end()));
      } else {
        mergedRanges.add(current);
        current = next;
      }
    }
    mergedRanges.add(current);

    return String.valueOf(mergedRanges.stream().mapToLong(Range::size).sum());
  }
}
