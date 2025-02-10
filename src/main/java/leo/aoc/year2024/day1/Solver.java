package leo.aoc.year2024.day1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private List<Integer> leftList;
    private List<Integer> rightList;

    public Solver(String input) {
        super(input);

        this.leftList = new ArrayList<>();
        this.rightList = new ArrayList<>();

        input.lines().forEach(line -> {
            String[] nums = line.split("\\s+");
            this.leftList.add(Integer.parseInt(nums[0]));
            this.rightList.add(Integer.parseInt(nums[1]));
        });
    }

    @Override
    public String solvePart1() {
        List<Integer> leftListCopy = new ArrayList<>(this.leftList);
        List<Integer> rightListCopy = new ArrayList<>(this.rightList);

        Collections.sort(leftListCopy);
        Collections.sort(rightListCopy);

        int totalDistance = IntStream.range(0, leftListCopy.size()).map(i -> {
            int left = leftListCopy.get(i);
            int right = rightListCopy.get(i);
            return Math.abs(left - right);
        }).sum();

        return Integer.toString(totalDistance);
    }

    @Override
    public String solvePart2() {
        Map<Integer, Long> frequencyMap = this.rightList.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long similarityScore = this.leftList.stream()
            .mapToLong(key -> key * frequencyMap.getOrDefault(key, 0L))
            .sum();

        return Long.toString(similarityScore);
    }
}