package leo.aoc.year2015.day20;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private final int limit;

  public Solver(String input) {
    super(input);
    this.limit = Integer.parseInt(input);
  }

  @Override
  public String solvePart1() {
    int house = 1;
    while (getNumPresents(house) < this.limit) {
      house++;
    }
    return Integer.toString(house);
  }

  @Override
  public String solvePart2() {
    int house = 1;
    while (getNumPresentsPart2(house) < this.limit) {
      house++;
    }
    return Integer.toString(house);
  }

  private int getNumPresents(int house) {
    int sum = 0;
    int maxDivisor = (int) Math.sqrt(house);

    for (int i = 1; i <= maxDivisor; i++) {
      if (house % i == 0) {
        sum += i * 10;
        if (i != house / i) {
          sum += (house / i) * 10;
        }
      }
    }
    return sum;
  }

  private int getNumPresentsPart2(int house) {
    int sum = 0;
    int maxDivisor = (int) Math.sqrt(house);

    for (int i = 1; i <= maxDivisor; i++) {
      if (house % i == 0) {
        if (house / i <= 50) {
          sum += i * 11;
        }
        if (i != house / i && house / (house / i) <= 50) {
          sum += (house / i) * 11;
        }
      }
    }
    return sum;
  }
}
