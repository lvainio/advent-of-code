package com.example.day24_2024;

public final class AndGate extends Gate {

    public AndGate(Wire input1, Wire input2, Wire output) {
        super(input1, input2, output);
    }

    @Override
    public Signal computeOutputSignal() {
        Signal inputSignal1 = this.input1.getSignal();
        Signal inputSignal2 = this.input2.getSignal();
        if (inputSignal1 == Signal.NONE) {
            inputSignal1 = this.input1.getSourceGate().computeOutputSignal();
        }
        if (inputSignal2 == Signal.NONE) {
            inputSignal2 = this.input2.getSourceGate().computeOutputSignal();
        }
        Signal outputSignal = inputSignal1.and(inputSignal2);
        this.output.setSignal(outputSignal);
        return outputSignal; 
    }
}
