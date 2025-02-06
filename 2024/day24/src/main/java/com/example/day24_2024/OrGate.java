package com.example.day24_2024;

import java.util.HashSet;

public final class OrGate extends Gate {

    public OrGate(Wire input1, Wire input2, Wire output) {
        super(input1, input2, output);
    }

    @Override
    public Signal computeOutputSignal(HashSet<String> visited) {
        if (visited.contains(this.getOutputId())) {
            throw new CycleDetectedException("Cycle detected in gate computation");
        }
        visited.add(this.getOutputId());

        Signal inputSignal1 = this.input1.getSignal();
        Signal inputSignal2 = this.input2.getSignal();
        if (inputSignal1 == Signal.NONE) {
            inputSignal1 = this.input1.getSourceGate().computeOutputSignal(visited);
        }
        if (inputSignal2 == Signal.NONE) {
            inputSignal2 = this.input2.getSourceGate().computeOutputSignal(visited);
        }
        Signal outputSignal = inputSignal1.or(inputSignal2);
        this.output.setSignal(outputSignal);
        return outputSignal; 
    }
}
