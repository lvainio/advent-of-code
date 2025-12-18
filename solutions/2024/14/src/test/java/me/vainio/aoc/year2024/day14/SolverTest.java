package me.vainio.aoc.year2024.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SolverTest {

  private static final String EXAMPLE_INPUT =
      """
      p=0,4 v=3,-3
      p=6,3 v=-1,-3
      p=10,3 v=-1,2
      p=2,0 v=2,-1
      p=0,0 v=1,3
      p=3,0 v=-2,-2
      p=7,6 v=-1,-3
      p=3,0 v=-1,-2
      p=9,3 v=2,3
      p=7,3 v=-1,2
      p=2,4 v=2,-3
      p=9,5 v=-3,-3\
      """;

  private static Solver solver;

  @BeforeAll
  static void setUp() {
    solver = new Solver(EXAMPLE_INPUT);
  }

  @Test
  @Disabled("Test uses other dimensions than the actual problem input")
  void testSolvePart1() {
    assertEquals("12", solver.solvePart1());
  }

  @Test
  @Disabled("Part 2 has no test case")
  void testSolvePart2() {
    assertEquals("", solver.solvePart2());
  }
}
