package me.vainio.aoc.year2024.day17;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 17;

  private final long a;
  private final long b;
  private final long c;

  private final List<Integer> instructions;

  public static void main(final String[] args) {
    final AocCache cache = new AocCache();

    final String input = cache.getInput(YEAR, DAY);
    final Solver solver = new Solver(input);

    final String part1 = solver.solvePart1();
    final String part2 = solver.solvePart2();

    System.out.println(part1);
    System.out.println(part2);

    cache.saveAnswer(YEAR, DAY, 1, part1);
    cache.saveAnswer(YEAR, DAY, 2, part2);
  }

  public Solver(final String input) {
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(input);

    if (!matcher.find()) {
      throw new IllegalArgumentException("Invalid input: missing a");
    }
    this.a = Long.parseLong(matcher.group());
    if (!matcher.find()) {
      throw new IllegalArgumentException("Invalid input: missing b");
    }
    this.b = Long.parseLong(matcher.group());
    if (!matcher.find()) {
      throw new IllegalArgumentException("Invalid input: missing c");
    }
    this.c = Long.parseLong(matcher.group());

    List<Integer> instructions = new ArrayList<>();
    while (matcher.find()) {
      instructions.add(Integer.parseInt(matcher.group()));
    }
    this.instructions = instructions;
  }

  public String solvePart1() {
    Computer computer = new Computer(a, b, c, new ArrayList<>(instructions));
    computer.runProgram();
    return computer.getOutput().trim().substring(0, computer.getOutput().length() - 1);
  }

  public String solvePart2() {
    long a2 = 0b000;
    long minValue = findMin(instructions, instructions.size() - 1, 0, a2, b, c);
    return Long.toString(minValue);
  }

  public long findMin(
      List<Integer> instructions, int instructionIdx, int outputIdx, long a, long b, long c) {
    if (instructionIdx < 0) {
      return a >> 3;
    }

    List<Long> validAValues = new ArrayList<>();
    for (int i = 0; i < 8; i++) {
      Computer computer = new Computer(a, b, c, new ArrayList<>(instructions));
      computer.runProgram();

      List<Integer> output = computer.getOutputList();
      if (Objects.equals(output.get(outputIdx), instructions.get(instructionIdx))) {
        validAValues.add(a);
      }
      a++;
    }
    return validAValues.stream()
        .mapToLong(newA -> findMin(instructions, instructionIdx - 1, outputIdx, newA << 3, b, c))
        .min()
        .orElse(Long.MAX_VALUE);
  }

  public static class Computer {

    private enum Instruction {
      ADV(0),
      BXL(1),
      BST(2),
      JNZ(3),
      BXC(4),
      OUT(5),
      BDV(6),
      CDV(7);

      private final int opcode;

      Instruction(int opcode) {
        this.opcode = opcode;
      }

      public static Instruction fromOpcode(int opcode) {
        for (Instruction instruction : Instruction.values()) {
          if (instruction.opcode == opcode) {
            return instruction;
          }
        }
        throw new IllegalArgumentException("Invalid opcode: " + opcode);
      }
    }

    private long a;
    private long b;
    private long c;

    private final List<Integer> instructions;
    private int ip;

    private final StringBuilder output;

    private final List<Integer> outputList;

    public Computer(long a, long b, long c, List<Integer> instructions) {
      this.a = a;
      this.b = b;
      this.c = c;

      this.instructions = instructions;
      this.ip = 0;

      this.output = new StringBuilder();
      this.outputList = new ArrayList<>();
    }

    public String getOutput() {
      return this.output.toString();
    }

    public List<Integer> getOutputList() {
      return this.outputList;
    }

    public void runProgram() {
      while (this.ip < instructions.size()) {
        runInstruction();
      }
    }

    private void runInstruction() {
      int opcode = this.instructions.get(this.ip);

      switch (Instruction.fromOpcode(opcode)) {
        case ADV -> runAdv();
        case BXL -> runBxl();
        case BST -> runBst();
        case JNZ -> runJnz();
        case BXC -> runBxc();
        case OUT -> runOut();
        case BDV -> runBdv();
        case CDV -> runCdv();
        default -> throw new IllegalArgumentException("Invalid instruction");
      }
    }

    private void runAdv() {
      long comboOperand = getComboOperand();

      long numerator = this.a;
      long denominator = (long) Math.pow(2, comboOperand);

      this.a = numerator / denominator;
      this.ip += 2;
    }

    private void runBxl() {
      long literalOperand = getLiteralOperand();

      this.b = this.b ^ literalOperand;
      this.ip += 2;
    }

    private void runBst() {
      long comboOperand = getComboOperand();

      this.b = comboOperand % 8;
      this.ip += 2;
    }

    private void runJnz() {
      if (this.a == 0) {
        this.ip += 2;
        return;
      }
      long literalOperand = getLiteralOperand();
      if (literalOperand != this.ip) {
        this.ip = (int) literalOperand;
      } else {
        this.ip += 2;
      }
    }

    private void runBxc() {
      this.b = this.b ^ this.c;
      this.ip += 2;
    }

    private void runOut() {
      long comboOperand = getComboOperand();
      this.output.append(comboOperand % 8).append(",");
      this.outputList.add((int) (comboOperand % 8));
      this.ip += 2;
    }

    private void runBdv() {
      long comboOperand = getComboOperand();

      long numerator = this.a;
      long denominator = (long) Math.pow(2, comboOperand);

      this.b = numerator / denominator;
      this.ip += 2;
    }

    private void runCdv() {
      long comboOperand = getComboOperand();

      long numerator = this.a;
      long denominator = (long) Math.pow(2, comboOperand);

      this.c = numerator / denominator;
      this.ip += 2;
    }

    private long getLiteralOperand() {
      return this.instructions.get(this.ip + 1);
    }

    private long getComboOperand() {
      int operand = this.instructions.get(this.ip + 1);
      if (operand <= 3) {
        return operand;
      } else if (operand == 4) {
        return this.a;
      } else if (operand == 5) {
        return this.b;
      } else if (operand == 6) {
        return this.c;
      } else {
        throw new IllegalArgumentException("Invalid combo operand");
      }
    }
  }
}
