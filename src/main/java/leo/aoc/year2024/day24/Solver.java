package leo.aoc.year2024.day24;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private Circuit circuit;

    public Solver(String input) {
        super(input);
        
        String[] inputsAndGates = input.split("\r?\n\r?\n");

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
    }

    @Override
    public String solvePart1() {
        return Long.toString(this.circuit.computeOutputValue(46));
    }

    @Override
    public String solvePart2() {
        return circuit.getBrokenWires();
    } 
}
