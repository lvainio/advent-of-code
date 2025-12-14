package leo.aoc.year2015.day6;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private enum Light {
    ON,
    OFF
  }

  private enum Button {
    ON,
    OFF,
    TOGGLE
  }

  private record Point(int row, int col) {}

  private record Instruction(Button b, Point p1, Point p2) {}

  private Light[][] grid;

  private int[][] gridPart2;

  private List<Instruction> instructions;

  public Solver(String input) {
    super(input);

    this.grid = new Light[1000][1000];
    for (int row = 0; row < 1000; row++) {
      for (int col = 0; col < 1000; col++) {
        this.grid[row][col] = Light.OFF;
      }
    }
    this.gridPart2 = new int[1000][1000];

    Pattern pattern = Pattern.compile("\\d+");
    this.instructions =
        input
            .lines()
            .map(
                line -> {
                  Button button = null;
                  if (line.contains("toggle")) {
                    button = Button.TOGGLE;
                  } else if (line.contains("off")) {
                    button = Button.OFF;
                  } else {
                    button = Button.ON;
                  }

                  Matcher matcher = pattern.matcher(line);
                  matcher.find();
                  int row1 = Integer.parseInt(matcher.group());
                  matcher.find();
                  int col1 = Integer.parseInt(matcher.group());
                  matcher.find();
                  int row2 = Integer.parseInt(matcher.group());
                  matcher.find();
                  int col2 = Integer.parseInt(matcher.group());

                  Point p1 = new Point(row1, col1);
                  Point p2 = new Point(row2, col2);

                  return new Instruction(button, p1, p2);
                })
            .toList();
  }

  @Override
  public String solvePart1() {
    for (Instruction instruction : this.instructions) {
      Button button = instruction.b();
      switch (button) {
        case ON -> pressOn(instruction.p1, instruction.p2);
        case OFF -> pressOff(instruction.p1, instruction.p2);
        case TOGGLE -> toggle(instruction.p1, instruction.p2);
      }
    }
    int count = 0;
    for (int row = 0; row < 1000; row++) {
      for (int col = 0; col < 1000; col++) {
        if (this.grid[row][col] == Light.ON) {
          count++;
        }
      }
    }
    return Integer.toString(count);
  }

  @Override
  public String solvePart2() {
    for (Instruction instruction : this.instructions) {
      Button button = instruction.b();
      switch (button) {
        case ON -> pressOn2(instruction.p1, instruction.p2);
        case OFF -> pressOff2(instruction.p1, instruction.p2);
        case TOGGLE -> toggle2(instruction.p1, instruction.p2);
      }
    }
    long count = 0;
    for (int row = 0; row < 1000; row++) {
      for (int col = 0; col < 1000; col++) {
        count += this.gridPart2[row][col];
      }
    }
    return Long.toString(count);
  }

  private void pressOn(Point p1, Point p2) {
    int startRow = Math.min(p1.row(), p2.row());
    int endRow = Math.max(p1.row(), p2.row());
    int startCol = Math.min(p1.col(), p2.col());
    int endCol = Math.max(p1.col(), p2.col());
    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {
        this.grid[row][col] = Light.ON;
      }
    }
  }

  private void pressOff(Point p1, Point p2) {
    int startRow = Math.min(p1.row(), p2.row());
    int endRow = Math.max(p1.row(), p2.row());
    int startCol = Math.min(p1.col(), p2.col());
    int endCol = Math.max(p1.col(), p2.col());
    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {
        this.grid[row][col] = Light.OFF;
      }
    }
  }

  private void toggle(Point p1, Point p2) {
    int startRow = Math.min(p1.row(), p2.row());
    int endRow = Math.max(p1.row(), p2.row());
    int startCol = Math.min(p1.col(), p2.col());
    int endCol = Math.max(p1.col(), p2.col());
    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {
        if (this.grid[row][col] == Light.ON) {
          this.grid[row][col] = Light.OFF;
        } else {
          this.grid[row][col] = Light.ON;
        }
      }
    }
  }

  private void pressOn2(Point p1, Point p2) {
    int startRow = Math.min(p1.row(), p2.row());
    int endRow = Math.max(p1.row(), p2.row());
    int startCol = Math.min(p1.col(), p2.col());
    int endCol = Math.max(p1.col(), p2.col());
    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {
        this.gridPart2[row][col]++;
      }
    }
  }

  private void pressOff2(Point p1, Point p2) {
    int startRow = Math.min(p1.row(), p2.row());
    int endRow = Math.max(p1.row(), p2.row());
    int startCol = Math.min(p1.col(), p2.col());
    int endCol = Math.max(p1.col(), p2.col());
    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {
        if (this.gridPart2[row][col] > 0) {
          this.gridPart2[row][col]--;
        }
      }
    }
  }

  private void toggle2(Point p1, Point p2) {
    int startRow = Math.min(p1.row(), p2.row());
    int endRow = Math.max(p1.row(), p2.row());
    int startCol = Math.min(p1.col(), p2.col());
    int endCol = Math.max(p1.col(), p2.col());
    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {
        this.gridPart2[row][col] += 2;
      }
    }
  }
}
