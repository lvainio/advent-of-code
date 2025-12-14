package leo.aoc.year2024.day19;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private List<String> patterns = null;
  private List<String> designs = null;

  public Solver(String input) {
    super(input);

    String[] PatternsAndDesigns = input.split("\r?\n\r?\n");

    this.patterns = Arrays.asList(PatternsAndDesigns[0].split(", "));
    this.designs = Arrays.asList(PatternsAndDesigns[1].split("\r?\n"));
  }

  @Override
  public String solvePart1() {
    long numValidDesigns = designs.stream().filter(s -> isValidDesign(s, patterns)).count();
    return Long.toString(numValidDesigns);
  }

  @Override
  public String solvePart2() {
    HashMap<String, Long> mem = new HashMap<>();
    long numArrangements =
        designs.stream().mapToLong(s -> countArrangements(s, patterns, mem)).sum();
    return Long.toString(numArrangements);
  }

  private boolean isValidDesign(String design, List<String> patterns) {
    if (design.length() == 0) {
      return true;
    }
    for (String pattern : patterns) {
      if (design.startsWith(pattern)
          && isValidDesign(design.substring(pattern.length()), patterns)) {
        return true;
      }
    }
    return false;
  }

  private long countArrangements(
      String design, List<String> patterns, HashMap<String, Long> designToCount) {
    if (design.length() == 0) {
      return 1;
    }
    if (designToCount.containsKey(design)) {
      return designToCount.get(design);
    }
    long count = 0;
    for (String pattern : patterns) {
      if (design.startsWith(pattern)) {
        count += countArrangements(design.substring(pattern.length()), patterns, designToCount);
      }
    }
    designToCount.put(design, count);
    return count;
  }
}
