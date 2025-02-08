package com.example.day03_2015;

import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class Day03 {
    public static void main(String[] args) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        List<Move> moves = parser.getMoves();

        // Part1
        Santa santa1 = new Santa();
        moves.forEach(santa1::move);
        System.out.println(santa1.getNumVisited());

        // Part2
        Santa santa2 = new Santa();
        Santa roboSanta = new Santa();
        
        IntStream.range(0, moves.size())
            .filter(i -> i % 2 == 0)
            .mapToObj(moves::get)
            .forEach(santa2::move);

        IntStream.range(0, moves.size())
            .filter(i -> i % 2 == 1)
            .mapToObj(moves::get)
            .forEach(roboSanta::move);

        HashSet<Point> set1 = santa2.getVisited();
        HashSet<Point> set2 = roboSanta.getVisited();

        HashSet<Point> combined = new HashSet<>(set1);
        combined.addAll(set2);

        int numUnique = combined.size();
        System.out.println(numUnique);
    }
}
