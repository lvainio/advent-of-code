package leo.aoc.year2024.day7;

import java.util.Arrays;
import java.util.List;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private record Equation(long testValue, List<Long> numbers) {}

    private final List<Equation> equations;

    public Solver(String input) {
        super(input);

        this.equations = input.lines().map(line -> {
            String[] equation = line.split(":");
            long testValue = Long.parseLong(equation[0].trim());
            List<Long> nums = Arrays.stream(equation[1].trim().split(" "))
                    .map(Long::parseLong)  
                    .toList();  
            return new Equation(testValue, nums);
        }).toList();
    }

    @Override
    public String solvePart1() {
        long count = this.equations.stream()
            .filter(e -> isValidEquationPart1(e.testValue(), e.numbers(), e.numbers().get(0), 1))
            .mapToLong(e -> e.testValue())
            .sum();
        return Long.toString(count);
    }

    @Override
    public String solvePart2() {
        long count = this.equations.stream()
            .filter(e -> isValidEquationPart2(e.testValue(), e.numbers(), e.numbers().get(0), 1))
            .mapToLong(e -> e.testValue())
            .sum();
        return Long.toString(count);
    }

    private static boolean isValidEquationPart1(
        long testValue, 
        List<Long> nums, 
        long value, 
        int idx) 
    {
        if (testValue < value) {
            return false;
        } else if (idx == nums.size()) { 
            return testValue == value;
        } else {
             return isValidEquationPart1(testValue, nums, value + nums.get(idx), idx+1) 
                || isValidEquationPart1(testValue, nums, value * nums.get(idx), idx+1);
        }
    }

    private static boolean isValidEquationPart2(
        long testValue, 
        List<Long> nums, 
        long value, 
        int idx) 
    {
        if (testValue < value) {
            return false;
        } else if (idx == nums.size()) { 
            return testValue == value;
        } else {
            long concatValue = Long.parseLong(value + "" + nums.get(idx));
            return isValidEquationPart2(testValue, nums, value + nums.get(idx), idx+1) 
                || isValidEquationPart2(testValue, nums, value * nums.get(idx), idx+1) 
                || isValidEquationPart2(testValue, nums, concatValue, idx+1);
        }
    }
}
