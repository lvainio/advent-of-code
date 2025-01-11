package com.example.day11_2024;

import java.util.HashMap;
import java.util.List;

public class Day11 {

    private record CacheKey(long stone, int blinksLeft) {}

    public static void main( String[] args ) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");
        List<Long> stones = parser.getStones();

        HashMap<CacheKey, Long> cache = new HashMap<>();
        long part1 = stones.stream()
            .reduce(0L, (sum, stone) -> sum + numStonesAfterBlinks(stone, 25, cache));
        long part2 = stones.stream()
            .reduce(0L, (sum, stone) -> sum + numStonesAfterBlinks(stone, 75, cache));

        System.out.println("Part1: " + part1);
        System.out.println("Part2: " + part2);
    }

    public static long numStonesAfterBlinks(long stone, int numBlinksLeft, HashMap<CacheKey, Long> cache) {
        CacheKey key = new CacheKey(stone, numBlinksLeft);
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else if (numBlinksLeft == 0) {
            return 1;
        } else if (stone == 0) {
            long count = numStonesAfterBlinks(1L, numBlinksLeft-1, cache);
            cache.put(key, count);
            return count;
        } else if (String.valueOf(stone).length() % 2 == 0) {
            String numStr = String.valueOf(stone);
            int mid = numStr.length() / 2;
            long firstHalf = Long.parseLong(numStr.substring(0, mid));
            long secondHalf = Long.parseLong(numStr.substring(mid));
            long firstCount = numStonesAfterBlinks(firstHalf, numBlinksLeft-1, cache);
            long secondCount = numStonesAfterBlinks(secondHalf, numBlinksLeft-1, cache);
            cache.put(key, firstCount+secondCount);
            return firstCount + secondCount;
        } else {
            long count = numStonesAfterBlinks(stone*2024, numBlinksLeft-1, cache);
            cache.put(key, count);
            return count;
        }
    }
}
