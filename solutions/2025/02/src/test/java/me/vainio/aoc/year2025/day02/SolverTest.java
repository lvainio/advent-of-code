package me.vainio.aoc.year2025.day02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {

  private static final String EXAMPLE_INPUT =
      "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,"
          + "1698522-1698528,446443-446449,38593856-38593862,565653-565659,"
          + "824824821-824824827,2121212118-2121212124";

  private static Solver solver;

  @BeforeAll
  static void setUp() {
    solver = new Solver(EXAMPLE_INPUT);
  }

  @Test
  void testSolvePart1() {
    assertEquals("1227775554", solver.solvePart1());
  }

  @Test
  void testSolvePart2() {
    assertEquals("4174379265", solver.solvePart2());
  }
}
