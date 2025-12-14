package me.vainio.aoc.year2024.day03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SolverTest {

  private static final String EXAMPLE_INPUT_1 =
      "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
  private static final String EXAMPLE_INPUT_2 =
      "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

  @Test
  void testSolvePart1() {
    final Solver solver = new Solver(EXAMPLE_INPUT_1);
    assertEquals("161", solver.solvePart1());
  }

  @Test
  void testSolvePart2() {
    final Solver solver = new Solver(EXAMPLE_INPUT_2);
    assertEquals("48", solver.solvePart2());
  }
}
