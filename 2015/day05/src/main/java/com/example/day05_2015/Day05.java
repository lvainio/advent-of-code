package com.example.day05_2015;

import java.util.List;

public class Day05 {
    public static void main(String[] args) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        // Part1
        List<String> strings = parser.getStrings();
        long count = strings.stream().filter(Day05::isNiceString).count();
        System.out.println(count);

        // Part2
        long count2 = strings.stream().filter(Day05::isNiceString2).count();
        System.out.println(count2);
    }

    private static boolean isNiceString2(String s) {
        return containsRepeatingLetter(s) 
            && containsRepeatingPair(s);
    }

    private static boolean containsRepeatingLetter(String s) {
        for (int i = 0; i < s.length() - 2; i++) {
            if (s.charAt(i) == s.charAt(i+2)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsRepeatingPair(String s) {
        for (int i = 0; i < s.length()-1; i++) {
            String pair = s.substring(i, i+2);
            if (s.substring(i+2).contains(pair)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNiceString(String s) {
        return !containsSubstrings(s) 
            && containsConsecutiveLetters(s) 
            && containsThreeVowels(s);
    }
    
    public static boolean containsThreeVowels(String s) {
        int vowelCount = 0;
        String vowels = "aeiou"; 
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (vowels.indexOf(c) != -1) {  
                vowelCount++;
            }
        }
        return vowelCount >= 3;
    }

    public static boolean containsConsecutiveLetters(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsSubstrings(String s) {
        String[] substrings = {"ab", "cd", "pq", "xy"};
        for (String sub : substrings) {
            if (s.contains(sub)) {
                return true;
            }
        }
        return false;
    }
}
