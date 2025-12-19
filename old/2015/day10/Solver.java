package leo.aoc.year2015.day10;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  public Solver(String input) {
    super(input);
  }

  @Override
  public String solvePart1() {
    return Integer.toString(iterate(input, 40).length());
  }

  @Override
  public String solvePart2() {
    return Integer.toString(iterate(input, 50).length());
  }

  private String iterate(String input, int n) {
    return Stream.iterate(input, this::computeNext).limit(n + 1).reduce((_, s) -> s).orElse(input);
  }

  private String computeNext(String s) {
    StringBuilder sb = new StringBuilder();
    for (String seq : split(s)) {
      sb.append(seq.length());
      sb.append(seq.charAt(0));
    }
    return sb.toString();
  }

  private List<String> split(String s) {
    List<String> sequences = new ArrayList<>();
    int i = 0;
    while (i < s.length()) {
      char currentChar = s.charAt(i);
      StringBuilder sequence = new StringBuilder();
      while (i < s.length() && s.charAt(i) == currentChar) {
        sequence.append(s.charAt(i));
        i++;
      }
      sequences.add(sequence.toString());
    }
    return sequences;
  }
}
