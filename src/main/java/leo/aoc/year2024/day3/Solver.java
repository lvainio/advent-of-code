package leo.aoc.year2024.day3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    public Solver(String input) {
        super(input);
    }
    
    @Override
    public String solvePart1() {
        String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.input);

        int sum = matcher.results()
                         .mapToInt(m -> {
                             int factor1 = Integer.parseInt(m.group(1));
                             int factor2 = Integer.parseInt(m.group(2));
                             return factor1 * factor2;
                         })
                         .sum();
        
        return Integer.toString(sum);
    }

    @Override
    public String solvePart2() {
        String regex = "(mul\\((\\d{1,3}),(\\d{1,3})\\))|(do\\(\\))|(don't\\(\\))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.input);

        int sum = 0;
        boolean mulEnabled = true;
        while (matcher.find()) {
            String instruction = matcher.group();
            switch (instruction) {
                case "do()" -> mulEnabled = true;
                case "don't()" -> mulEnabled = false;
                default -> {
                    if (mulEnabled) {
                        int factor1 = Integer.parseInt(matcher.group(2));
                        int factor2 = Integer.parseInt(matcher.group(3));
                        sum += factor1 * factor2;
                    }
                }
            }
        }
        return Integer.toString(sum);   
    }
}
