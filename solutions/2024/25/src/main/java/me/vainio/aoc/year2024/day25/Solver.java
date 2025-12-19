package me.vainio.aoc.year2024.day25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 25;

  private record Key(int[] key) {}

  private record Lock(int[] lock) {
    private static final int NUM_ROWS = 6;
    private static final int NUM_COLS = 5;

    public boolean fitsKey(Key key) {
      return IntStream.range(0, NUM_COLS)
          .map(i -> this.lock[i] + key.key()[i])
          .allMatch(sum -> sum <= NUM_ROWS);
    }
  }

  private final List<Key> keys;
  private final List<Lock> locks;

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
    List<Key> keys = new ArrayList<>();
    List<Lock> locks = new ArrayList<>();
    Arrays.stream(input.split("\r?\n\r?\n"))
        .forEach(
            keyOrLock -> {
              int[] colCounts = getColCounts(keyOrLock);
              if (isLock(keyOrLock)) {
                locks.add(new Lock(colCounts));
              } else {
                keys.add(new Key(colCounts));
              }
            });
    this.keys = keys;
    this.locks = locks;
  }

  public String solvePart1() {
    long count = locks.stream().flatMap(lock -> keys.stream().filter(lock::fitsKey)).count();
    return Long.toString(count);
  }

  public String solvePart2() {
    // No part 2 for day 25
    return "";
  }

  private boolean isLock(String keyOrLock) {
    return keyOrLock.split("\r?\n")[0].trim().equals("#####");
  }

  private int[] getColCounts(String keyOrLock) {
    int[] colCounts = new int[5];
    String[] grid = keyOrLock.split("\r?\n");
    for (int row = 1; row <= 6; row++) {
      for (int col = 0; col <= 4; col++) {
        if (grid[row].charAt(col) == '#') {
          colCounts[col]++;
        }
      }
    }
    return colCounts;
  }
}
