package com.example.day24_2024;

public class Day24 {

    public static void main(String[] args) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");
        
        Circuit circuit = parser.getCircuit();

        // Part1
        System.out.println(circuit.computeOutputValue(46));
  
        // Part2
        System.out.println(circuit.getBrokenWires(46)); 
    }
}
