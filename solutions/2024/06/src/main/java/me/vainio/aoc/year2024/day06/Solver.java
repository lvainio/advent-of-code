package me.vainio.aoc.year2024.day06;

import java.util.HashSet;
import java.util.Set;
import me.vainio.aoc.cache.AocCache;
import me.vainio.aoc.util.Direction;
import me.vainio.aoc.util.Grid;
import me.vainio.aoc.util.Location;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 6;

  private static final char STARTING_POINT = '^';
  private static final char EMPTY_SPACE = '.';
  private static final char OBSTACLE = '#';

  private record LocationAndDirection(Location p, Direction dir) {}

  private final Grid<Character> grid;

  private final Location start;

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
    this.grid = Grid.ofChars(input);
    this.start = grid.findFirst(STARTING_POINT).orElseThrow();
  }

  public String solvePart1() {
    Direction currentDirection = Direction.NORTH;
    Location currentLocation = this.start;
    Set<Location> visited = new HashSet<>();
    while (grid.isInBounds(currentLocation)) {
      visited.add(currentLocation);
      while (nextIsObstacle(currentLocation, currentDirection)) {
        currentDirection = currentDirection.turnRight(90);
      }
      final int newRow = currentLocation.row() + currentDirection.getDr();
      final int newCol = currentLocation.col() + currentDirection.getDc();
      currentLocation = new Location(newRow, newCol);
    }
    return String.valueOf(visited.size());
  }

  public String solvePart2() {
    int count = 0;
    for (int row = 0; row < this.grid.numRows(); row++) {
      for (int col = 0; col < this.grid.numCols(); col++) {
        if (this.grid.get(row, col) == EMPTY_SPACE && !this.start.equals(new Location(row, col))) {
          Direction currentDirection = Direction.NORTH;
          Location currentLocation = this.start;
          HashSet<LocationAndDirection> visited = new HashSet<>();
          this.grid.set(row, col, OBSTACLE);
          while (grid.isInBounds(currentLocation)) {
            LocationAndDirection pad = new LocationAndDirection(currentLocation, currentDirection);
            if (visited.contains(pad)) {
              count++;
              break;
            }
            visited.add(pad);
            while (nextIsObstacle(currentLocation, currentDirection)) {
              currentDirection = currentDirection.turnRight(90);
            }
            int newRow = currentLocation.row() + currentDirection.getDr();
            int newCol = currentLocation.col() + currentDirection.getDc();
            currentLocation = new Location(newRow, newCol);
          }
          this.grid.set(row, col, EMPTY_SPACE);
        }
      }
    }
    return String.valueOf(count);
  }

  private boolean nextIsObstacle(Location loc, Direction dir) {
    int newRow = loc.row() + dir.getDr();
    int newCol = loc.col() + dir.getDc();
    Location newLocation = new Location(newRow, newCol);
    return grid.isInBounds(newLocation) && grid.get(newLocation) == OBSTACLE;
  }
}
