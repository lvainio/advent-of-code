package me.vainio.aoc.year2025.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SolverTest {

  private static final String EXAMPLE_INPUT_P1 =
      """
      aaa: you hhh
      you: bbb ccc
      bbb: ddd eee
      ccc: ddd eee fff
      ddd: ggg
      eee: out
      fff: out
      ggg: out
      hhh: ccc fff iii
      iii: out\
      """;

  private static final String EXAMPLE_INPUT_P2 =
      """
      svr: aaa bbb
      aaa: fft
      fft: ccc
      bbb: tty
      tty: ccc
      ccc: ddd eee
      ddd: hub
      hub: fff
      eee: dac
      dac: fff
      fff: ggg hhh
      ggg: out
      hhh: out\
      """;

  @Test
  void testSolvePart1() {
    Solver solver = new Solver(EXAMPLE_INPUT_P1);
    assertEquals("5", solver.solvePart1());
  }

  @Test
  void testSolvePart2() {
    Solver solver = new Solver(EXAMPLE_INPUT_P2);
    assertEquals("2", solver.solvePart2());
  }
}
