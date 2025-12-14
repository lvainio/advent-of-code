package me.vainio.aoc.year2025.day05;

record Range(long start, long end) {
  boolean contains(long value) {
    return value >= start && value <= end;
  }

  long size() {
    return end - start + 1;
  }
}
