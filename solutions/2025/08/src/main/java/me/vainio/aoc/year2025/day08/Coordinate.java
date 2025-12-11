package me.vainio.aoc.year2025.day08;

public record Coordinate(int x, int y, int z) {
    public double distanceTo(Coordinate other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
