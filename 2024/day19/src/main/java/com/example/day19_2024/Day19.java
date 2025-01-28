package com.example.day19_2024;

import java.util.HashMap;
import java.util.List;

public class Day19 {
    public static void main( String[] args ) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        List<String> patterns = parser.getPatterns();
        List<String> designs = parser.getDesigns();

        int numValidDesigns = 0;
        for (String design : designs) {
            if (isValidDesign(design, patterns)) {
                numValidDesigns++;
            }
        }
        System.out.println("Part1: " + numValidDesigns);

        long numArrangements = 0;
        HashMap<String, Long> designToCount = new HashMap<>();
        for (String design : designs) {
            numArrangements += countArrangements(design, patterns, designToCount);
            System.out.println(numArrangements);
        }
        System.out.println("Part2: " + numArrangements);
    }

    public static boolean isValidDesign(String design, List<String> patterns) {
        if (design.length() == 0) {
            return true;
        }
        for (String pattern : patterns) {
            if (design.startsWith(pattern) && isValidDesign(design.substring(pattern.length()), patterns)) {
                return true;
            }
        }
        return false;
    }

    public static long countArrangements(String design, List<String> patterns, HashMap<String, Long> designToCount) {
        if (design.length() == 0) {
            return 1;
        }
        if (designToCount.containsKey(design)) {
            return designToCount.get(design);
        }
        long count = 0;
        for (String pattern : patterns) {
            if (design.startsWith(pattern)) {
                count += countArrangements(design.substring(pattern.length()), patterns, designToCount);
            }
        }
        designToCount.put(design, count);
        return count;
    }
}
