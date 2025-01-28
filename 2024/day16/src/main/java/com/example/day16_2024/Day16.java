package com.example.day16_2024;

import java.util.HashMap;
import java.util.HashSet;

public class Day16 {
    public static void main( String[] args ) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        HashMap<Node, HashSet<Node>> graph = parser.getMap();
        Node startNode = parser.getStartNode();
        Node endNode = parser.getEndNode();

        Graph g1 = new Graph(graph, startNode, endNode);

        int part1 = g1.findLowestScore();
        int part2 = g1.getNumBestPathNodes();

        System.out.println("Part1: " + part1);
        System.out.println("Part2: " + part2);
    }
}
