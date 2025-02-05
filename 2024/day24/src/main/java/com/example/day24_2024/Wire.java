package com.example.day24_2024;

public class Wire {

    private final String id; 
    private Signal signal; 

    private Gate sourceGate;

    public Wire(String id, Signal signal) {
        this.id = id;
        this.signal = signal;
    }

    public String getId() {
        return this.id;
    }

    public Signal getSignal() {
        return this.signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public Gate getSourceGate() {
        return this.sourceGate;
    }

    public void setSourceGate(Gate sourcGate) {
        this.sourceGate = sourcGate;
    }

    @Override
    public String toString() {
        return "Wire{id='" + id + "', signal=" + signal +  '}';
    }
}
