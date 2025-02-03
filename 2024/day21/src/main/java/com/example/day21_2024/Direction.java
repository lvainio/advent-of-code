package com.example.day21_2024;

public enum Direction {
    NORTH(-1, 0, '^'),
    SOUTH(1, 0, 'v'),
    WEST(0, -1, '<'),
    EAST(0, 1, '>');

    private final int dr;
    private final int dc;
    private final char symbol;

    private Direction(int dr, int dc, char symbol) {
        this.dr = dr;
        this.dc = dc;
        this.symbol = symbol;
    }

    public int getDr() {
        return this.dr;
    }

    public int getDc() {
        return this.dc;
    }

    public char getSymbol() {
        return this.symbol;
    }
}