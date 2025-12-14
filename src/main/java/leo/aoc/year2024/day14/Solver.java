package leo.aoc.year2024.day14;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private List<Robot> robots;

  public Solver(String input) {
    super(input);

    Pattern pattern = Pattern.compile("-?\\d+");
    this.robots =
        input
            .lines()
            .map(
                s -> {
                  Matcher matcher = pattern.matcher(s);
                  int x = this.getNext(matcher);
                  int y = this.getNext(matcher);
                  int vx = this.getNext(matcher);
                  int vy = this.getNext(matcher);
                  return new Robot(x, y, vx, vy);
                })
            .collect(Collectors.toList());
  }

  private int getNext(Matcher matcher) {
    matcher.find();
    return Integer.parseInt(matcher.group());
  }

  @Override
  public String solvePart1() {
    final int width = 101;
    final int height = 103;
    IntStream.range(0, 100)
        .forEach(
            _ -> {
              robots.forEach(robot -> robot.step(width, height));
            });
    int[] counts = new int[4];
    robots.forEach(
        robot -> {
          if (robot.isInNwQuadrant(width, height)) {
            counts[0]++;
          } else if (robot.isInNeQuadrant(width, height)) {
            counts[1]++;
          } else if (robot.isInSwQuadrant(width, height)) {
            counts[2]++;
          } else if (robot.isInSeQuadrant(width, height)) {
            counts[3]++;
          }
        });
    return Integer.toString(counts[0] * counts[1] * counts[2] * counts[3]);
  }

  @Override
  public String solvePart2() {
    final int width = 101;
    final int height = 103;
    for (int sec = 100; sec < 1_000_000; sec++) {
      char[][] grid = robotsToGrid(robots, width, height);
      if (isXmasTree(grid)) {
        return Integer.toString(sec);
      }
      for (Robot robot : robots) {
        robot.step(width, height);
      }
    }
    throw new IllegalStateException("No XMAS tree found!");
  }

  public static char[][] robotsToGrid(List<Robot> robots, int width, int height) {
    char[][] grid = new char[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        grid[row][col] = '.';
      }
    }
    for (Robot robot : robots) {
      grid[robot.getY()][robot.getX()] = '#';
    }
    return grid;
  }

  public static boolean isXmasTree(char[][] grid) {
    for (int row = 0; row < grid.length; row++) {
      String rowStr = new String(grid[row]);
      int sub = longestHashSubstring(rowStr);
      if (sub > 10) {
        return true;
      }
    }
    return false;
  }

  public static int longestHashSubstring(String rowStr) {
    int maxCount = 0, currentCount = 0;
    for (char c : rowStr.toCharArray()) {
      if (c == '#') {
        currentCount++;
        maxCount = Math.max(maxCount, currentCount);
      } else {
        currentCount = 0;
      }
    }
    return maxCount;
  }

  private class Robot {
    private int x;
    private int y;

    private final int vx;
    private final int vy;

    public Robot(int x, int y, int vx, int vy) {
      this.x = x;
      this.y = y;
      this.vx = vx;
      this.vy = vy;
    }

    public void step(int width, int height) {
      this.x = (this.x + this.vx + width) % width;
      this.y = (this.y + this.vy + height) % height;
    }

    public int getX() {
      return this.x;
    }

    public int getY() {
      return this.y;
    }

    public boolean isInNwQuadrant(int width, int height) {
      int xMid = width / 2;
      int yMid = height / 2;
      return this.x < xMid && this.y < yMid;
    }

    public boolean isInNeQuadrant(int width, int height) {
      int xMid = width / 2;
      int yMid = height / 2;
      return this.x > xMid && this.y < yMid;
    }

    public boolean isInSwQuadrant(int width, int height) {
      int xMid = width / 2;
      int yMid = height / 2;
      return this.x < xMid && this.y > yMid;
    }

    public boolean isInSeQuadrant(int width, int height) {
      int xMid = width / 2;
      int yMid = height / 2;
      return this.x > xMid && this.y > yMid;
    }
  }
}
