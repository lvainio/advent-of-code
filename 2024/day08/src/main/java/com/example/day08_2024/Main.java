package com.example.day08_2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        long part1 = 0;
        long part2 = 0;

        InputReader reader = new InputReader();

        HashMap<Character, ArrayList<Node>> antennas = reader.getAntennas();
        int numRows = reader.getNumRows();
        int numCols = reader.getNumCols();
        
        HashSet<Node> antiNodesPart1 = new HashSet<>();
        for (ArrayList<Node> nodes : antennas.values()) {
            for (int i = 0; i < nodes.size(); i++) {
                for (int j = i + 1; j < nodes.size(); j++) {
                    Node antenna1 = nodes.get(i);
                    Node antenna2 = nodes.get(j);
                    Node antiNode1 = new Node(
                        antenna1.row() + (antenna1.row() - antenna2.row()), 
                        antenna1.col() + (antenna1.col() - antenna2.col()));
                    Node antiNode2 = new Node(
                        antenna2.row() + (antenna2.row() - antenna1.row()), 
                        antenna2.col() + (antenna2.col() - antenna1.col()));
                    if (antiNode1.isInsideGrid(numRows, numCols)) {
                        antiNodesPart1.add(antiNode1);
                    }
                    if (antiNode2.isInsideGrid(numRows, numCols)) {
                        antiNodesPart1.add(antiNode2);
                    }
                }
            }
        }

        HashSet<Node> antiNodesPart2 = new HashSet<>();
        for (ArrayList<Node> nodes : antennas.values()) {
            for (int i = 0; i < nodes.size(); i++) {
                for (int j = i + 1; j < nodes.size(); j++) {
                    Node antenna1 = nodes.get(i);
                    Node antenna2 = nodes.get(j);

                    double slope = (double) (antenna1.row() - antenna2.row()) / (antenna1.col() - antenna2.col());
                    double intersect = (double) antenna1.row() - (slope * antenna1.col()); 

                    for (int row = 0; row < numRows; row++) {
                        for (int col = 0; col < numCols; col++) {
                            double tolerance = 1e-9;
                            if (Math.abs(row - (slope * col + intersect)) < tolerance) {
                                antiNodesPart2.add(new Node(row, col));
                            }
                        }
                    }
                }
            }
        }

        part1 = antiNodesPart1.size();
        part2 = antiNodesPart2.size();

        System.out.println("Part1: " + part1);
        System.out.println("Part2: " + part2);
    }
}