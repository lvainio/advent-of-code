package com.example.day20_2024;

public enum Cell {
    WALL,
    EMPTY,
    START,
    END;

    public static Cell charToCell(char c) {
        return switch (c) {
            case '#' -> WALL;
            case '.' -> EMPTY;
            case 'S' -> START;
            case 'E' -> END;
            default -> throw new IllegalArgumentException("Unknown character: " + c);
        };
    }
}
