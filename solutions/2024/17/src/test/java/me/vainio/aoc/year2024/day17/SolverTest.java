package me.vainio.aoc.year2024.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SolverTest {

  private static final String EXAMPLE_INPUT_1 =
      """
      Register A: 729
      Register B: 0
      Register C: 0

      Program: 0,1,5,4,3,0\
      """;

  private static final String EXAMPLE_INPUT_2 =
      """
      Register A: 2024
      Register B: 0
      Register C: 0

      Program: 0,3,5,4,3,0\
      """;

  private static Solver solver;

  @Test
  void testSolvePart1() {
    Solver solver = new Solver(EXAMPLE_INPUT_1);
    assertEquals("4,6,3,5,6,3,5,2,1,0", solver.solvePart1());
  }

  @Test
  void testSolvePart2() {
    Solver solver = new Solver(EXAMPLE_INPUT_2);
    assertEquals("117440", solver.solvePart2());
  }
}
