package com.example.day24_2024;

import java.util.HashSet;

public sealed abstract class Gate permits XorGate, AndGate, OrGate {

    protected Wire input1;
    protected Wire input2;
    protected Wire output;

    public Gate(Wire input1, Wire input2, Wire output) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    public Wire getOutput() {
        return this.output;
    }

    public void setOutput(Wire output) {
        this.output = output;
    }

    public String getOutputId() {
        return this.output.getId();
    }

    public abstract Signal computeOutputSignal(HashSet<String> visited);
}
