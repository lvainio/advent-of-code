package com.example.day20_2024;

public enum Direction {
    NORTH(-1, 0),
    SOUTH(1, 0),
    WEST(0, -1),
    EAST(0, 1);

    private final int dr;
    private final int dc;

    private Direction(int dr, int dc) {
        this.dr = dr;
        this.dc = dc;
    }

    public int getDr() {
        return this.dr;
    }

    public int getDc() {
        return this.dc;
    }
}
