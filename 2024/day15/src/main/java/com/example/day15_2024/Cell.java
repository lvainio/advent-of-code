package com.example.day15_2024;

enum Cell {
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

    public char getSymbol() {
        return this.symbol;
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
