package com.example.day9_2024;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.io.FileNotFoundException;

public class Main {

    private static final int FREE_SPACE = -1;

    public static void main(String[] args) {
        final String inputFile = "input.txt";
        InputParser parser = new InputParser();
        try {
            parser.parseInputFile(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        List<Integer> memory = parser.getMemory();

        List<Integer> memoryPart1 = new ArrayList<>(memory);
        int pointer = 0;
        for (int i = memoryPart1.size() - 1; i >= 0; i--) {
            if (pointer > i) {
                break;
            }

            if (memoryPart1.get(i) != FREE_SPACE) {
                while (memoryPart1.get(pointer) != FREE_SPACE) {
                    pointer++;
                }
                if (pointer < i) {
                    memoryPart1.set(pointer, memoryPart1.get(i));
                    memoryPart1.set(i, FREE_SPACE);
                } 
            }
        }

        List<Integer> memoryPart2 = new ArrayList<>(memory);
        for (int i = memoryPart2.size() - 1; i >= 0; i--) {

            int id = memoryPart2.get(i);
            int fileSize = 1;
            while (i - 1 >= 0 && memoryPart2.get(i-1) == id) {
                fileSize++;
                i--;
            }

            int firstFreeSpace = findFreeSpace(memoryPart2, fileSize, i);

            // set old to free space
            for (int j = i; j < i + fileSize; j++) {
                memoryPart2.set(j, -1);
            }

            // set free space to id
            for (int j = firstFreeSpace; j < firstFreeSpace + fileSize; j++) {
                memoryPart2.set(j, id);
            }
        }

        long part1 = calculateChecksum(memoryPart1);
        long part2 = calculateChecksum(memoryPart2);
       
        System.out.println("Part1: " + part1);
        System.out.println("Part2: " + part2);
    }

    private static long calculateChecksum(List<Integer> memory) {
        return IntStream.range(0, memory.size())
                .mapToLong(i -> memory.get(i) == -1 ? 0 : (long) i * memory.get(i))
                .sum();
    }

    private static int findFreeSpace(List<Integer> memory, int fileSize, int fileIndex) {
        // find first space in memory that fits the filesize:

        int count = 0;
        for (int i = 0; i < fileIndex; i++) {
            if (memory.get(i) != -1) {
                count = 0;
                continue;
            } 

            count++;

            if (count == fileSize) { 
                return i - fileSize + 1;
            }
        }

        return fileIndex;
    }
}