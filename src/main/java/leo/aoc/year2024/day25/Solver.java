package leo.aoc.year2024.day25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private record Key(int[] key) {}

    private record Lock(int[] lock) {
        private static int NUM_ROWS = 6;
        private static int NUM_COLS = 5;

        public boolean fitsKey(Key key) {
            return IntStream.range(0, NUM_COLS)
                    .map(i -> this.lock[i] + key.key()[i])
                    .allMatch(sum -> sum <= NUM_ROWS);
        }
    }

    private List<Key> keys;
    private List<Lock> locks;

    public Solver(String input) {
        super(input);
        
        List<Key> keys = new ArrayList<>();
        List<Lock> locks = new ArrayList<>();
        Arrays.stream(input.split("\r?\n\r?\n")).forEach(keyOrLock -> {
            int[] colCounts = getColCounts(keyOrLock);
            if (isLock(keyOrLock)) {
                locks.add(new Lock(colCounts));
            } else {
                keys.add(new Key(colCounts));
            }
        });
        this.keys = keys;
        this.locks = locks;
    }

    private boolean isLock(String keyOrLock) {
        return keyOrLock.split("\r?\n")[0].trim().equals("#####");
    }

    private int[] getColCounts(String keyOrLock) {
        int[] colCounts = new int[5];
        String[] grid = keyOrLock.split("\r?\n");
        for (int row = 1; row <= 6; row++) {
            for (int col = 0; col <= 4; col++) {
                if (grid[row].charAt(col) == '#') {
                    colCounts[col]++;
                }
            }
        }
        return colCounts;
    }

    @Override
    public String solvePart1() {
        long count = locks.stream()
            .flatMap(lock -> keys.stream().filter(lock::fitsKey)) 
            .count();
        return Long.toString(count);
    }

    @Override
    public String solvePart2() {
        return "Day 25 has no part2";
    }
}
