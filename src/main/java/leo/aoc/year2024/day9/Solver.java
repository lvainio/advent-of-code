package leo.aoc.year2024.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private static final int FREE_SPACE = -1;

  private List<Integer> memory;

  public Solver(String input) {
    super(input);

    List<Integer> digits = input.chars().map(Character::getNumericValue).boxed().toList();

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

  @Override
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

  @Override
  public String solvePart2() {
    List<Integer> memoryCopy = new ArrayList<>(memory);
    for (int i = memoryCopy.size() - 1; i >= 0; i--) {
      int id = memoryCopy.get(i);
      int fileSize = 1;
      while (i - 1 >= 0 && memoryCopy.get(i - 1) == id) {
        fileSize++;
        i--;
      }
      int firstFreeSpace = findFreeSpace(memoryCopy, fileSize, i);
      for (int j = i; j < i + fileSize; j++) {
        memoryCopy.set(j, -1);
      }
      for (int j = firstFreeSpace; j < firstFreeSpace + fileSize; j++) {
        memoryCopy.set(j, id);
      }
    }
    return Long.toString(calculateChecksum(memoryCopy));
  }

  private static long calculateChecksum(List<Integer> memory) {
    return IntStream.range(0, memory.size())
        .mapToLong(i -> memory.get(i) == -1 ? 0 : (long) i * memory.get(i))
        .sum();
  }

  private static int findFreeSpace(List<Integer> memory, int fileSize, int fileIndex) {
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
