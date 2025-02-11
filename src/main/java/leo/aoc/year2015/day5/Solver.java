package leo.aoc.year2015.day5;

import java.util.List;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private List<String> strings;

    public Solver(String input) {
        super(input);
        this.strings = input.lines().toList();
    }

    @Override
    public String solvePart1() {
        long count = strings.stream().filter(this::isNiceString).count();
        return Long.toString(count);
    }

    @Override
    public String solvePart2() {
        long count = strings.stream().filter(this::isNiceString2).count();
        return Long.toString(count);
    }


    private boolean isNiceString2(String s) {
        return containsRepeatingLetter(s) 
            && containsRepeatingPair(s);
    }

    private boolean containsRepeatingLetter(String s) {
        for (int i = 0; i < s.length() - 2; i++) {
            if (s.charAt(i) == s.charAt(i+2)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsRepeatingPair(String s) {
        for (int i = 0; i < s.length()-1; i++) {
            String pair = s.substring(i, i+2);
            if (s.substring(i+2).contains(pair)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNiceString(String s) {
        return !containsSubstrings(s) 
            && containsConsecutiveLetters(s) 
            && containsThreeVowels(s);
    }
    
    public boolean containsThreeVowels(String s) {
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

    public boolean containsConsecutiveLetters(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsSubstrings(String s) {
        String[] substrings = {"ab", "cd", "pq", "xy"};
        for (String sub : substrings) {
            if (s.contains(sub)) {
                return true;
            }
        }
        return false;
    }
}
