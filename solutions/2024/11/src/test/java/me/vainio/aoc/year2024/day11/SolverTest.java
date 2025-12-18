package me.vainio.aoc.year2024.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {

  private static final String EXAMPLE_INPUT =
      """
      125 17\
      """;

  private static Solver solver;

  @BeforeAll
  static void setUp() {
    solver = new Solver(EXAMPLE_INPUT);
  }

  @Test
  void testSolvePart1() {
    assertEquals("55312", solver.solvePart1());
  }

  @Test
  void testSolvePart2() {
    assertEquals("65601038650482", solver.solvePart2());
  }
}
