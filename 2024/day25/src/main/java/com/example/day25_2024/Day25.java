package com.example.day25_2024;

import java.util.List;

public class Day25 {

    public static void main(String[] args) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        List<Key> keys = parser.getKeys();
        List<Lock> locks = parser.getLocks();

        long count = locks.stream()
            .flatMap(lock -> keys.stream().filter(lock::fitsKey)) 
            .count();

        System.out.println(count);
    }
}
