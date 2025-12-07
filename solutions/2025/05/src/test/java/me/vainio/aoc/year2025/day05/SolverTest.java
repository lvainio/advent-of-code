package me.vainio.aoc.year2025.day05;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {

    private static final String EXAMPLE_INPUT = """
            3-5
            10-14
            16-20
            12-18
            
            1
            5
            8
            11
            17
            32""";

    private static Solver solver;

    @BeforeAll
    static void setUp() {
        solver = new Solver(EXAMPLE_INPUT);
    }

    @Test
    void testSolvePart1() {
        assertEquals("3", solver.solvePart1());
    }

    @Test
    void testSolvePart2() {
        assertEquals("14", solver.solvePart2());
    }
}
