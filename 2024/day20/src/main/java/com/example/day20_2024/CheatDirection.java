package com.example.day20_2024;

public enum CheatDirection {
    NORTH(-2, 0),
    SOUTH(2, 0),
    WEST(0, -2),
    EAST(0, 2),
    NORTH_WEST(-1, -1),
    NORTH_EAST(-1, 1),
    SOUTH_WEST(1, -1),
    SOUTH_EAST(1, 1);
    
    private final int dr;
    private final int dc;

    private CheatDirection(int dr, int dc) {
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
