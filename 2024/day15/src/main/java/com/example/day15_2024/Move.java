package com.example.day15_2024;

public enum Move {
    UP('^'),
    DOWN('v'),
    LEFT('<'),
    RIGHT('>');

    private final char symbol;

    Move(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return this.symbol;
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
