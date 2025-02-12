package leo.aoc.year2015.day17;

import java.util.List;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private List<Integer> numbers;

    public Solver(String input) {
        super(input);
        this.numbers = input.lines().map(Integer::parseInt).toList();
    }

    @Override
    public String solvePart1() {
        return Integer.toString(countCombinations(150, 0));
    }

    @Override
    public String solvePart2() {
        int minContainers = computeMinCombination(150, 0, 0);
        return Integer.toString(countCombinationsPart2(150, 0, 0, minContainers));
    }

    private int countCombinations(int remaining, int idx) {
        if (remaining == 0) {
            return 1;
        }
        if (remaining < 0) {
            return 0;
        }
        int sum = 0;
        for (int i = idx; i < this.numbers.size(); i++) {
            sum += countCombinations(remaining - this.numbers.get(i), i + 1);
        }
        return sum;
    }

    private int computeMinCombination(int remaining, int idx, int numContainers) {
        if (remaining == 0) {
            return numContainers;
        }
        if (remaining < 0) {
            return Integer.MAX_VALUE;
        }
        int min = Integer.MAX_VALUE;
        for (int i = idx; i < this.numbers.size(); i++) {
            int numContainer = computeMinCombination(
                    remaining - this.numbers.get(idx),
                    i + 1,
                    numContainers + 1);
            min = Math.min(numContainer, min);
        }
        return min;
    }

    private int countCombinationsPart2(int remaining, int idx, int numContaiers, int goal) {
        if (remaining == 0 && numContaiers == goal) {
            return 1;
        }
        if (remaining <= 0) {
            return 0;
        }
        int sum = 0;
        for (int i = idx; i < this.numbers.size(); i++) {
            sum += countCombinationsPart2(
                    remaining - this.numbers.get(i),
                    i + 1,
                    numContaiers + 1,
                    goal);
        }
        return sum;
    }
}
