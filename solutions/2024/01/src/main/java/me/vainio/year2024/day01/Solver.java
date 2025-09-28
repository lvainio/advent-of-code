package me.vainio.year2024.day01;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solver {
    private List<Integer> leftList;
    private List<Integer> rightList;

    public static void main(String[] args) {  
        try {
            String input = new String(System.in.readAllBytes(), StandardCharsets.UTF_8).trim();
            Solver solver = new Solver(input);
            System.out.println(solver.solvePart1());
            System.out.println(solver.solvePart2());
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
            System.exit(1);
        }
    }

    public Solver(String input) {
        this.leftList = new ArrayList<>();
        this.rightList = new ArrayList<>();

        input.lines().forEach(line -> {
            String[] nums = line.trim().split("\\s+");
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