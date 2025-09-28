package me.vainio.year2024.day01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            StringBuilder inputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                inputBuilder.append(line).append("\n");
            }
            String input = inputBuilder.toString().trim();
            
            Solver solver = new Solver(input);
            String part1 = solver.solvePart1();
            String part2 = solver.solvePart2();

            System.out.println(part1);
            System.out.println(part2);
        } catch (Exception e) {
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

        int totalDistance = IntStream.range(0, leftListCopy.size()).map(idx -> {
            int left = leftListCopy.get(idx);
            int right = rightListCopy.get(idx);
            return Math.abs(left - right);
        }).sum();

        return Integer.toString(totalDistance);
    }

    public String solvePart2() {
        Map<Integer, Long> frequencyMap = this.rightList.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long similarityScore = this.leftList.stream()
            .mapToLong(key -> key * frequencyMap.getOrDefault(key, 0L))
            .sum();

        return Long.toString(similarityScore);
    }
}