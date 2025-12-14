package leo.aoc.year2024.day11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private record CacheKey(long stone, int blinksLeft) {}

  private final List<Long> stones;

  public Solver(String input) {
    super(input);

    this.stones = Arrays.stream(input.split(" ")).map(Long::parseLong).toList();
  }

  @Override
  public String solvePart1() {
    HashMap<CacheKey, Long> cache = new HashMap<>();
    long numStones =
        stones.stream().reduce(0L, (sum, stone) -> sum + numStonesAfterBlinks(stone, 25, cache));
    return Long.toString(numStones);
  }

  @Override
  public String solvePart2() {
    HashMap<CacheKey, Long> cache = new HashMap<>();
    long numStones =
        stones.stream().reduce(0L, (sum, stone) -> sum + numStonesAfterBlinks(stone, 75, cache));
    return Long.toString(numStones);
  }

  private long numStonesAfterBlinks(long stone, int numBlinksLeft, HashMap<CacheKey, Long> cache) {
    CacheKey key = new CacheKey(stone, numBlinksLeft);
    if (cache.containsKey(key)) {
      return cache.get(key);
    } else if (numBlinksLeft == 0) {
      return 1;
    } else if (stone == 0) {
      long count = numStonesAfterBlinks(1L, numBlinksLeft - 1, cache);
      cache.put(key, count);
      return count;
    } else if (String.valueOf(stone).length() % 2 == 0) {
      String numStr = String.valueOf(stone);
      int mid = numStr.length() / 2;
      long firstHalf = Long.parseLong(numStr.substring(0, mid));
      long secondHalf = Long.parseLong(numStr.substring(mid));
      long firstCount = numStonesAfterBlinks(firstHalf, numBlinksLeft - 1, cache);
      long secondCount = numStonesAfterBlinks(secondHalf, numBlinksLeft - 1, cache);
      cache.put(key, firstCount + secondCount);
      return firstCount + secondCount;
    } else {
      long count = numStonesAfterBlinks(stone * 2024, numBlinksLeft - 1, cache);
      cache.put(key, count);
      return count;
    }
  }
}
