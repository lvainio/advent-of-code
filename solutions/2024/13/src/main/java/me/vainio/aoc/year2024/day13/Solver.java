package me.vainio.aoc.year2024.day13;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 13;

  private record ClawMachine(long ax, long ay, long bx, long by, long px, long py) {
    public long calculateCheapestWin() {
      double aPresses =
          (double) (this.by * this.px - this.bx * this.py)
              / (this.ax * this.by - this.ay * this.bx);
      double bPresses = (this.px - aPresses * this.ax) / this.bx;
      if (aPresses >= 0.0 && bPresses >= 0.0 && aPresses % 1.0 == 0.0 && bPresses % 1 == 0.0) {
        return (long) (aPresses * 3.0 + bPresses);
      }
      return 0;
    }

    public ClawMachine addLongToPrizeCoordinates(long num) {
      return new ClawMachine(this.ax, this.ay, this.bx, this.by, this.px + num, this.py + num);
    }
  }

  private final List<ClawMachine> clawMachines;

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
    List<String> lines = input.lines().toList();
    clawMachines =
        IntStream.iterate(0, idx -> idx < lines.size(), idx -> idx + 4)
            .mapToObj(
                idx -> {
                  Pattern pattern = Pattern.compile("\\d+");
                  Matcher matcherButtonA = pattern.matcher(lines.get(idx));
                  int ax = this.getNext(matcherButtonA);
                  int ay = this.getNext(matcherButtonA);
                  Matcher matcherButtonB = pattern.matcher(lines.get(idx + 1));
                  int bx = this.getNext(matcherButtonB);
                  int by = this.getNext(matcherButtonB);
                  Matcher matcherPrice = pattern.matcher(lines.get(idx + 2));
                  int px = this.getNext(matcherPrice);
                  int py = this.getNext(matcherPrice);
                  return new ClawMachine(ax, ay, bx, by, px, py);
                })
            .collect(Collectors.toList());
  }

  private int getNext(Matcher matcher) {
    if (!matcher.find()) {
      throw new IllegalStateException("No more matches found");
    }
    return Integer.parseInt(matcher.group());
  }

  public String solvePart1() {
    long sum = this.clawMachines.stream().mapToLong(ClawMachine::calculateCheapestWin).sum();
    return Long.toString(sum);
  }

  public String solvePart2() {
    long sum =
        this.clawMachines.stream()
            .map(machine -> machine.addLongToPrizeCoordinates(10000000000000L))
            .mapToLong(ClawMachine::calculateCheapestWin)
            .sum();
    return Long.toString(sum);
  }
}
