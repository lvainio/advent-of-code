package com.example.day01_2024;

import java.util.List;

public class Day01 {
    public static void main(String[] args) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        List<Token> tokens = parser.getTokens();
        int floor = 0;
        int position = 1;
        for (Token token : tokens) {
            switch (token) {
                case LPAR -> floor++;
                case RPAR -> floor--;
                default -> throw new IllegalArgumentException("Invalid token!");
            }
            if (floor == -1) {
                System.out.println("Position: " + position);
            }
            position++;
        }
        System.out.println("Floor: " + floor);
    }
}
