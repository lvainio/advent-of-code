package me.vainio.year2024.day01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {

    private static final String EXAMPLE_INPUT = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
        """;
        
    private static Solver solver;

    @BeforeAll
    static void setUp() {
        solver = new Solver(EXAMPLE_INPUT);
    }

    @Test
    void testSolvePart1() {
        String result = solver.solvePart1();
        assertEquals("11", result);
    }

    @Test
    void testSolvePart2() {
        String result = solver.solvePart2();
        assertEquals("31", result);
    }
}
