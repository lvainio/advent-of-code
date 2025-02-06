package com.example.day24_2024;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Circuit {

    private record Pair(String id1, String id2) {}

    private static final int NUM_OUTPUT_BITS = 46;
    private static final int NUM_INPUT_BITS = 45;

    /*
     * Mapper from output wire ID to gate object. 
     */
    private Map<String, Gate> gates;

    /*
     * Mapper from wire id to wire object.
     */
    private Map<String, Wire> wires;

    public Circuit(Map<String, Gate> gates, Map<String, Wire> wires) {
        this.gates = gates;
        this.wires = wires;
    }

    /*
     * Computes the output value z (lowest numBits) given some input
     * on the x and y wires.
     */
    public long computeOutputValue(int numBits) {
        if (numBits == 0) {
            return 0L;
        }
        if (numBits < 0 || numBits > NUM_OUTPUT_BITS) {
            throw new IllegalArgumentException();
        }
        String binaryString = this.gates.keySet().stream()
            .filter(key -> key.startsWith("z"))
            .sorted(Comparator.reverseOrder())
            .map(key -> this.gates.get(key).computeOutputSignal(new HashSet<>()).toString())
            .collect(Collectors.joining()); 
        return Long.parseLong(binaryString.substring(NUM_OUTPUT_BITS-numBits, NUM_OUTPUT_BITS), 2);
    }

    private HashSet<Pair> swapsMade = new HashSet<>();

    public String getBrokenWires(int numBits) {

        List<Pair> swaps = new ArrayList<>();

        // FIRST:
        swapGateOutputs("hdt", "z05");
        swapsMade.add(new Pair("hdt", "z05"));
        swapsMade.add(new Pair("z05", "hdt"));

        // SECOND:
        swapGateOutputs("z09", "gbf");
        swapsMade.add(new Pair("z09", "gbf"));
        swapsMade.add(new Pair("gbf", "z09"));

        // THIRD:
        swapGateOutputs("mht", "jgt");
        swapsMade.add(new Pair("mht", "jgt"));
        swapsMade.add(new Pair("jgt", "mht"));

        // FOURTH:
        swapGateOutputs("z30", "nbf");
        swapsMade.add(new Pair("z30", "nbf"));
        swapsMade.add(new Pair("nbf", "z30"));

        for (int i = 0; i <= numBits; i++) {

            System.out.println(i);

            if (!verifyAdd(i)) {

                System.out.println("FAILED FOR LEVEL: " + i);

                List<String> keys = new ArrayList<>(gates.keySet());

                swaps = getSuccessfulSwaps(i);

                System.out.println(swaps);
            } 
        }
        return "";
    }

    /*
     * Returns a list of swaps that makes addition work up to numBits input sizes.
     */
    public List<Pair> getSuccessfulSwaps(int numBits) {
        List<Pair> swaps = new ArrayList<>();
        List<String> keys = new ArrayList<>(gates.keySet());
        for (int i = 0; i < keys.size(); i++) {
            for (int j = i + 1; j < keys.size(); j++) {

                if (this.swapsMade.contains(new Pair(keys.get(i), keys.get(j)))) {
                    continue;
                }

                swapGateOutputs(keys.get(i), keys.get(j));
                try {
                    if (verifyAdd(numBits)) {
                        swaps.add(new Pair(keys.get(i), keys.get(j)));
                    } 
                } catch (CycleDetectedException e) {
                    // Do nothing.
                }
                swapGateOutputs(keys.get(i), keys.get(j));
            }
        }
        return swaps;
    }

    /*
     * Returns current value on x or y wires.
     */
    private long getInputValue(String prefix, int numBits) {
        if (numBits == 0) {
            return 0L;
        }
        if (numBits < 0 || numBits > NUM_INPUT_BITS) {
            throw new IllegalArgumentException();
        }
        String binaryString = this.wires.values().stream()
            .filter(wire -> wire.getId().startsWith(prefix))
            .sorted(Comparator.comparing(Wire::getId).reversed())
            .map(wire -> wire.getSignal().toString())
            .collect(Collectors.joining());
        return Long.parseLong(binaryString.substring(NUM_INPUT_BITS-numBits, NUM_INPUT_BITS), 2);
    }

    /*
     * Returns true if addition works for 1000 randomly generated test
     * cases.
     */
    private boolean verifyAdd(int numBits) {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            resetSignals();
            long x = random.nextLong() & ((1L << numBits) - 1);
            long y = random.nextLong() & ((1L << numBits) - 1);
            setInputValue("x", x);
            setInputValue("y", y);
            long z = computeOutputValue(numBits+1);
            if (x + y != z) {
                return false;
            }
        }
        return true;
    }

    /*
     * Swaps output wires of two gates.
     */
    private void swapGateOutputs(String outputId1, String outputId2) {
        Gate gate1 = this.gates.get(outputId1);
        Gate gate2 = this.gates.get(outputId2);

        Wire outputWire1 = this.wires.get(outputId1);
        Wire outputWire2 = this.wires.get(outputId2);
        
        gate1.setOutput(outputWire2);
        gate2.setOutput(outputWire1);

        outputWire1.setSourceGate(gate2);
        outputWire2.setSourceGate(gate1);

        this.gates.put(outputId1, gate2);
        this.gates.put(outputId2, gate1);     
    }

    /*
     * Set input wires x or y to the specified value.
     */
    private void setInputValue(String prefix, long value) {
        String binaryValueString = Long.toBinaryString(value);
        int length = binaryValueString.length();
        this.wires.values().stream()
            .filter(wire -> wire.getId().startsWith(prefix))
            .forEach(wire -> {
                int bitPosition = Integer.parseInt(wire.getId().substring(1, 3));
                Signal signal = Signal.ZERO;
                if (bitPosition < length) {
                    int bitValue = Character.getNumericValue(binaryValueString.charAt(length - 1 - bitPosition));
                    signal = Signal.fromInt(bitValue);
                }
                wire.setSignal(signal);
        });
    }

    /*
     * Set all wire signals in the circuit to NONE.
     */
    private void resetSignals() {
        this.wires.values().forEach(wire -> {
            wire.setSignal(Signal.NONE);
        });
    }
}
