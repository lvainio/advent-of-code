package leo.aoc.year2015.day13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private HashMap<String, HashMap<String, Integer>> happinessMap;

  public Solver(String input) {
    super(input);

    HashMap<String, HashMap<String, Integer>> happinessMap = new HashMap<>();
    input
        .lines()
        .forEach(
            line -> {
              String[] parts = line.substring(0, line.length() - 1).split("\\s+");
              String name1 = parts[0];
              String name2 = parts[10];
              String gainOrLose = parts[2];
              int amount = Integer.parseInt(parts[3]);
              happinessMap.putIfAbsent(name1, new HashMap<>());
              if (gainOrLose.equals("gain")) {
                happinessMap.get(name1).put(name2, amount);
              } else {
                happinessMap.get(name1).put(name2, -amount);
              }
            });
    this.happinessMap = happinessMap;
  }

  @Override
  public String solvePart1() {
    Set<String> people = this.happinessMap.keySet();

    List<List<String>> allArrangements = new ArrayList<>();
    generateAllArrangements(new ArrayList<>(), people, allArrangements);

    int maxHappiness =
        allArrangements.stream().mapToInt(this::computeChange).max().orElse(Integer.MAX_VALUE);

    return Integer.toString(maxHappiness);
  }

  @Override
  public String solvePart2() {
    Set<String> people = this.happinessMap.keySet();
    this.happinessMap.put("me", new HashMap<>());
    for (String person : people) {
      this.happinessMap.get(person).put("me", 0);
      this.happinessMap.get("me").put(person, 0);
    }
    people = new HashSet<>(people);
    people.add("me");

    List<List<String>> allArrangements = new ArrayList<>();
    generateAllArrangements(new ArrayList<>(), people, allArrangements);

    int maxHappiness =
        allArrangements.stream().mapToInt(this::computeChange).max().orElse(Integer.MAX_VALUE);

    return Integer.toString(maxHappiness);
  }

  private int computeChange(List<String> seating) {
    int total = 0;
    for (int i = 0; i < seating.size(); i++) {
      String name1 = seating.get(i);
      String name2 = seating.get((i + 1) % seating.size());
      total += this.happinessMap.get(name1).get(name2);
      total += this.happinessMap.get(name2).get(name1);
    }
    return total;
  }

  public void generateAllArrangements(
      List<String> seating, Set<String> peopleLeft, List<List<String>> allArrangements) {
    if (peopleLeft.size() == 0) {
      allArrangements.add(seating);
    }
    for (String person : peopleLeft) {
      List<String> newSeating = new ArrayList<>(seating);
      newSeating.add(person);
      Set<String> newPeopleLeft = new HashSet<>(peopleLeft);
      newPeopleLeft.remove(person);
      generateAllArrangements(newSeating, newPeopleLeft, allArrangements);
    }
  }
}
