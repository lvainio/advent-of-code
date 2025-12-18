package me.vainio.aoc.year2024.day15;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 15;

  private final Cell[][] map1;
  private final Cell[][] map2;
  private final List<Move> moves;

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
    String[] mapAndMoves = input.lines().collect(Collectors.joining("\n")).split("\r?\n\r?\n");

    this.map1 =
        Arrays.stream(mapAndMoves[0].split("\r?\n"))
            .map(line -> line.chars().mapToObj(c -> Cell.fromChar((char) c)).toArray(Cell[]::new))
            .toArray(Cell[][]::new);

    this.moves =
        mapAndMoves[1]
            .replaceAll("\\s+", "")
            .chars()
            .mapToObj(c -> Move.fromChar((char) c))
            .collect(Collectors.toList());

    this.map2 = new Cell[this.map1.length][this.map1[0].length * 2];
    for (int row = 0; row < this.map1.length; row++) {
      for (int col = 0; col < this.map1[0].length; col++) {
        switch (this.map1[row][col]) {
          case WALL -> {
            this.map2[row][col * 2] = Cell.WALL;
            this.map2[row][col * 2 + 1] = Cell.WALL;
          }
          case EMPTY -> {
            this.map2[row][col * 2] = Cell.EMPTY;
            this.map2[row][col * 2 + 1] = Cell.EMPTY;
          }
          case BOX -> {
            this.map2[row][col * 2] = Cell.BOXLEFT;
            this.map2[row][col * 2 + 1] = Cell.BOXRIGHT;
          }
          case ROBOT -> {
            this.map2[row][col * 2] = Cell.ROBOT;
            this.map2[row][col * 2 + 1] = Cell.EMPTY;
          }
          default -> throw new IllegalArgumentException("Invalid cell");
        }
      }
    }
  }

  public String solvePart1() {
    Warehouse warehouse1 = new Warehouse(map1);
    warehouse1.makeMoves(moves);
    return Integer.toString(warehouse1.countScore());
  }

  public String solvePart2() {
    Warehouse warehouse2 = new Warehouse(map2);
    warehouse2.makeMovesPart2(moves);
    return Integer.toString(warehouse2.countScorePart2());
  }

  private enum Cell {
    WALL('#'),
    BOX('O'),
    BOXLEFT('['),
    BOXRIGHT(']'),
    EMPTY('.'),
    ROBOT('@');

    private final char symbol;

    Cell(char symbol) {
      this.symbol = symbol;
    }

    public static Cell fromChar(char c) {
      for (Cell cell : values()) {
        if (cell.symbol == c) {
          return cell;
        }
      }
      throw new IllegalArgumentException("Invalid character: " + c);
    }
  }

  private enum Move {
    UP('^'),
    DOWN('v'),
    LEFT('<'),
    RIGHT('>');

    private final char symbol;

    Move(char symbol) {
      this.symbol = symbol;
    }

    public static Move fromChar(char c) {
      for (Move move : values()) {
        if (move.symbol == c) {
          return move;
        }
      }
      throw new IllegalArgumentException("Invalid character: " + c);
    }
  }

  private static class Warehouse {

    private record Point(int row, int col) {}

    private final Cell[][] map;

    private int robotRow;
    private int robotCol;

    public Warehouse(Cell[][] map) {
      for (int row = 0; row < map.length; row++) {
        for (int col = 0; col < map[0].length; col++) {
          if (map[row][col] == Cell.ROBOT) {
            this.robotRow = row;
            this.robotCol = col;
          }
        }
      }
      this.map = map;
    }

    public void makeMoves(List<Move> moves) {
      moves.forEach(this::move);
    }

    public void makeMovesPart2(List<Move> moves) {
      moves.forEach(this::movePart2);
    }

    public int countScore() {
      int total = 0;
      for (int row = 0; row < this.map.length; row++) {
        for (int col = 0; col < this.map[0].length; col++) {
          if (this.map[row][col] == Cell.BOX) {
            total += row * 100 + col;
          }
        }
      }
      return total;
    }

    public int countScorePart2() {
      int total = 0;
      for (int row = 0; row < this.map.length; row++) {
        for (int col = 0; col < this.map[0].length; col++) {
          if (this.map[row][col] == Cell.BOXLEFT) {
            total += row * 100 + col;
          }
        }
      }
      return total;
    }

    public void move(Move move) {
      switch (move) {
        case LEFT -> moveLeft();
        case RIGHT -> moveRight();
        case UP -> moveUp();
        case DOWN -> moveDown();
        default -> throw new IllegalArgumentException("Invalid move: " + move);
      }
    }

    public void movePart2(Move move) {
      switch (move) {
        case LEFT -> moveLeftPart2();
        case RIGHT -> moveRightPart2();
        case UP -> moveUpPart2();
        case DOWN -> moveDownPart2();
        default -> throw new IllegalArgumentException("Invalid move: " + move);
      }
    }

    private void moveLeftPart2() {
      int row = this.robotRow;
      int col = this.robotCol;

      boolean canMove = canMovePart2(row, col - 1, Move.LEFT);
      if (canMove) {
        int currentCol = col - 1;
        while (this.map[row][currentCol] == Cell.BOXLEFT
            || this.map[row][currentCol] == Cell.BOXRIGHT) {
          currentCol--;
        }
        for (int c = currentCol; c < col; c++) {
          this.map[row][c] = this.map[row][c + 1];
        }
        this.map[row][col] = Cell.EMPTY;
        this.robotCol--;
      }
    }

    private void moveRightPart2() {
      int row = this.robotRow;
      int col = this.robotCol;

      boolean canMove = canMovePart2(row, col + 1, Move.RIGHT);
      if (canMove) {
        int currentCol = col + 1;
        while (this.map[row][currentCol] == Cell.BOXLEFT
            || this.map[row][currentCol] == Cell.BOXRIGHT) {
          currentCol++;
        }
        for (int c = currentCol; c > col; c--) {
          this.map[row][c] = this.map[row][c - 1];
        }
        this.map[row][col] = Cell.EMPTY;
        this.robotCol++;
      }
    }

    private void moveUpPart2() {
      int row = this.robotRow;
      int col = this.robotCol;

      boolean canMove = canMovePart2(row - 1, col, Move.UP);

      if (canMove) {
        moveUpPart2Helper(row, col, new HashSet<>());
        this.robotRow--;
        this.map[row][col] = Cell.EMPTY;
        this.map[this.robotRow][this.robotCol] = Cell.ROBOT;
      }
    }

    private void moveUpPart2Helper(int row, int col, HashSet<Point> visited) {
      if (this.map[row][col] == Cell.EMPTY) {
        return;
      }
      if (visited.contains(new Point(row, col))) {
        return;
      }
      visited.add(new Point(row, col));

      int newRow = row - 1;
      if (this.map[newRow][col] == Cell.BOXLEFT) {
        moveUpPart2Helper(newRow, col, visited);
        moveUpPart2Helper(newRow, col + 1, visited);
      } else if (this.map[newRow][col] == Cell.BOXRIGHT) {
        moveUpPart2Helper(newRow, col, visited);
        moveUpPart2Helper(newRow, col - 1, visited);
      }

      this.map[newRow][col] = this.map[row][col];
      this.map[row][col] = Cell.EMPTY;

      visited.add(new Point(newRow, col));
    }

    private void moveDownPart2() {
      int row = this.robotRow;
      int col = this.robotCol;

      boolean canMove = canMovePart2(row + 1, col, Move.DOWN);

      if (canMove) {
        moveDownPart2Helper(row, col, new HashSet<>());
        this.robotRow++;
        this.map[row][col] = Cell.EMPTY;
        this.map[this.robotRow][this.robotCol] = Cell.ROBOT;
      }
    }

    private void moveDownPart2Helper(int row, int col, HashSet<Point> visited) {
      if (this.map[row][col] == Cell.EMPTY) {
        return;
      }
      if (visited.contains(new Point(row, col))) {
        return;
      }
      visited.add(new Point(row, col));

      int newRow = row + 1;
      if (this.map[newRow][col] == Cell.BOXLEFT) {
        moveDownPart2Helper(newRow, col, visited);
        moveDownPart2Helper(newRow, col + 1, visited);
      } else if (this.map[newRow][col] == Cell.BOXRIGHT) {
        moveDownPart2Helper(newRow, col, visited);
        moveDownPart2Helper(newRow, col - 1, visited);
      }

      this.map[newRow][col] = this.map[row][col];
      this.map[row][col] = Cell.EMPTY;

      visited.add(new Point(newRow, col));
    }

    private boolean canMovePart2(int row, int col, Move move) {
      if (this.map[row][col] == Cell.WALL) {
        return false;
      } else if (this.map[row][col] == Cell.EMPTY) {
        return true;
      }

      // box
      return switch (move) {
        case LEFT -> {
          int newCol = col - 2;
          yield canMovePart2(row, newCol, move);
        }
        case RIGHT -> {
          int newCol = col + 2;
          yield canMovePart2(row, newCol, move);
        }
        case UP -> {
          int newRow = row - 1;
          if (this.map[row][col] == Cell.BOXLEFT) {
            yield canMovePart2(newRow, col, move) && canMovePart2(newRow, col + 1, move);
          } else {
            yield canMovePart2(newRow, col, move) && canMovePart2(newRow, col - 1, move);
          }
        }
        case DOWN -> {
          int newRow = row + 1;
          if (this.map[row][col] == Cell.BOXLEFT) {
            yield canMovePart2(newRow, col, move) && canMovePart2(newRow, col + 1, move);
          } else {
            yield canMovePart2(newRow, col, move) && canMovePart2(newRow, col - 1, move);
          }
        }
        default -> true;
      };
    }

    private void moveLeft() {
      int row = this.robotRow;
      int col = this.robotCol;
      Cell leftCell = this.map[row][col - 1];

      switch (leftCell) {
        case WALL -> {
          return;
        }
        case EMPTY -> {
          this.map[row][col] = Cell.EMPTY;
          this.robotCol--;
          this.map[this.robotRow][this.robotCol] = Cell.ROBOT;
        }
        case BOX -> {
          boolean canMove = false;
          int currentCol = col - 1;
          while (currentCol >= 0) {
            if (this.map[row][currentCol] == Cell.WALL) {
              break;
            }
            if (this.map[row][currentCol] == Cell.EMPTY) {
              canMove = true;
              break;
            }
            currentCol--;
          }
          if (canMove) {
            for (int c = currentCol; c < col; c++) {
              this.map[row][c] = this.map[row][c + 1];
            }
            this.map[row][col] = Cell.EMPTY;
            this.robotCol--;
          }
        }
        default -> throw new IllegalArgumentException("Invalid cell: " + leftCell);
      }
    }

    private void moveRight() {
      int row = this.robotRow;
      int col = this.robotCol;
      Cell rightCell = this.map[row][col + 1];

      switch (rightCell) {
        case WALL -> {
          return;
        }
        case EMPTY -> {
          this.map[row][col] = Cell.EMPTY;
          this.robotCol++;
          this.map[this.robotRow][this.robotCol] = Cell.ROBOT;
        }
        case BOX -> {
          boolean canMove = false;
          int currentCol = col + 1;
          while (currentCol < this.map[0].length) {
            if (this.map[row][currentCol] == Cell.WALL) {
              break;
            }
            if (this.map[row][currentCol] == Cell.EMPTY) {
              canMove = true;
              break;
            }
            currentCol++;
          }
          if (canMove) {
            for (int c = currentCol; c > col; c--) {
              this.map[row][c] = this.map[row][c - 1];
            }
            this.map[row][col] = Cell.EMPTY;
            this.robotCol++;
          }
        }
        default -> throw new IllegalArgumentException("Invalid cell: " + rightCell);
      }
    }

    private void moveUp() {
      int row = this.robotRow;
      int col = this.robotCol;
      Cell aboveCell = this.map[row - 1][col];

      switch (aboveCell) {
        case WALL -> {
          return;
        }
        case EMPTY -> {
          this.map[row][col] = Cell.EMPTY;
          this.robotRow--;
          this.map[this.robotRow][this.robotCol] = Cell.ROBOT;
        }
        case BOX -> {
          boolean canMove = false;
          int currentRow = row - 1;
          while (currentRow >= 0) {
            if (this.map[currentRow][col] == Cell.WALL) {
              break;
            }
            if (this.map[currentRow][col] == Cell.EMPTY) {
              canMove = true;
              break;
            }
            currentRow--;
          }
          if (canMove) {
            for (int r = currentRow; r < row; r++) {
              this.map[r][col] = this.map[r + 1][col];
            }
            this.map[row][col] = Cell.EMPTY;
            this.robotRow--;
          }
        }
        default -> throw new IllegalArgumentException("Invalid cell: " + aboveCell);
      }
    }

    private void moveDown() {
      int row = this.robotRow;
      int col = this.robotCol;
      Cell belowCell = this.map[row + 1][col];

      switch (belowCell) {
        case WALL -> {
          return;
        }
        case EMPTY -> {
          this.map[row][col] = Cell.EMPTY;
          this.robotRow++;
          this.map[this.robotRow][this.robotCol] = Cell.ROBOT;
        }
        case BOX -> {
          boolean canMove = false;
          int currentRow = row + 1;
          while (currentRow < this.map.length) {
            if (this.map[currentRow][col] == Cell.WALL) {
              break;
            }
            if (this.map[currentRow][col] == Cell.EMPTY) {
              canMove = true;
              break;
            }
            currentRow++;
          }
          if (canMove) {
            for (int r = currentRow; r > row; r--) {
              this.map[r][col] = this.map[r - 1][col];
            }
            this.map[row][col] = Cell.EMPTY;
            this.robotRow++;
          }
        }
        default -> throw new IllegalArgumentException("Invalid cell: " + belowCell);
      }
    }
  }
}
