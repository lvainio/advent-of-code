package me.vainio.aoc.year2024.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {

  private static final String EXAMPLE_INPUT =
      """
      89010123
      78121874
      87430965
      96549874
      45678903
      32019012
      01329801
      10456732\
      """;

  private static Solver solver;

  @BeforeAll
  static void setUp() {
    solver = new Solver(EXAMPLE_INPUT);
  }

  @Test
  void testSolvePart1() {
    assertEquals("36", solver.solvePart1());
  }

  @Test
  void testSolvePart2() {
    assertEquals("81", solver.solvePart2());
  }
}
