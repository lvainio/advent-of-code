package me.vainio.aoc.year2025.day09;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {

  private static final String EXAMPLE_INPUT =
      """
      7,1
      11,1
      11,7
      9,7
      9,5
      2,5
      2,3
      7,3\
      """;

  private static Solver solver;

  @BeforeAll
  static void setUp() {
    solver = new Solver(EXAMPLE_INPUT);
  }

  @Test
  void testSolvePart1() {
    assertEquals("50", solver.solvePart1());
  }

  @Test
  void testSolvePart2() {
    assertEquals("24", solver.solvePart2());
  }
}
