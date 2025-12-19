package me.vainio.aoc.year2024.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SolverTest {

  private static final String EXAMPLE_INPUT =
      """
      5,4
      4,2
      4,5
      3,0
      2,1
      6,3
      2,4
      1,5
      0,6
      3,3
      2,6
      5,1
      1,2
      5,5
      2,5
      6,5
      1,4
      0,4
      6,4
      1,1
      6,1
      1,0
      0,5
      1,6
      2,0\
      """;

  private static Solver solver;

  @BeforeAll
  static void setUp() {
    solver = new Solver(EXAMPLE_INPUT);
  }

  @Test
  @Disabled("Real input uses other dimensions")
  void testSolvePart1() {
    assertEquals("22", solver.solvePart1());
  }

  @Test
  @Disabled("Real input uses other dimensions")
  void testSolvePart2() {
    assertEquals("6,1", solver.solvePart2());
  }
}
