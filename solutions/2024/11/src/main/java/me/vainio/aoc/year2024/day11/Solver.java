package me.vainio.aoc.year2024.day11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 11;

  private record CacheKey(long stone, int blinksLeft) {}

  private final List<Long> stones;

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
    this.stones = Arrays.stream(input.split(" ")).map(Long::parseLong).toList();
  }

  public String solvePart1() {
    final HashMap<CacheKey, Long> cache = new HashMap<>();
    final long numStones =
        stones.stream().reduce(0L, (sum, stone) -> sum + numStonesAfterBlinks(stone, 25, cache));
    return Long.toString(numStones);
  }

  public String solvePart2() {
    final HashMap<CacheKey, Long> cache = new HashMap<>();
    final long numStones =
        stones.stream().reduce(0L, (sum, stone) -> sum + numStonesAfterBlinks(stone, 75, cache));
    return Long.toString(numStones);
  }

  private long numStonesAfterBlinks(
      final long stone, final int numBlinksLeft, final HashMap<CacheKey, Long> cache) {
    final CacheKey key = new CacheKey(stone, numBlinksLeft);
    if (cache.containsKey(key)) {
      return cache.get(key);

    } else if (numBlinksLeft == 0) {
      return 1;

    } else if (stone == 0) {
      final long count = numStonesAfterBlinks(1L, numBlinksLeft - 1, cache);
      cache.put(key, count);
      return count;

    } else if (String.valueOf(stone).length() % 2 == 0) {
      final String numStr = String.valueOf(stone);
      final int mid = numStr.length() / 2;
      final long firstHalf = Long.parseLong(numStr.substring(0, mid));
      final long secondHalf = Long.parseLong(numStr.substring(mid));
      final long firstCount = numStonesAfterBlinks(firstHalf, numBlinksLeft - 1, cache);
      final long secondCount = numStonesAfterBlinks(secondHalf, numBlinksLeft - 1, cache);
      cache.put(key, firstCount + secondCount);
      return firstCount + secondCount;

    } else {
      final long count = numStonesAfterBlinks(stone * 2024, numBlinksLeft - 1, cache);
      cache.put(key, count);
      return count;
    }
  }
}
