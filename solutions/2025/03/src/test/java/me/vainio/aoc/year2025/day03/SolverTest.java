package me.vainio.aoc.year2025.day03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {

    private static final String EXAMPLE_INPUT = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111""";

    private static Solver solver;

    @BeforeAll
    static void setUp() {
        solver = new Solver(EXAMPLE_INPUT);
    }

    @Test
    void testSolvePart1() {
        assertEquals("357", solver.solvePart1());
    }

    @Test
    void testSolvePart2() {
        assertEquals("3121910778619", solver.solvePart2());
    }
}
