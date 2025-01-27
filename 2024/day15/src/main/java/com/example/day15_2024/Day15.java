package com.example.day15_2024;

import java.util.List;

public class Day15 {
    public static void main( String[] args ) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        Cell[][] map1 = parser.getMap1();
        Cell[][] map2 = parser.getMap2();
        List<Move> moves = parser.getMoves();

        Warehouse warehouse1 = new Warehouse(map1);
        warehouse1.makeMoves(moves);

        Warehouse warehouse2 = new Warehouse(map2);
        warehouse2.makeMovesPart2(moves);

        int part1 = warehouse1.countScore();
        int part2 = warehouse2.countScorePart2();

        System.out.println("Part1: " + part1);
        System.out.println("Part2: " + part2);
    }
}
