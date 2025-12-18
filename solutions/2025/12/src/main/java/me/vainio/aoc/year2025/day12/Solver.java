package me.vainio.aoc.year2025.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import me.vainio.aoc.cache.AocCache;
import me.vainio.aoc.util.Grid;

public class Solver {
  private static final int YEAR = 2025;
  private static final int DAY = 12;

  private static final char PRESENT = '#';
  private static final char EMPTY = '.';

  private record Area(int width, int height, List<Integer> quantities) {}

  private final List<Grid<Character>> presents;
  private final List<Area> areas;

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
    List<String> parts = Arrays.stream(input.split("\n\n")).map(String::trim).toList();

    presents =
        parts.subList(0, parts.size() - 1).stream()
            .map(present -> present.lines().skip(1).collect(Collectors.joining("\n")))
            .map(Grid::ofChars)
            .toList();

    areas =
        parts
            .getLast()
            .lines()
            .map(
                line -> {
                  final Pattern pattern = Pattern.compile("\\d+");
                  final Matcher matcher = pattern.matcher(line);
                  if (!matcher.find()) {
                    throw new IllegalArgumentException("Line missing width integer: " + line);
                  }
                  final int width = Integer.parseInt(matcher.group());
                  if (!matcher.find()) {
                    throw new IllegalArgumentException("Line missing height integer: " + line);
                  }
                  final int height = Integer.parseInt(matcher.group());
                  final List<Integer> quantities = new ArrayList<>();
                  while (matcher.find()) {
                    quantities.add(Integer.parseInt(matcher.group()));
                  }
                  return new Area(width, height, quantities);
                })
            .toList();
  }

  public String solvePart1() {
    int count = 0;
    for (Area area : areas) {
      final int areaSize = area.width * area.height;
      int presentSizeSum = 0;
      for (int i = 0; i < area.quantities.size(); i++) {
        final Grid<Character> present = presents.get(i);
        presentSizeSum += area.quantities.get(i) * present.countOccurrences(PRESENT);
      }
      if (presentSizeSum <= areaSize) {
        count++;
      }
    }
    return String.valueOf(count);
  }

  public String solvePart2() {
    // No part 2 for this day
    return "";
  }
}
