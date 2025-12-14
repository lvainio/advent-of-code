package me.vainio.aoc.year2025.day01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {

  private static final String EXAMPLE_INPUT =
      """
      L68
      L30
      R48
      L5
      R60
      L55
      L1
      L99
      R14
      L82
      """;

  private static Solver solver;

  @BeforeAll
  static void setUp() {
    solver = new Solver(EXAMPLE_INPUT);
  }

  @Test
  void testSolvePart1() {
    assertEquals("3", solver.solvePart1());
  }

  @Test
  void testSolvePart2() {
    assertEquals("6", solver.solvePart2());
  }
}
