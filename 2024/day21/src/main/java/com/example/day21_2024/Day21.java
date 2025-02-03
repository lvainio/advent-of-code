package com.example.day21_2024;

import java.util.List;

public class Day21 {

    public static void main(String[] args) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        List<String> codes = parser.getCodes();

        Keypad nkp = new Keypad(Keypad.NUMERIC_KEYPAD);
        Keypad dkp = new Keypad(Keypad.DIRECTIONAL_KEYPAD);

        long total = 0;
        for (String code : codes) {
            List<String> inputs = nkp.getAllInputSequences(code);

            long min = Long.MAX_VALUE;
            for (String input : inputs) {
                min = Math.min(min, dkp.computeLength(input, 25));
            }
            total += min * Integer.parseInt(code.substring(0, 3));
        }
        System.out.println(total);
    }
}
