package me.vainio.aoc.year2025.day08;

import java.util.Comparator;

public record EdgeWithDistance (Coordinate c1, Coordinate c2, double distance) {
    public static final Comparator<EdgeWithDistance> BY_DISTANCE =
            Comparator.comparingDouble(EdgeWithDistance::distance);

    public EdgeWithDistance(Coordinate c1, Coordinate c2) {
        this(c1, c2, c1.distanceTo(c2));
    }
}
