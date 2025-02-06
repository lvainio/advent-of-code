package com.example.day24_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputParser {

    private Circuit circuit = null; 

    public Circuit getCircuit() {
        return this.circuit;
    }

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String[] inputsAndGates = reader.lines().collect(Collectors.joining("\n")).split("\r?\n\r?\n");

            Map<String, Wire> wires = inputsAndGates[0].lines().map(line -> {
                String id = line.substring(0, 3);
                Signal signal = Signal.fromInt(Character.getNumericValue(line.charAt(line.length()-1)));
                return new Wire(id, signal);
            })
            .collect(Collectors.toMap(Wire::getId, wire -> wire));

            Pattern pattern = Pattern.compile("([a-zA-Z0-9]+)\\s+(XOR|OR|AND)\\s+([a-zA-Z0-9]+)\\s+->\\s+([a-zA-Z0-9]+)");
            Map<String, Gate> gates = inputsAndGates[1].lines().map(line -> {
                Matcher matcher = pattern.matcher(line);
                matcher.find();
                String inputId1 = matcher.group(1).trim();
                String operation = matcher.group(2).trim();
                String inputId2 = matcher.group(3).trim();
                String outputId = matcher.group(4).trim(); 

                Wire inputWire1 = wires.get(inputId1);
                if (inputWire1 == null) {
                    inputWire1 = new Wire(inputId1, Signal.NONE);
                }
                Wire inputWire2 = wires.get(inputId2);
                if (inputWire2 == null) {
                    inputWire2 = new Wire(inputId2, Signal.NONE);
                }
                Wire outputWire = wires.get(outputId);
                if (outputWire == null) {
                    outputWire = new Wire(outputId, Signal.NONE);
                }

                Gate gate = switch (operation) {
                    case "XOR" -> new XorGate(inputWire1, inputWire2, outputWire);
                    case "AND" -> new AndGate(inputWire1, inputWire2, outputWire);
                    case "OR" -> new OrGate(inputWire1, inputWire2, outputWire);
                    default -> throw new IllegalStateException("Invalid operation: " + operation);
                };
                inputWire1.addTargetGate(gate);
                inputWire2.addTargetGate(gate);
                outputWire.setSourceGate(gate);

                wires.put(inputId1, inputWire1);
                wires.put(inputId2, inputWire2);
                wires.put(outputId, outputWire);

                return gate;
            })
            .collect(Collectors.toMap(Gate::getOutputId, gate -> gate));
            this.circuit = new Circuit(gates, wires);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
