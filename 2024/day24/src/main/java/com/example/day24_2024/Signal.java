package com.example.day24_2024;

public enum Signal {
    ZERO, 
    ONE,
    NONE;

    public Signal or(Signal other) {
        if (this == NONE || other == NONE) {
            throw new IllegalStateException("Invalid operation: Cannot perform OR with NONE signal.");
        }
        return (this == ONE || other == ONE) ? ONE : ZERO;
    }

    public Signal and(Signal other) {
        if (this == NONE || other == NONE) {
            throw new IllegalStateException("Invalid operation: Cannot perform AND with NONE signal.");
        }
        return (this == ONE && other == ONE) ? ONE : ZERO;
    }

    public Signal xor(Signal other) {
        if (this == NONE || other == NONE) {
            throw new IllegalStateException("Invalid operation: Cannot perform XOR with NONE signal.");
        }
        return (this != other) ? ONE : ZERO;
    }

    public static Signal fromInt(int value) {
        return switch (value) {
            case 0 -> ZERO;
            case 1 -> ONE;
            default -> throw new IllegalArgumentException("Invalid value: " + value + ". Expected 0 or 1.");
        };
    }

    @Override
    public String toString() {
        switch (this) {
            case ONE:
                return "1";
            case ZERO:
                return "0";
            case NONE:
                return "NONE";
            default:
                throw new IllegalStateException("Invalid instance of Signal");
        }
    }
}
