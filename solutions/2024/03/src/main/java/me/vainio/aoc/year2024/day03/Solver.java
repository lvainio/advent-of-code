package me.vainio.aoc.year2024.day03;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.vainio.aoc.cache.AocCache;

public class Solver {
    private static final int YEAR = 2024;
    private static final int DAY = 3;

    final private String input;

    public static void main(final String[] args) {
        final AocCache cache = new AocCache();

        final String input = cache.getInput(YEAR, DAY);
        final Solver solver = new Solver(input);

        final String part1 = solver.solvePart1();
        final String part2 = solver.solvePart2();

        System.out.println(part1);
        System.out.println(part2);

        cache.saveAnswer(YEAR, DAY, 1, part1);
        cache.saveAnswer(YEAR, DAY, 2, part2);
    }

    public Solver(final String input) {
        this.input = input;
    }

    public String solvePart1() {
        final String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(this.input);

        int sum = matcher.results()
                .mapToInt(m -> {
                    int factor1 = Integer.parseInt(m.group(1));
                    int factor2 = Integer.parseInt(m.group(2));
                    return factor1 * factor2;
                })
                .sum();

        return Integer.toString(sum);
    }

    public String solvePart2() {
        final String regex = "(mul\\((\\d{1,3}),(\\d{1,3})\\))|(do\\(\\))|(don't\\(\\))";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(this.input);

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
