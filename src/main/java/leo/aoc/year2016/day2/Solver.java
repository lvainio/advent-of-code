package leo.aoc.year2016.day2;

import java.util.List;

import leo.aoc.AbstractSolver;
import leo.aoc.util.Direction;

public class Solver extends AbstractSolver {

    private static final int[][] KEYPAD = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9},
    };

    private static final char[][] KEYPAD2 = {
        {'0', '0', '1', '0', '0'},
        {'0', '2', '3', '4', '0'},
        {'5', '6', '7', '8', '9'},
        {'0', 'A', 'B', 'C', '0'},
        {'0', '0', 'D', '0', '0'},
    };

    private static int START_ROW = 1;
    private static int START_COL = 1;

    private static int START_ROW2 = 2;
    private static int START_COL2 = 0;

    private List<String> instructions;

    public Solver(String input) {
        super(input);
        this.instructions = input.lines().toList();
    }

    @Override
    public String solvePart1() {
        int row = START_ROW;
        int col = START_COL;
        StringBuilder code = new StringBuilder();
        for (String instruction : this.instructions) {
            for (Character move : instruction.toCharArray()) {
                Direction direction = switch (move) {
                    case 'U' -> Direction.NORTH;
                    case 'D' -> Direction.SOUTH;
                    case 'L' -> Direction.WEST;
                    case 'R' -> Direction.EAST;
                    default -> throw new IllegalArgumentException("Invalid move!");
                };
                int newRow = row + direction.getDr();
                int newCol = col + direction.getDc();
                if (isInBounds(newRow, newCol)) {
                    row = newRow;
                    col = newCol;
                }
            }
            code.append(KEYPAD[row][col]);
        }
        return code.toString();
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 
            && row < KEYPAD.length
            && col >= 0
            && col < KEYPAD[0].length;
    }

    @Override
    public String solvePart2() {
        int row = START_ROW2;
        int col = START_COL2;
        StringBuilder code = new StringBuilder();
        for (String instruction : this.instructions) {
            for (Character move : instruction.toCharArray()) {
                Direction direction = switch (move) {
                    case 'U' -> Direction.NORTH;
                    case 'D' -> Direction.SOUTH;
                    case 'L' -> Direction.WEST;
                    case 'R' -> Direction.EAST;
                    default -> throw new IllegalArgumentException("Invalid move!");
                };
                int newRow = row + direction.getDr();
                int newCol = col + direction.getDc();
                if (isValidKey(newRow, newCol)) {
                    row = newRow;
                    col = newCol;
                }
            }
            code.append(KEYPAD2[row][col]);
        }
        return code.toString();
    }

    private boolean isValidKey(int row, int col) {
        return row >= 0 
            && row < KEYPAD2.length
            && col >= 0
            && col < KEYPAD2[0].length
            && KEYPAD2[row][col] != '0';
    }
}
