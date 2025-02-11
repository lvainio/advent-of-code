package leo.aoc.year2015.day1;

import java.util.List;
import java.util.stream.Collectors;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private enum Token {
        LPAR,
        RPAR
    }

    private List<Token> tokens;

    public Solver(String input) {
        super(input);
        
        this.tokens = input.chars()
            .mapToObj(c -> c == '(' ? Token.LPAR : Token.RPAR)
            .collect(Collectors.toList());
    }

    @Override
    public String solvePart1() {
        int floor = 0;
        for (Token token : tokens) {
            switch (token) {
                case LPAR -> floor++;
                case RPAR -> floor--;
                default -> throw new IllegalArgumentException("Invalid token!");
            }
        }
        return Integer.toString(floor);
    }

    @Override
    public String solvePart2() {
        int floor = 0;
        int position = 1;
        for (Token token : tokens) {
            switch (token) {
                case LPAR -> floor++;
                case RPAR -> floor--;
                default -> throw new IllegalArgumentException("Invalid token!");
            }
            if (floor == -1) {
                break;
            }
            position++;
        }
        return Integer.toString(position);
    }
}
