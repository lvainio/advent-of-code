package leo.aoc.year2024.day17;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private long a = 0;
    private long b = 0;
    private long c = 0;
    
    private List<Integer> instructions;

    public Solver(String input) {
        super(input);
        
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        matcher.find();
        this.a = Long.parseLong(matcher.group());
        matcher.find();
        this.b = Long.parseLong(matcher.group());
        matcher.find();
        this.c = Long.parseLong(matcher.group());

        List<Integer> instructions = new ArrayList<>();
        while(matcher.find()) {
            instructions.add(Integer.parseInt(matcher.group()));
        }
        this.instructions = instructions;
    }

    @Override
    public String solvePart1() {
        Computer computer = new Computer(a, b, c, new ArrayList<>(instructions));
        computer.runProgram();
        return computer.getOutput().trim();
    }

    @Override
    public String solvePart2() {
        long a2 = 0b000;
        long b2 = b;
        long c2 = c;
        long minValue = findMin(instructions, instructions.size()-1, 0, a2, b2, c2);
        return Long.toString(minValue);
    }

    public long findMin(
        List<Integer> instructions, 
        int instructionIdx, 
        int outputIdx, 
        long a, 
        long b, 
        long c
    ) {
        if (instructionIdx < 0) {
            return a>>3;
        }

        List<Long> validAValues = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Computer computer = new Computer(a, b, c, new ArrayList<>(instructions));
            computer.runProgram();

            List<Integer> output = computer.getOutputList();
            if (output.get(outputIdx) == instructions.get(instructionIdx)) {
                validAValues.add(a);
            }
            a++;
        }
        return validAValues.stream()
                    .mapToLong(newA -> 
                        findMin(instructions, instructionIdx-1, outputIdx, newA<<3, b, c))
                    .min()
                    .orElse(Long.MAX_VALUE);
    }
    
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
    }
}
