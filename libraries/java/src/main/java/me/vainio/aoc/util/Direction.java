package me.vainio.aoc.util;

import java.util.Set;

public enum Direction {
  EAST(0),
  NORTHEAST(45),
  NORTH(90),
  NORTHWEST(135),
  WEST(180),
  SOUTHWEST(225),
  SOUTH(270),
  SOUTHEAST(315);

  private final int angle;

  Direction(final int angle) {
    this.angle = angle;
  }

  private static Direction fromAngle(final int angle) {
    for (Direction dir : Direction.values()) {
      if (dir.angle == angle) {
        return dir;
      }
    }
    throw new IllegalArgumentException("Invalid angle: " + angle);
  }

  /**
   * Turns the current direction to the right by the specified number of degrees.
   *
   * <p>This method rotates the direction by the given degrees in a clockwise direction, or
   * counterclockwise if degrees is negative. The rotation is restricted to multiples of 45°.
   *
   * <p>For example: - `turnRight(90)` turns the direction 90 degrees clockwise (e.g., from EAST to
   * SOUTH). - `turnRight(360)` returns the same direction (360° is a full rotation). -
   * `turnRight(450)` behaves as if you had rotated 90° (since 450 % 360 = 90). - `turnRight(-90)`
   * turns the direction 90 degrees counterclockwise (e.g., from EAST to NORTH).
   *
   * @param degrees degrees The number of degrees to rotate the direction (must be a multiple of
   *     45°).
   * @return The new direction after turning by the specified degrees.
   * @throws IllegalArgumentException if the angle is not a multiple of 45°.
   */
  public Direction turnRight(final int degrees) {
    if (degrees % 45 != 0) {
      throw new IllegalArgumentException("Invalid angle, degrees must be a multiple of 45");
    }
    int newAngle = (this.angle - degrees) % 360;
    if (newAngle < 0) {
      newAngle += 360;
    }
    return Direction.fromAngle(newAngle);
  }

  /**
   * Turns the current direction to the left by the specified number of degrees.
   *
   * <p>This method rotates the direction by the given degrees in a counterclockwise direction, or
   * clockwise if degrees is negative. The rotation is restricted to multiples of 45°.
   *
   * <p>For example: - `turnLeft(90)` turns the direction 90 degrees counterclockwise (e.g., from
   * SOUTH to EAST). - `turnLeft(360)` returns the same direction (360° is a full rotation). -
   * `turnLeft(450)` behaves as if you had rotated 90° (since 450 % 360 = 90). - `turnLeft(-90)`
   * turns the direction 90 degrees clockwise (e.g., from NORTH to EAST).
   *
   * @param degrees degrees The number of degrees to rotate the direction (must be a multiple of
   *     45°).
   * @return The new direction after turning by the specified degrees.
   * @throws IllegalArgumentException if the angle is not a multiple of 45°.
   */
  public Direction turnLeft(final int degrees) {
    return turnRight(-degrees);
  }

  /**
   * Returns the change in the x-coordinate for the direction.
   *
   * @return The change in the x-coordinate.
   */
  public int getDx() {
    return switch (this) {
      case EAST, NORTHEAST, SOUTHEAST -> 1;
      case WEST, NORTHWEST, SOUTHWEST -> -1;
      case NORTH, SOUTH -> 0;
    };
  }

  /**
   * Returns the change in the y-coordinate for the direction.
   *
   * @return The change in the y-coordinate.
   */
  public int getDy() {
    return switch (this) {
      case NORTH, NORTHEAST, NORTHWEST -> 1;
      case SOUTH, SOUTHEAST, SOUTHWEST -> -1;
      case EAST, WEST -> 0;
    };
  }

  /**
   * Returns the change in the row index for the direction.
   *
   * @return The change in the row index.
   */
  public int getDr() {
    return switch (this) {
      case NORTH, NORTHEAST, NORTHWEST -> -1;
      case SOUTH, SOUTHEAST, SOUTHWEST -> 1;
      case EAST, WEST -> 0;
    };
  }

  /**
   * Returns the change in the column index for the direction.
   *
   * @return The change in the column index.
   */
  public int getDc() {
    return getDx();
  }

  /**
   * Returns a list of the four cardinal directions.
   *
   * @return an immutable list containing the cardinal directions.
   */
  public static Set<Direction> getCardinalDirections() {
    return Set.of(EAST, NORTH, WEST, SOUTH);
  }
}
