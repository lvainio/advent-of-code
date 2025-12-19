package me.vainio.aoc.year2024.day24;

import java.util.ArrayList;
import java.util.Collections;
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

      System.out.println(numBits);

      throw new IllegalArgumentException();
    }
    String binaryString =
        this.gates.keySet().stream()
            .filter(key -> key.startsWith("z"))
            .sorted(Comparator.reverseOrder())
            .map(key -> this.gates.get(key).computeOutputSignal(new HashSet<>()).toString())
            .collect(Collectors.joining());
    return Long.parseLong(binaryString.substring(NUM_OUTPUT_BITS - numBits, NUM_OUTPUT_BITS), 2);
  }

  public String getBrokenWires() {
    HashSet<Pair> brokenWires = getBrokenWiresRecursive(new HashSet<>());
    List<String> sortedIds = new ArrayList<>();
    for (Pair pair : brokenWires) {
      sortedIds.add(pair.id1());
      sortedIds.add(pair.id2());
    }
    Collections.sort(sortedIds);
    return sortedIds.stream().collect(Collectors.joining(","));
  }

  private HashSet<Pair> getBrokenWiresRecursive(HashSet<Pair> swaps) {
    if (swaps.size() == 4) {
      for (Pair pair : swaps) {
        swapGateOutputs(pair.id1(), pair.id2());
      }
      if (verifyAdd(NUM_INPUT_BITS)) {
        return swaps;
      } else {
        for (Pair pair : swaps) {
          swapGateOutputs(pair.id1(), pair.id2());
        }
        return null;
      }
    }

    for (Pair pair : swaps) {
      swapGateOutputs(pair.id1(), pair.id2());
    }

    for (int i = 0; i <= NUM_INPUT_BITS; i++) {
      if (!verifyAdd(i)) {
        List<Pair> potentialSwaps = getSuccessfulSwaps(i, swaps);

        // Swap back earlier gates to not interfer with recursive call.
        for (Pair pair : swaps) {
          swapGateOutputs(pair.id1(), pair.id2());
        }

        // Try each potential swap recursively.
        for (Pair pair : potentialSwaps) {
          HashSet<Pair> newSwaps = new HashSet<>(swaps);
          newSwaps.add(pair);
          HashSet<Pair> brokenWires = getBrokenWiresRecursive(newSwaps);
          if (brokenWires != null) {
            return brokenWires;
          }
        }
        break;
      }
    }
    return null;
  }

  /*
   * Returns a list of swaps that makes addition work up to numBits input sizes.
   */
  public List<Pair> getSuccessfulSwaps(int numBits, HashSet<Pair> swapsMade) {
    List<Pair> swaps = new ArrayList<>();
    List<String> keys = new ArrayList<>(gates.keySet());
    for (int i = 0; i < keys.size(); i++) {
      for (int j = i + 1; j < keys.size(); j++) {

        if (swapsMade.contains(new Pair(keys.get(i), keys.get(j)))) {
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
      long z = computeOutputValue(numBits + 1);
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
        .forEach(
            wire -> {
              int bitPosition = Integer.parseInt(wire.getId().substring(1, 3));
              Signal signal = Signal.ZERO;
              if (bitPosition < length) {
                int bitValue =
                    Character.getNumericValue(binaryValueString.charAt(length - 1 - bitPosition));
                signal = Signal.fromInt(bitValue);
              }
              wire.setSignal(signal);
            });
  }

  /*
   * Set all wire signals in the circuit to NONE.
   */
  private void resetSignals() {
    this.wires
        .values()
        .forEach(
            wire -> {
              wire.setSignal(Signal.NONE);
            });
  }
}
