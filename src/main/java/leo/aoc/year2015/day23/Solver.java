package leo.aoc.year2015.day23;

import java.util.List;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private enum InstructionType {
        HLF,
        TPL,
        INC,
        JMP,
        JIE,
        JIO;
    }

    private class Instruction {
        private InstructionType type;
        private String register;
        private int value;

        public Instruction(InstructionType type, String register, int value) {
            this.type = type;
            this.register = register;
            this.value = value;
        }   

        public InstructionType getType() {
            return type;
        }
    
        public String getRegister() {
            return register;
        }
    
        public int getValue() {
            return value;
        }
    
        @Override
        public String toString() {
            return type + " " + (register != null ? register : "") + (value != 0 ? " " + value : "");
        }
    }

    private List<Instruction> instructions;

    public Solver(String input) {
        super(input);
        
        this.instructions = input.lines().map(line -> {
            String[] parts = line.split("\\s+");
            InstructionType type = InstructionType.valueOf(parts[0].toUpperCase());
            Instruction instruction = null;
            switch (type) {
                case HLF, TPL, INC -> {
                    String register = parts[1];
                    instruction = new Instruction(type, register, 0);
                }
                case JMP -> {
                    int value = Integer.parseInt(parts[1]);
                    instruction = new Instruction(type, null, value);
                }
                case JIO, JIE -> {
                    String register = parts[1].substring(0, 1);
                    int value = Integer.parseInt(parts[2]);
                    instruction = new Instruction(type, register, value);
                }
                default -> throw new IllegalArgumentException("Invalid instruction");
            }
            return instruction;
        }).toList();
    }

    @Override
    public String solvePart1() {
        return Long.toString(runProgram(0, 0));
    }

    @Override
    public String solvePart2() {
        return Long.toString(runProgram(1, 0));
    }

    private long runProgram(long a, long b) {
        int instructionPointer = 0;
        while (instructionPointer < this.instructions.size()) {
            Instruction instruction = this.instructions.get(instructionPointer);
            switch (instruction.getType()) {
                case HLF -> {
                    String register = instruction.getRegister();
                    if (register.equals("a")) {
                        a /= 2;
                    } else {
                        b /= 2;
                    }
                    instructionPointer++;
                }
                case TPL -> {
                    String register = instruction.getRegister();
                    if (register.equals("a")) {
                        a *= 3;
                    } else {
                        b *= 3;
                    }
                    instructionPointer++;
                }
                case INC -> {
                    String register = instruction.getRegister();
                    if (register.equals("a")) {
                        a++;
                    } else {
                        b++;
                    }
                    instructionPointer++;
                }
                case JMP -> {
                    int offset = instruction.getValue();
                    instructionPointer += offset;
                }
                case JIE -> {
                    String register = instruction.getRegister();
                    int offset = instruction.getValue();
                    boolean isEven = switch(register) {
                        case "a" -> a % 2 == 0;
                        case "b" -> b % 2 == 0;
                        default -> throw new IllegalArgumentException("invalid register");
                    };
                    if (isEven) {
                        instructionPointer += offset;
                    } else {
                        instructionPointer++;
                    }
                }
                case JIO -> {
                    String register = instruction.getRegister();
                    int offset = instruction.getValue();
                    boolean isOne = switch(register) {
                        case "a" -> a == 1;
                        case "b" -> b == 1;
                        default -> throw new IllegalArgumentException("invalid register");
                    };
                    if (isOne) {
                        instructionPointer += offset;
                    } else {
                        instructionPointer++;
                    }
                }
            }
        }
        return b;
    }
}
