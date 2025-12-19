package me.vainio.aoc.year2024.day19;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 19;

  private final List<String> patterns;
  private final List<String> designs;

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
    String[] PatternsAndDesigns = input.split("\r?\n\r?\n");

    this.patterns = Arrays.asList(PatternsAndDesigns[0].split(", "));
    this.designs = Arrays.asList(PatternsAndDesigns[1].split("\r?\n"));
  }

  public String solvePart1() {
    long numValidDesigns = designs.stream().filter(s -> isValidDesign(s, patterns)).count();
    return Long.toString(numValidDesigns);
  }

  public String solvePart2() {
    HashMap<String, Long> mem = new HashMap<>();
    long numArrangements =
        designs.stream().mapToLong(s -> countArrangements(s, patterns, mem)).sum();
    return Long.toString(numArrangements);
  }

  private boolean isValidDesign(final String design, List<String> patterns) {
    if (design.isEmpty()) {
      return true;
    }
    for (String pattern : patterns) {
      if (design.startsWith(pattern)
          && isValidDesign(design.substring(pattern.length()), patterns)) {
        return true;
      }
    }
    return false;
  }

  private long countArrangements(
      String design, List<String> patterns, HashMap<String, Long> designToCount) {
    if (design.isEmpty()) {
      return 1;
    }
    if (designToCount.containsKey(design)) {
      return designToCount.get(design);
    }
    long count = 0;
    for (String pattern : patterns) {
      if (design.startsWith(pattern)) {
        count += countArrangements(design.substring(pattern.length()), patterns, designToCount);
      }
    }
    designToCount.put(design, count);
    return count;
  }
}
