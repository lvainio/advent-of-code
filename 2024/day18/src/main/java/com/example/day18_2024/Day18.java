package com.example.day18_2024;

import java.util.ArrayList;
import java.util.List;

public class Day18 {
    public static void main( String[] args ) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        List<Coordinate> coordinates = parser.getCoordinates();
        Coordinate starCoordinate = new Coordinate(0, 0); 
        Coordinate endCoordinate = new Coordinate(70, 70); 

        GridGraph gridGraph = new GridGraph(starCoordinate, endCoordinate);
        gridGraph.corrupt(1024, new ArrayList<>(coordinates));

        int part1 = gridGraph.bfs();
        System.out.println("Part1: " + part1);
       
        GridGraph gridGraph2 = null;
        for (int i = 1; i <= coordinates.size(); i++) {
            try {
                gridGraph2 = new GridGraph(starCoordinate, endCoordinate);
                gridGraph2.corrupt(i, new ArrayList<>(coordinates));
                gridGraph2.bfs();
            } catch (IllegalStateException e) {
                System.out.println("Part2: " + coordinates.get(i-1));
                break;
            }  
        }
    }
}
