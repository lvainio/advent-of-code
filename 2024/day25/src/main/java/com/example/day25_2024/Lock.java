package com.example.day25_2024;

import java.util.stream.IntStream;

public record Lock(int[] lock) {
    private static int NUM_ROWS = 6;
    private static int NUM_COLS = 5;

    public boolean fitsKey(Key key) {
        return IntStream.range(0, NUM_COLS)
                .map(i -> this.lock[i] + key.key()[i])
                .allMatch(sum -> sum <= NUM_ROWS);
    }
}
