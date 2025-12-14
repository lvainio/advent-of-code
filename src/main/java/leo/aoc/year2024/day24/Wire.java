package leo.aoc.year2024.day24;

import java.util.HashSet;
import java.util.Set;

public class Wire {

  private final String id;
  private Signal signal;

  private Gate sourceGate;
  private Set<Gate> targetGates;

  public Wire(String id, Signal signal) {
    this.id = id;
    this.signal = signal;
    this.sourceGate = null;
    this.targetGates = new HashSet<>();
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

  public Set<Gate> getTargetGates() {
    return this.targetGates;
  }

  public void addTargetGate(Gate targetGate) {
    this.targetGates.add(targetGate);
  }

  @Override
  public String toString() {
    return "Wire{id='" + id + "', signal=" + signal + '}';
  }
}
