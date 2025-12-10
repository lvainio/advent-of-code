package me.vainio.aoc.year2025.day07;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {

    private static final String EXAMPLE_INPUT = """
            .......S.......
            ...............
            .......^.......
            ...............
            ......^.^......
            ...............
            .....^.^.^.....
            ...............
            ....^.^...^....
            ...............
            ...^.^...^.^...
            ...............
            ..^...^.....^..
            ...............
            .^.^.^.^.^...^.
            ...............""";

    private static Solver solver;

    @BeforeAll
    static void setUp() {
        solver = new Solver(EXAMPLE_INPUT);
    }

    @Test
    void testSolvePart1() {
        assertEquals("21", solver.solvePart1());
    }

    @Test
    void testSolvePart2() {
        assertEquals("40", solver.solvePart2());
    }
}
