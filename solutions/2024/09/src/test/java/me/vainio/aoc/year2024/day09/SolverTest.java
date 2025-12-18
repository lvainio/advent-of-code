package me.vainio.aoc.year2024.day09;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {

  private static final String EXAMPLE_INPUT =
      """
      2333133121414131402\
      """;

  private static Solver solver;

  @BeforeAll
  static void setUp() {
    solver = new Solver(EXAMPLE_INPUT);
  }

  @Test
  void testSolvePart1() {
    assertEquals("1928", solver.solvePart1());
  }

  @Test
  void testSolvePart2() {
    assertEquals("2858", solver.solvePart2());
  }
}
