package me.vainio.aoc.year2025.day09;

import java.util.ArrayList;
import java.util.List;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2025;
  private static final int DAY = 9;

  private record Location(long row, long col) {}

  private record Line(Location start, Location end) {}

  private record Rectangle(Location loc1, Location loc2) {
    public long area() {
      return (Math.abs(loc1.row - loc2.row) + 1) * (Math.abs(loc1.col - loc2.col) + 1);
    }

    public boolean intersects(final Line line) {
      final long rectMinRow = Math.min(loc1.row, loc2.row);
      final long rectMaxRow = Math.max(loc1.row, loc2.row);
      final long rectMinCol = Math.min(loc1.col, loc2.col);
      final long rectMaxCol = Math.max(loc1.col, loc2.col);

      final long lineMinRow = Math.min(line.start.row, line.end.row);
      final long lineMaxRow = Math.max(line.start.row, line.end.row);
      final long lineMinCol = Math.min(line.start.col, line.end.col);
      final long lineMaxCol = Math.max(line.start.col, line.end.col);

      if (line.start.col == line.end.col) {
        return line.start.col > rectMinCol
            && line.start.col < rectMaxCol
            && lineMaxRow > rectMinRow
            && lineMinRow < rectMaxRow;
      } else {
        return line.start.row > rectMinRow
            && line.start.row < rectMaxRow
            && lineMaxCol > rectMinCol
            && lineMinCol < rectMaxCol;
      }
    }
  }

  private final List<Location> locations;

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
    this.locations =
        input
            .lines()
            .map(
                line -> {
                  String[] parts = line.split(",");
                  return new Location(Long.parseLong(parts[1]), Long.parseLong(parts[0]));
                })
            .toList();
  }

  public String solvePart1() {
    long maxArea = 0;
    for (int i = 0; i < locations.size(); i++) {
      for (int j = i + 1; j < locations.size(); j++) {
        final Rectangle rect = new Rectangle(locations.get(i), locations.get(j));
        maxArea = Math.max(maxArea, rect.area());
      }
    }
    return String.valueOf(maxArea);
  }

  public String solvePart2() {
    List<Line> lines = new ArrayList<>();
    for (int i = 0; i < locations.size() - 1; i++) {
      lines.add(new Line(locations.get(i), locations.get(i + 1)));
    }
    lines.add(new Line(locations.getLast(), locations.getFirst()));
    long maxArea = 0;
    for (int i = 0; i < locations.size(); i++) {
      for (int j = i + 1; j < locations.size(); j++) {
        final Rectangle rect = new Rectangle(locations.get(i), locations.get(j));
        if (lines.stream().noneMatch(rect::intersects)) {
          maxArea = Math.max(maxArea, rect.area());
        }
      }
    }
    return String.valueOf(maxArea);
  }
}
