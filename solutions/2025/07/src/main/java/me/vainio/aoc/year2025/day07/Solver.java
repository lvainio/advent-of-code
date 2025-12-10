package me.vainio.aoc.year2025.day07;

import me.vainio.aoc.cache.AocCache;
import me.vainio.aoc.util.Grid;
import me.vainio.aoc.util.Location;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Solver {
    private static final int YEAR = 2025;
    private static final int DAY = 7;

    private static final char START = 'S';
    private static final char SPLIT = '^';

    private final Grid<Character> grid;
    private final Location startLocation;

    public static void main(final String[] args) {
        final AocCache cache = new AocCache();

        final String input = cache.getInput(YEAR, DAY);
        final Solver solver = new Solver(input);

        final String part1 = solver.solvePart1();
        final String part2 = solver.solvePart2();

        System.out.println(part1);
        System.out.println(part2);

        cache.saveAnswer(YEAR, DAY, 1, part1);
        cache.saveAnswer(YEAR, DAY, 2, part2);
    }

    public Solver(final String input) {
        this.grid = Grid.ofChars(input);
        this.startLocation = grid.findFirst(START).orElseThrow();
    }

    public String solvePart1() {
        int count = 0;
        Queue<Location> queue = new LinkedList<>();
        Set<Location> visited = new HashSet<>();
        queue.offer(startLocation);
        while (!queue.isEmpty()) {
            Location current = queue.poll();
            if (!grid.isInBounds(current) || visited.contains(current)) {
                continue;
            }
            if (grid.get(current).equals(SPLIT)) {
                count++;
                queue.offer(new Location(current.row(), current.col() - 1));
                queue.offer(new Location(current.row(), current.col() + 1));
            } else {
                queue.offer(new Location(current.row() + 1, current.col()));
            }
            visited.add(current);
        }
        return String.valueOf(count);
    }

    public String solvePart2() {
        int count = 0;
        Queue<Location> queue = new LinkedList<>();
        queue.offer(startLocation);
        while (!queue.isEmpty()) {
            Location current = queue.poll();
            if (!grid.isInBounds(current)) {
                count++;
                continue;
            }
            if (grid.get(current).equals(SPLIT)) {
                queue.offer(new Location(current.row(), current.col() - 1));
                queue.offer(new Location(current.row(), current.col() + 1));
            } else {
                queue.offer(new Location(current.row() + 1, current.col()));
            }
        }

        // FIXME: we need a dynamic programming solution here I believe
        // a simple dfs with memoization should work

        return String.valueOf(count);
    }
}
