package me.vainio.aoc.year2024.day07;

import java.util.Arrays;
import java.util.List;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 7;

  private record Equation(long testValue, List<Long> numbers) {}

  private final List<Equation> equations;

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
    this.equations =
        input
            .lines()
            .map(
                line -> {
                  final String[] equation = line.split(":");
                  final long testValue = Long.parseLong(equation[0].trim());
                  final List<Long> nums =
                      Arrays.stream(equation[1].trim().split(" ")).map(Long::parseLong).toList();
                  return new Equation(testValue, nums);
                })
            .toList();
  }

  public String solvePart1() {
    final long count =
        this.equations.stream()
            .filter(e -> isValidEquationPart1(e.testValue(), e.numbers(), e.numbers().get(0), 1))
            .mapToLong(Equation::testValue)
            .sum();
    return Long.toString(count);
  }

  public String solvePart2() {
    final long count =
        this.equations.stream()
            .filter(e -> isValidEquationPart2(e.testValue(), e.numbers(), e.numbers().get(0), 1))
            .mapToLong(Equation::testValue)
            .sum();
    return Long.toString(count);
  }

  private static boolean isValidEquationPart1(
      final long testValue, final List<Long> nums, final long value, final int idx) {
    if (testValue < value) {
      return false;
    } else if (idx == nums.size()) {
      return testValue == value;
    } else {
      return isValidEquationPart1(testValue, nums, value + nums.get(idx), idx + 1)
          || isValidEquationPart1(testValue, nums, value * nums.get(idx), idx + 1);
    }
  }

  private static boolean isValidEquationPart2(
      final long testValue, final List<Long> nums, final long value, final int idx) {
    if (testValue < value) {
      return false;
    } else if (idx == nums.size()) {
      return testValue == value;
    } else {
      final long concatValue = Long.parseLong(value + "" + nums.get(idx));
      return isValidEquationPart2(testValue, nums, value + nums.get(idx), idx + 1)
          || isValidEquationPart2(testValue, nums, value * nums.get(idx), idx + 1)
          || isValidEquationPart2(testValue, nums, concatValue, idx + 1);
    }
  }
}
