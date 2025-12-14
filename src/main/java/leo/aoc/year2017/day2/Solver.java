package leo.aoc.year2017.day2;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private final IFn solvePart1Fn;
  private final IFn solvePart2Fn;

  public Solver(String input) {
    super(input);

    Clojure.var("clojure.core", "require").invoke(Clojure.read("leo.aoc.year2017.day2.solver"));

    solvePart1Fn = (IFn) Clojure.var("leo.aoc.year2017.day2.solver", "solve-part1");
    solvePart2Fn = (IFn) Clojure.var("leo.aoc.year2017.day2.solver", "solve-part2");
  }

  @Override
  public String solvePart1() {
    return (String) solvePart1Fn.invoke(input);
  }

  @Override
  public String solvePart2() {
    return (String) solvePart2Fn.invoke(input);
  }
}
