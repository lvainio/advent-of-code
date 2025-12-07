package me.vainio.aoc.year2025.day04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {

    private static final String EXAMPLE_INPUT = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.""";

    private static Solver solver;

    @BeforeAll
    static void setUp() {
        solver = new Solver(EXAMPLE_INPUT);
    }

    @Test
    void testSolvePart1() {
        assertEquals("13", solver.solvePart1());
    }

    @Test
    void testSolvePart2() {
        assertEquals("43", solver.solvePart2());
    }
}
