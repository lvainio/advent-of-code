package com.example.day9_2024;

import java.util.List;
import java.util.ArrayList;
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
        int pointer = 0;
        for (int i = memory.size() - 1; i >= 0; i--) {
            if (pointer > i) {
                break;
            }

            if (memory.get(i) != FREE_SPACE) {
                while (memory.get(pointer) != FREE_SPACE) {
                    pointer++;
                }
                if (pointer < i) {
                    memory.set(pointer, memory.get(i));
                    memory.set(i, FREE_SPACE);
                } 
            }
        }

        int i = 0;
        long total = 0;
        while(memory.get(i) != FREE_SPACE) {
            total += (long) i * memory.get(i);
            i++;
        }

        System.out.println("Part1: " + total);
    }
}