package me.vainio.aoc.year2025.day01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import me.vainio.aoc.cache.AocCache;

public class Solver {
    private static final int YEAR = 2024;
    private static final int DAY = 1;

    private final List<Integer> leftList;
    private final List<Integer> rightList;

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
        this.leftList = new ArrayList<>();
        this.rightList = new ArrayList<>();

        input.lines().forEach(line -> {
            final String[] nums = line.trim().split("\\s+");
            this.leftList.add(Integer.valueOf(nums[0]));
            this.rightList.add(Integer.valueOf(nums[1]));
        });
    }

    public String solvePart1() {
        List<Integer> leftListCopy = new ArrayList<>(this.leftList);
        List<Integer> rightListCopy = new ArrayList<>(this.rightList);

        Collections.sort(leftListCopy);
        Collections.sort(rightListCopy);

        final int totalDistance = IntStream.range(0, leftListCopy.size()).map(idx -> {
            int left = leftListCopy.get(idx);
            int right = rightListCopy.get(idx);
            return Math.abs(left - right);
        }).sum();

        return Integer.toString(totalDistance);
    }

    public String solvePart2() {
        final Map<Integer, Long> frequencyMap = this.rightList.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        final long similarityScore = this.leftList.stream()
            .mapToLong(key -> key * frequencyMap.getOrDefault(key, 0L))
            .sum();

        return Long.toString(similarityScore);
    }
}