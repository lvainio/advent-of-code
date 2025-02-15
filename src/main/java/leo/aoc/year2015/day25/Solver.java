package leo.aoc.year2015.day25;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private final int targetRow;
    private final int targetCol;

    public Solver(String input) {
        super(input);
        
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(this.input);

        matcher.find();
        this.targetRow = Integer.parseInt(matcher.group());
        matcher.find();
        this.targetCol = Integer.parseInt(matcher.group());
    }

    @Override
    public String solvePart1() {
        long num = 20151125;
        int row = 1;
        int col = 1;
        while(!(row == this.targetRow && col == this.targetCol)) {
            if (row == 1) {
                row = col + 1;
                col = 1;
            } else {
                row--;
                col++;
            }
            num = (num * 252533) % 33554393;
        }
        return Long.toString(num);
    }

    @Override
    public String solvePart2() {
        return "Day 25 has no part 2!";
    }
}
