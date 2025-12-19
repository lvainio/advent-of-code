package leo.aoc.year2015.day24;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private List<Integer> weights;

  public Solver(String input) {
    super(input);
    this.weights = input.lines().map(Integer::parseInt).toList();
  }

  @Override
  public String solvePart1() {
    int totalSum = this.weights.stream().reduce(0, Integer::sum);
    int targetSum = totalSum / 3;

    List<List<Integer>> groups = findSmallestGroups(weights, targetSum);
    groups.sort(Comparator.comparingLong(this::quantumEntanglement));

    return String.valueOf(quantumEntanglement(groups.get(0)));
  }

  @Override
  public String solvePart2() {
    int totalSum = this.weights.stream().reduce(0, Integer::sum);
    int targetSum = totalSum / 4;

    List<List<Integer>> groups = findSmallestGroups(weights, targetSum);
    groups.sort(Comparator.comparingLong(this::quantumEntanglement));

    return String.valueOf(quantumEntanglement(groups.get(0)));
  }

  private List<List<Integer>> findSmallestGroups(List<Integer> nums, int target) {
    List<List<Integer>> result = new ArrayList<>();
    findSubsets(nums, 0, new ArrayList<>(), 0, target, result);
    return result;
  }

  private void findSubsets(
      List<Integer> nums,
      int index,
      List<Integer> current,
      int sum,
      int target,
      List<List<Integer>> result) {
    if (sum == target) {
      if (result.isEmpty() || current.size() < result.get(0).size()) {
        result.clear();
        result.add(new ArrayList<>(current));
      } else if (current.size() == result.get(0).size()) {
        result.add(new ArrayList<>(current));
      }
      return;
    }
    if (sum > target || index >= nums.size()) return;

    current.add(nums.get(index));
    findSubsets(nums, index + 1, current, sum + nums.get(index), target, result);
    current.remove(current.size() - 1);
    findSubsets(nums, index + 1, current, sum, target, result);
  }

  private long quantumEntanglement(List<Integer> group) {
    return group.stream().mapToLong(i -> i).reduce(1, (a, b) -> a * b);
  }
}
