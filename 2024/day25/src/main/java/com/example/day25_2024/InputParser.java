package com.example.day25_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputParser {

    private List<Key> keys;
    private List<Lock> locks;

    public List<Key> getKeys() {
        return this.keys;
    }

    public List<Lock> getLocks() {
        return this.locks;
    }

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<Key> keys = new ArrayList<>();
            List<Lock> locks = new ArrayList<>();
            Arrays.stream(reader.lines().collect(Collectors.joining("\n")).split("\r?\n\r?\n")).forEach(keyOrLock -> {
                int[] colCounts = getColCounts(keyOrLock);
                if (isLock(keyOrLock)) {
                    locks.add(new Lock(colCounts));
                } else {
                    keys.add(new Key(colCounts));
                }
            });
            this.keys = keys;
            this.locks = locks;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
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
}
