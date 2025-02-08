package com.example.day02_2015;

import java.util.stream.IntStream;

public record Present(int length, int width, int height) {

    public int wrappingPaperNeeded() {
        int side1 = length * width;
        int side2 = length * height;
        int side3 = width * height;
        int minSide = IntStream.of(side1, side2, side3).min().getAsInt();
        return (side1 * 2) + (side2 * 2) + (side3 * 2) + minSide;
    }

    public int ribbonNeeded() {
        int[] sortedSides = IntStream.of(length, width, height)
                             .sorted()
                             .toArray();
        int minSide = sortedSides[0];
        int midSide = sortedSides[1];
        int bow = length * width * height;
        return (minSide * 2) + (midSide * 2) + bow;
    }
}
