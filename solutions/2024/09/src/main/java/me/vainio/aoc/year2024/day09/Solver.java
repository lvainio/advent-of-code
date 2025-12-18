package me.vainio.aoc.year2024.day09;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 9;

  private static final int FREE_SPACE = -1;

  private final List<Integer> memory;

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
    final List<Integer> digits = input.chars().map(Character::getNumericValue).boxed().toList();
    List<Integer> memory = new ArrayList<>();
    for (int i = 0; i < digits.size(); i++) {
      if (i % 2 == 0) {
        for (int j = 0; j < digits.get(i); j++) {
          memory.add(i / 2);
        }
      } else {
        for (int j = 0; j < digits.get(i); j++) {
          memory.add(-1);
        }
      }
    }
    this.memory = memory;
  }

  public String solvePart1() {
    List<Integer> memoryCopy = new ArrayList<>(memory);
    int pointer = 0;
    for (int i = memoryCopy.size() - 1; i >= 0; i--) {
      if (pointer > i) {
        break;
      }
      if (memoryCopy.get(i) != FREE_SPACE) {
        while (memoryCopy.get(pointer) != FREE_SPACE) {
          pointer++;
        }
        if (pointer < i) {
          memoryCopy.set(pointer, memoryCopy.get(i));
          memoryCopy.set(i, FREE_SPACE);
        }
      }
    }
    return Long.toString(calculateChecksum(memoryCopy));
  }

  public String solvePart2() {
    List<Integer> memoryCopy = new ArrayList<>(memory);
    for (int i = memoryCopy.size() - 1; i >= 0; i--) {
      final int id = memoryCopy.get(i);
      int fileSize = 1;
      while (i - 1 >= 0 && memoryCopy.get(i - 1) == id) {
        fileSize++;
        i--;
      }
      final int firstFreeSpace = findFreeSpace(memoryCopy, fileSize, i);
      for (int j = i; j < i + fileSize; j++) {
        memoryCopy.set(j, -1);
      }
      for (int j = firstFreeSpace; j < firstFreeSpace + fileSize; j++) {
        memoryCopy.set(j, id);
      }
    }
    return Long.toString(calculateChecksum(memoryCopy));
  }

  private static long calculateChecksum(final List<Integer> memory) {
    return IntStream.range(0, memory.size())
        .mapToLong(i -> memory.get(i) == -1 ? 0 : (long) i * memory.get(i))
        .sum();
  }

  private static int findFreeSpace(
      final List<Integer> memory, final int fileSize, final int fileIndex) {
    int count = 0;
    for (int i = 0; i < fileIndex; i++) {
      if (memory.get(i) != -1) {
        count = 0;
        continue;
      }
      count++;
      if (count == fileSize) {
        return i - fileSize + 1;
      }
    }
    return fileIndex;
  }
}
