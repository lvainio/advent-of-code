package me.vainio.aoc.year2025.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {

  private static final String EXAMPLE_INPUT =
      """
      INPUT
      INPUT
      INPUT\
      """;

  private static Solver solver;

  @BeforeAll
  static void setUp() {
    solver = new Solver(EXAMPLE_INPUT);
  }

  @Test
  void testSolvePart1() {
    assertEquals("", solver.solvePart1());
  }

  @Test
  void testSolvePart2() {
    assertEquals("", solver.solvePart2());
  }
}
