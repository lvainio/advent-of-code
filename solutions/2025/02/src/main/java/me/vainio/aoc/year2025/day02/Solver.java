package me.vainio.aoc.year2025.day02;

import me.vainio.aoc.cache.AocCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solver {
    private static final int YEAR = 2025;
    private static final int DAY = 2;

    private final List<Range> ranges;

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
        ranges = Arrays.stream(input.split(","))
                .map(range -> {
                    final String[] parts = range.split("-");
                    final long start = Long.parseLong(parts[0]);
                    final long end = Long.parseLong(parts[1]);
                    return new Range(start, end);
                })
                .toList();
    }

    public String solvePart1() {
        List<Long> invalidIds = new ArrayList<>();
        for (Range range : ranges) {
            for (long i = range.start(); i <= range.end(); i++) {
                if (isInvalid1(i)) {
                    invalidIds.add(i);
                }
            }
        }
        return String.valueOf(invalidIds.stream().reduce(0L, Long::sum));
    }

    private boolean isInvalid1(final long i) {
        final String strNum = String.valueOf(i);
        if (strNum.length() % 2 != 0) {
            return false;
        }
        final int mid = strNum.length() / 2;
        return strNum.substring(0, mid).equals(strNum.substring(mid));
    }

    public String solvePart2() {
        List<Long> invalidIds = new ArrayList<>();
        for (Range range : ranges) {
            for (long i = range.start(); i <= range.end(); i++) {
                if (isInvalid2(i)) {
                    invalidIds.add(i);
                }
            }
        }
        return String.valueOf(invalidIds.stream().reduce(0L, Long::sum));
    }

    private boolean isInvalid2(final long i) {
        final String strNum = String.valueOf(i);
        if (strNum.length() == 1) {
            return false;
        }
        for (int j = 1; j <= strNum.length() / 2; j++) {
            if (strNum.length() % j != 0) {
                continue;
            }
            final String part = strNum.substring(0, j);
            boolean valid = false;
            for (int k = 0; k < strNum.length(); k += j) {
                if (!part.equals(strNum.substring(k, k + j))) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                return true;
            }
        }
        return false;
    }
}