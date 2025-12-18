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

    // I believe a solution to this problem is to iterate over all
    // xmas trees.

    // for each one, call a backtracking dfs function that iterates
    // over all positions in the grid. at each position try to place
    // the present and if it can then recurse to the next present and do the same.

    // we also need to iterate over all rotations for each present.

    // this is defo a brute force solution but it could work if the search space is smaller

    // the dfs/backtrac should return a boolean which is nice and easy. the end case is when all
    // presents are placed
    // which is super easy to implement

    // to implement this I believe we can use our grid class (add rotate method support) but then
    // extend
    // the grid class and implement a canPlace/palce/remove methods for placing presents (these are
    // not relevant
    // for normal grids so we should not add them there).

    // presents are generally placed more than one time so need to think about that as well. seems
    // to be arouund
    // 100-200 presents in each area which is quite a lot.

    // I believe its doable.

    // lets highball the calculations

    // we have around 1000 test cases, each with 300 presents, each present has 4 rotations and we
    // have to check
    // each area is around 50x50 = 2500 positions this totals to around 3000000000 operations which
    // is probably
    // alright. We can log each test case so that we can see progress.

    // backtracking is pretty space efficient, and hopefully the grid can be semi efficient as well.

    // maybe its possible to use dynamic programming to speed this up. that would require state to
    // be nr
    return "";
  }

  public String solvePart2() {
    // FIXME: Implement solution for part 2
    return "";
  }
}
