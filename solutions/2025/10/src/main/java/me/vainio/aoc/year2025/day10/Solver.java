package me.vainio.aoc.year2025.day10;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2025;
  private static final int DAY = 10;

  private static final Pattern NUM_PATTERN = Pattern.compile("\\d+");
  private static final char LIGHT_ON = '#';
  private static final char LIGHT_OFF = '.';

  static {
    Loader.loadNativeLibraries();
  }

  private record Machine(
      List<Character> pattern, Set<Set<Integer>> buttons, List<Integer> joltageRequirements) {}

  private record State(List<Character> list, int depth) {}

  private final List<Machine> machines;

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
    this.machines =
        input
            .lines()
            .map(
                line -> {
                  final List<String> parts = Arrays.stream(line.split(" ")).toList();
                  final List<Character> pattern =
                      parts
                          .getFirst()
                          .substring(1, parts.getFirst().length() - 1)
                          .chars()
                          .mapToObj(i -> (char) i)
                          .toList();
                  final Set<Set<Integer>> buttons =
                      parts.subList(1, parts.size() - 1).stream()
                          .map(
                              nums -> {
                                Matcher m = NUM_PATTERN.matcher(nums);
                                return m.results()
                                    .map(match -> Integer.parseInt(match.group()))
                                    .collect(Collectors.toSet());
                              })
                          .collect(Collectors.toSet());
                  final List<Integer> joltageRequirements =
                      NUM_PATTERN
                          .matcher(parts.getLast())
                          .results()
                          .map(m -> Integer.parseInt(m.group()))
                          .toList();
                  return new Machine(pattern, buttons, joltageRequirements);
                })
            .toList();
  }

  public String solvePart1() {
    int count = 0;
    for (Machine machine : machines) {
      final List<Character> lights =
          new ArrayList<>(Collections.nCopies(machine.pattern().size(), LIGHT_OFF));
      final State startState = new State(lights, 0);
      Queue<State> queue = new LinkedList<>(List.of(startState));
      Set<List<Character>> visited = new java.util.HashSet<>();
      visited.add(startState.list());
      int depthForMachine = 0;
      while (!queue.isEmpty()) {
        final State current = queue.poll();
        if (current.list().equals(machine.pattern())) {
          depthForMachine = current.depth();
          break;
        }
        for (Set<Integer> button : machine.buttons()) {
          final List<Character> newLights = new ArrayList<>(current.list());
          for (Integer index : button) {
            newLights.set(index, newLights.get(index) == LIGHT_OFF ? LIGHT_ON : LIGHT_OFF);
          }
          if (visited.add(newLights)) {
            queue.add(new State(newLights, current.depth() + 1));
          }
        }
      }
      count += depthForMachine;
    }
    return String.valueOf(count);
  }

  public String solvePart2() {
    long totalPresses = 0;
    for (Machine machine : machines) {
      final List<Integer> target = machine.joltageRequirements();
      final List<Set<Integer>> buttons = new ArrayList<>(machine.buttons());

      final MPSolver solver = MPSolver.createSolver("CBC");

      final MPVariable[] x = new MPVariable[buttons.size()];
      for (int button = 0; button < buttons.size(); button++) {
        x[button] = solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "x_" + button);
      }

      for (int counter = 0; counter < target.size(); counter++) {
        final MPConstraint constraint =
            solver.makeConstraint(target.get(counter), target.get(counter), "c_" + counter);
        for (int button = 0; button < buttons.size(); button++) {
          if (buttons.get(button).contains(counter)) {
            constraint.setCoefficient(x[button], 1.0);
          }
        }
      }

      final MPObjective objective = solver.objective();
      for (int button = 0; button < buttons.size(); button++) {
        objective.setCoefficient(x[button], 1.0);
      }
      objective.setMinimization();

      MPSolver.ResultStatus status = solver.solve();
      if (status != MPSolver.ResultStatus.OPTIMAL) {
        throw new IllegalStateException("Solving failed for machine " + machine);
      }

      totalPresses += Math.round(objective.value());
    }
    return String.valueOf(totalPresses);
  }
}
