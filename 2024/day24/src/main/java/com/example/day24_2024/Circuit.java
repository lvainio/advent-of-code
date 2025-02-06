package com.example.day24_2024;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class Circuit {

    private Map<String, Gate> gates;

    // maybe have a list of wire IDs or set.

    public Circuit(Map<String, Gate> gates) {
        this.gates = gates;
    }

    public long getOutputValue(int numBits) {
        String binaryString = this.gates.keySet().stream()
            .filter(key -> key.startsWith("z"))
            .sorted(Comparator.reverseOrder())
            .map(key -> this.gates.get(key).computeOutputSignal().toString())
            .collect(Collectors.joining()); 
        return Long.parseLong(binaryString.substring(0, numBits), 2);
    }

    public String getBrokenWires() {
        return "";
    }

    private boolean verify(int numBits) {

        // generate 1000 random ints in 2^numBits and check that add works as intended maybe

        return false;
    }

    private void setInputValue(String prefix, long value) {

    }

    private void swapWireTargets() {
         // ripple adder. go from lowest digit to highest as soon as problem occurs and brute force swaps.

        // no gate will have outputId x.. or y.. so i have to fix that as well.

        // swaps might cause ciruclar paths so i need to maybe throw exception then and catch it idk.
        
        // I want a way to set signal of an input wire. Then i might have to recalculate the entire thing so my calcs
        // might need to change.
    }

    private void resetSignals() {
        // set all wires signals to None.
    }
}
