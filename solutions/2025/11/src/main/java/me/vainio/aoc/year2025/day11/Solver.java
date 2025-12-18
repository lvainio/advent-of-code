package me.vainio.aoc.year2025.day11;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2025;
  private static final int DAY = 11;

  private static final String YOU = "you";
  private static final String SVR = "svr";
  private static final String OUT = "out";
  private static final String DAC = "dac";
  private static final String FFT = "fft";

  private final Map<String, Set<String>> dag;

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
    this.dag =
        input
            .lines()
            .map(line -> line.split(": "))
            .collect(Collectors.toMap(parts -> parts[0], parts -> Set.of(parts[1].split(" "))));
  }

  public String solvePart1() {
    return String.valueOf(dfs(YOU, OUT, new HashMap<>(), Set.of()));
  }

  public String solvePart2() {
    long svrToDac = dfs(SVR, DAC, new HashMap<>(), Set.of(FFT, OUT));
    long svrToFft = dfs(SVR, FFT, new HashMap<>(), Set.of(DAC, OUT));
    long dacToFft = dfs(DAC, FFT, new HashMap<>(), Set.of(SVR, OUT));
    long fftToDac = dfs(FFT, DAC, new HashMap<>(), Set.of(SVR, OUT));
    long dacToOut = dfs(DAC, OUT, new HashMap<>(), Set.of(SVR, FFT));
    long fftToOut = dfs(FFT, OUT, new HashMap<>(), Set.of(SVR, DAC));

    long numPaths = svrToDac * dacToFft * fftToOut + svrToFft * fftToDac * dacToOut;
    return String.valueOf(numPaths);
  }

  private long dfs(
      final String node, final String goal, Map<String, Long> memo, Set<String> exclude) {
    if (node.equals(goal)) {
      return 1;
    }
    if (exclude.contains(node)) {
      return 0;
    }
    if (memo.containsKey(node)) {
      return memo.get(node);
    }

    long paths = 0;
    for (String neighbor : dag.get(node)) {
      paths += dfs(neighbor, goal, memo, exclude);
    }

    memo.put(node, paths);
    return paths;
  }
}
