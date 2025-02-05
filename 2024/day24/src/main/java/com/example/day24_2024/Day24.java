package com.example.day24_2024;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class Day24 {

    public static void main(String[] args) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        Map<String, Gate> gates = parser.getGates();

        String binaryString = gates.keySet().stream()
            .filter(key -> key.startsWith("z"))
            .sorted(Comparator.reverseOrder())
            .map(key -> gates.get(key).computeOutputSignal().toString())
            .collect(Collectors.joining());
        System.out.println(Long.parseLong(binaryString, 2));

        // I might need to add target to wire again hehe
    }
}
