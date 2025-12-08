package me.vainio.aoc.year2025.day06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {

    private static final String EXAMPLE_INPUT = """
            123 328  51 64\s
             45 64  387 23\s
              6 98  215 314
            *   +   *   +  \s""";

    private static Solver solver;

    @BeforeAll
    static void setUp() {
        solver = new Solver(EXAMPLE_INPUT);
    }

    @Test
    void testSolvePart1() {
        assertEquals("4277556", solver.solvePart1());
    }

    @Test
    void testSolvePart2() {
        assertEquals("3263827", solver.solvePart2());
    }
}
