package com.example.day15_2024;

import java.util.List;

public class Day15 {
    public static void main( String[] args ) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        Cell[][] map = parser.getMap();
        List<Move> moves = parser.getMoves();
        
        Warehouse warehouse = new Warehouse(map);
        warehouse.makeMoves(moves);

        int part1 = warehouse.countScore();

        System.out.println("Part1: " + part1);
    }
}
