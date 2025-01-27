package com.example.day15_2024;

import java.util.List;

public class Warehouse {

    private Cell[][] map;

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
        moves.forEach(m -> move(m));
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

    public void move(Move move) {
        switch (move) {
            case LEFT -> moveLeft();
            case RIGHT -> moveRight();
            case UP -> moveUp();
            case DOWN -> moveDown();
            default -> throw new IllegalArgumentException("Invalid move: " + move);
        }
    }

    private void moveLeft() {
        int row = this.robotRow;
        int col = this.robotCol;
        Cell leftCell = this.map[row][col-1];

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
                int currentCol = col-1;
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
                        this.map[row][c] = this.map[row][c+1];
                    }
                    this.map[row][col] = Cell.EMPTY;
                    this.robotCol--;
                }
            }
            case ROBOT -> {
                throw new IllegalArgumentException("Can not exist two robots at the same time!");
            }
            default -> throw new IllegalArgumentException("Invalid cell: " + leftCell);   
        }
    }

    private void moveRight() {
        int row = this.robotRow;
        int col = this.robotCol;
        Cell rightCell = this.map[row][col+1];

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
                int currentCol = col+1;
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
                        this.map[row][c] = this.map[row][c-1];
                    }
                    this.map[row][col] = Cell.EMPTY;
                    this.robotCol++;
                }
            }
            case ROBOT -> {
                throw new IllegalArgumentException("Can not exist two robots at the same time!");
            }
            default -> throw new IllegalArgumentException("Invalid cell: " + rightCell);   
        }
    }

    private void moveUp() {
        int row = this.robotRow;
        int col = this.robotCol;
        Cell aboveCell = this.map[row-1][col];

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
                int currentRow = row-1;
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
                        this.map[r][col] = this.map[r+1][col];
                    }
                    this.map[row][col] = Cell.EMPTY;
                    this.robotRow--;
                }
            }
            case ROBOT -> {
                throw new IllegalArgumentException("Can not exist two robots at the same time!");
            }
            default -> throw new IllegalArgumentException("Invalid cell: " + aboveCell);   
        }
    }

    private void moveDown() {
        int row = this.robotRow;
        int col = this.robotCol;
        Cell belowCell = this.map[row+1][col];

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
                int currentRow = row+1;
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
                        this.map[r][col] = this.map[r-1][col];
                    }
                    this.map[row][col] = Cell.EMPTY;
                    this.robotRow++;
                }
            }
            case ROBOT -> {
                throw new IllegalArgumentException("Can not exist two robots at the same time!");
            }
            default -> throw new IllegalArgumentException("Invalid cell: " + belowCell);   
        }
    }
}
