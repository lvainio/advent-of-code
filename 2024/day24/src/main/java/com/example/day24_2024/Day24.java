package com.example.day24_2024;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class Day24 {

    public static void main(String[] args) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        Map<String, Gate> gates = parser.getGates();

        // Part1
        System.out.println(getOutputValue(gates, 46));
        
        // Part2

        // I might need to add target to wire again so i can swap two wires
        // ripple adder. go from lowest digit to highest as soon as problem occurs and brute force swaps.

        // no gate will have outputId x.. or y.. so i have to fix that as well.

        // swaps might cause ciruclar paths so i need to maybe throw exception then and catch it idk.
        
        // I want a way to set signal of an input wire. Then i might have to recalculate the entire thing so my calcs
        // might need to change. 

        
    }

    private static long getOutputValue(Map<String, Gate> gates, int numBits) {
        String binaryString = gates.keySet().stream()
            .filter(key -> key.startsWith("z"))
            .sorted(Comparator.reverseOrder())
            .map(key -> gates.get(key).computeOutputSignal().toString())
            .collect(Collectors.joining()); 
        return Long.parseLong(binaryString.substring(0, numBits), 2);
    }

    private static void setInputValue(Map<String, Gate> gates, String prefix, long value) {

    }
}
