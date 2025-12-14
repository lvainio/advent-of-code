package leo.aoc.year2015.day15;

import java.util.ArrayList;
import java.util.List;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private record Ingredient(
      String name, int capacity, int durability, int flavor, int texture, int calories) {}

  private List<Ingredient> ingredients;

  public Solver(String input) {
    super(input);

    this.ingredients =
        input
            .lines()
            .map(
                line -> {
                  String[] parts = line.split("\\s+");
                  String name = parts[0];
                  int capacity = Integer.parseInt(parts[2].substring(0, parts[2].length() - 1));
                  int durability = Integer.parseInt(parts[4].substring(0, parts[4].length() - 1));
                  int flavor = Integer.parseInt(parts[6].substring(0, parts[6].length() - 1));
                  int texture = Integer.parseInt(parts[8].substring(0, parts[8].length() - 1));
                  int calories = Integer.parseInt(parts[10]);
                  return new Ingredient(name, capacity, durability, flavor, texture, calories);
                })
            .toList();
  }

  @Override
  public String solvePart1() {
    return Long.toString(computeBestScore());
  }

  @Override
  public String solvePart2() {
    return Long.toString(computeBestScorePart2());
  }

  private long computeBestScore() {
    List<List<Integer>> allCombinations = new ArrayList<>();
    generateAllCombinations(new ArrayList<>(), this.ingredients.size(), 0, allCombinations);

    return allCombinations.stream().mapToLong(this::computeScoreForCombination).max().orElse(0);
  }

  private long computeBestScorePart2() {
    List<List<Integer>> allCombinations = new ArrayList<>();
    generateAllCombinationsPart2(new ArrayList<>(), this.ingredients.size(), 0, allCombinations);

    return allCombinations.stream().mapToLong(this::computeScoreForCombination).max().orElse(0);
  }

  private long computeScoreForCombination(List<Integer> combination) {
    long capacity = 0;
    long durability = 0;
    long flavor = 0;
    long texture = 0;
    for (int i = 0; i < this.ingredients.size(); i++) {
      Ingredient ingredient = this.ingredients.get(i);
      int numUsed = combination.get(i);
      capacity += numUsed * ingredient.capacity();
      durability += numUsed * ingredient.durability();
      flavor += numUsed * ingredient.flavor();
      texture += numUsed * ingredient.texture();
    }
    capacity = Math.max(0, capacity);
    durability = Math.max(0, durability);
    flavor = Math.max(0, flavor);
    texture = Math.max(0, texture);
    return capacity * durability * flavor * texture;
  }

  private void generateAllCombinations(
      List<Integer> combination,
      int numIngredients,
      int numUsed,
      List<List<Integer>> allCombinations) {
    if (combination.size() == numIngredients) {
      if (numUsed == 100) {
        allCombinations.add(combination);
      }
      return;
    }
    int numLeft = 100 - numUsed;
    for (int i = 0; i <= numLeft; i++) {
      List<Integer> newCombination = new ArrayList<>(combination);
      newCombination.add(i);
      generateAllCombinations(newCombination, numIngredients, numUsed + i, allCombinations);
    }
  }

  private void generateAllCombinationsPart2(
      List<Integer> combination,
      int numIngredients,
      int numUsed,
      List<List<Integer>> allCombinations) {
    if (combination.size() == numIngredients) {
      int numCalories = computeNumCalories(combination);
      if (numUsed == 100 && numCalories == 500) {
        allCombinations.add(combination);
      }
      return;
    }
    int numLeft = 100 - numUsed;
    for (int i = 0; i <= numLeft; i++) {
      List<Integer> newCombination = new ArrayList<>(combination);
      newCombination.add(i);
      generateAllCombinationsPart2(newCombination, numIngredients, numUsed + i, allCombinations);
    }
  }

  private int computeNumCalories(List<Integer> combination) {
    int numCalories = 0;
    for (int i = 0; i < this.ingredients.size(); i++) {
      numCalories += combination.get(i) * this.ingredients.get(i).calories();
    }
    return numCalories;
  }
}
