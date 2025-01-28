package com.example.day17_2024;

import java.util.ArrayList;
import java.util.List;

public class Computer {

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

    private List<Integer> instructions;
    private int ip;

    private StringBuilder output;

    private List<Integer> outputList;

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
        return this.instructions.get(this.ip+1);
    }

    private long getComboOperand() {
        int operand = this.instructions.get(this.ip+1);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                                .append("Computer(a=")
                                .append(this.a)
                                .append(", b=")
                                .append(this.b)
                                .append(", c=")
                                .append(this.c)
                                .append(", instructions=")
                                .append(this.instructions)
                                .append(", ip=")
                                .append(this.ip)
                                .append(", output=")
                                .append(this.output)
                                .append(")");
        return sb.toString();
    }
}
