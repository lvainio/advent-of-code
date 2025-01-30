package com.example.day20_2024;

public class Day20 {
    public static void main(String[] args) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        Cell[][] grid = parser.getGrid();
        Node startNode = parser.getStartPosition();
        Node endNode = parser.getEndPosition();

        GridGraph gg = new GridGraph(grid, startNode, endNode);

        int part1 = gg.search();

        System.out.println("Part1: " + part1);
    }
}
