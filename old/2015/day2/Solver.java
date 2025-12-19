package leo.aoc.year2015.day2;

import java.util.List;
import java.util.stream.IntStream;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private record Present(int length, int width, int height) {
    public int wrappingPaperNeeded() {
      int side1 = length * width;
      int side2 = length * height;
      int side3 = width * height;
      int minSide = IntStream.of(side1, side2, side3).min().getAsInt();
      return (side1 * 2) + (side2 * 2) + (side3 * 2) + minSide;
    }

    public int ribbonNeeded() {
      int[] sortedSides = IntStream.of(length, width, height).sorted().toArray();
      int minSide = sortedSides[0];
      int midSide = sortedSides[1];
      int bow = length * width * height;
      return (minSide * 2) + (midSide * 2) + bow;
    }
  }

  private List<Present> presents;

  public Solver(String input) {
    super(input);

    this.presents =
        input
            .lines()
            .map(
                line -> {
                  String[] nums = line.split("x");
                  int value1 = Integer.parseInt(nums[0]);
                  int value2 = Integer.parseInt(nums[1]);
                  int value3 = Integer.parseInt(nums[2]);
                  return new Present(value1, value2, value3);
                })
            .toList();
  }

  @Override
  public String solvePart1() {
    return Integer.toString(presents.stream().mapToInt(Present::wrappingPaperNeeded).sum());
  }

  @Override
  public String solvePart2() {
    return Integer.toString(presents.stream().mapToInt(Present::ribbonNeeded).sum());
  }
}
