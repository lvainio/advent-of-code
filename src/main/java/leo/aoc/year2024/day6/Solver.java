package leo.aoc.year2024.day6;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private char[][] grid;

    public Solver(String input) {
        super(input);

        this.grid = input.lines()
                .map(String::toCharArray)  
                .toArray(char[][]::new);
    }
    
    @Override
    public String solvePart1() {
        return ";";
    }

    @Override
    public String solvePart2() {
        return ";";
    }
    
}
