package com.example.day24_2024;

import java.util.Map;

public class Day24 {

    public static void main(String[] args) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");
        Map<String, Gate> gates = parser.getGates();
        
        Circuit circuit = new Circuit(gates);

        // Part1
        System.out.println(circuit.getOutputValue(46));
        
        // Part2
        System.out.println(circuit.getBrokenWires()); 
    }
}
