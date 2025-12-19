package me.vainio.aoc.year2024.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SolverTest {

  private static final String EXAMPLE_INPUT_1 =
      """
      1
      10
      100
      2024\
      """;

  private static final String EXAMPLE_INPUT_2 =
      """
      1
      2
      3
      2024\
      """;

  @Test
  void testSolvePart1() {
    Solver solver = new Solver(EXAMPLE_INPUT_1);
    assertEquals("37327623", solver.solvePart1());
  }

  @Test
  void testSolvePart2() {
    Solver solver = new Solver(EXAMPLE_INPUT_2);
    assertEquals("23", solver.solvePart2());
  }
}
