package leo.aoc.year2015.day7;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private record Wire (String id, int signal) {}

    private HashMap<String, Wire> wires;

    private HashMap<String, Wire> wires2;

    public Solver(String input) {
        super(input);
        
        Set<String> instructions = input.lines().collect(Collectors.toSet());

        HashMap<String, Wire> wires = new HashMap<>();
        Pattern pattern = Pattern.compile("[a-z]+");
        instructions.stream().forEach(instruction -> {
            Matcher matcher = pattern.matcher(instruction);
            while (matcher.find()) {
                String id = matcher.group();
                Wire wire = new Wire(id, -1);
                wires.put(id, wire);
            }
        });

        Queue<String> queue = new LinkedList<>(instructions);
        while(!queue.isEmpty()) {
            String instruction = queue.poll();

            // ASSIGNMENT instruction
            if (instruction.matches("\\d+\\s+->\\s+[a-z]+")) {
                Pattern pattern2 = Pattern.compile("(\\d+)\\s+->\\s+([a-z]+)");
                Matcher matcher2 = pattern2.matcher(instruction);

                matcher2.find();
                Integer value = Integer.parseInt(matcher2.group(1));
                String id = matcher2.group(2);

                wires.put(id, new Wire(id, value));
            }

            // NOT instruction
            if (instruction.contains("NOT")) {
                String[] args = instruction.split("\\s+");
                String id1 = args[1];
                String id2 = args[3];

                Wire wire1 = wires.get(id1);
                if (wire1.signal() >= 0) {
                    wires.put(id2, new Wire(id2, ~wire1.signal() & 0xFFFF)); 
                } else {
                    queue.offer(instruction);
                }
            }

            // AND instruction
            if (instruction.contains("AND")) {
                String[] args = instruction.split("\\s+");

                String arg1 = args[0];
                String arg2 = args[2];
                String arg3 = args[4];

                int signal1 = 0;
                int signal2 = 0;
                if (wires.containsKey(arg1)) {
                    signal1 = wires.get(arg1).signal();
                } else {
                    signal1 = Integer.parseInt(arg1);
                }
                if (wires.containsKey(arg2)) {
                    signal2 = wires.get(arg2).signal();
                } else {
                    signal2 = Integer.parseInt(arg2);
                }

                if (signal1 >= 0 && signal2 >= 0) {
                    wires.put(arg3, new Wire(arg3, (signal1 & signal2) & 0xFFFF));
                } else {
                    queue.offer(instruction);
                }
            }

            // OR instruction
            if (instruction.contains("OR")) {
                String[] args = instruction.split("\\s+");

                String arg1 = args[0];
                String arg2 = args[2];
                String arg3 = args[4];

                int signal1 = 0;
                int signal2 = 0;
                if (wires.containsKey(arg1)) {
                    signal1 = wires.get(arg1).signal();
                } else {
                    signal1 = Integer.parseInt(arg1);
                }
                if (wires.containsKey(arg2)) {
                    signal2 = wires.get(arg2).signal();
                } else {
                    signal2 = Integer.parseInt(arg2);
                }

                if (signal1 >= 0 && signal2 >= 0) {
                    wires.put(arg3, new Wire(arg3, (signal1 | signal2) & 0xFFFF));
                } else {
                    queue.offer(instruction);
                }
            }

            // LSHIFT instruction
            if (instruction.contains("LSHIFT")) {
                String[] args = instruction.split("\\s+");
                String id1 = args[0];
                int shift = Integer.parseInt(args[2]);
                String id2 = args[4];

                Wire wire1 = wires.get(id1);
                if (wire1.signal() >= 0) {
                    wires.put(id2, new Wire(id2, (wire1.signal() << shift) & 0xFFFF));
                } else {
                    queue.offer(instruction);
                }
            }

            // RSHIFT instruction
            if (instruction.contains("RSHIFT")) {
                String[] args = instruction.split("\\s+");
                String id1 = args[0];
                int shift = Integer.parseInt(args[2]);
                String id2 = args[4];

                Wire wire1 = wires.get(id1);
                if (wire1.signal() >= 0) {
                    wires.put(id2, new Wire(id2, (wire1.signal() >> shift) & 0xFFFF));
                } else {
                    queue.offer(instruction);
                }
            }

            // WIRE TO WIRE instruction
            if (instruction.matches("[a-z]+\\s+->\\s+[a-z]+")) {

                String[] args = instruction.split("\\s+");
                String id1 = args[0];
                String id2 = args[2];

                Wire wire1 = wires.get(id1);

                if (wire1.signal() >= 0) {
                    wires.put(id2, new Wire(id2, wire1.signal()));
                } else {
                    queue.offer(instruction);
                }
            } 
        }
        this.wires = wires;

        HashMap<String, Wire> wires2 = new HashMap<>(this.wires);
        int aValue = wires2.get("a").signal();
        for (String id : wires2.keySet()) {
            wires2.put(id, new Wire(id, -1));
        }
        wires2.put("b", new Wire("b", aValue));

        Queue<String> queue2 = new LinkedList<>(instructions);
        while(!queue2.isEmpty()) {
            String instruction = queue2.poll();

            // ASSIGNMENT instruction
            if (instruction.matches("\\d+\\s+->\\s+[a-z]+")) {
                Pattern pattern2 = Pattern.compile("(\\d+)\\s+->\\s+([a-z]+)");
                Matcher matcher2 = pattern2.matcher(instruction);

                matcher2.find();
                Integer value = Integer.parseInt(matcher2.group(1));
                String id = matcher2.group(2);

                if (!id.equals("b")) {
                    wires2.put(id, new Wire(id, value));
                }
            }

            // NOT instruction
            if (instruction.contains("NOT")) {
                String[] args = instruction.split("\\s+");
                String id1 = args[1];
                String id2 = args[3];

                Wire wire1 = wires2.get(id1);
                if (wire1.signal() >= 0) {
                    wires2.put(id2, new Wire(id2, ~wire1.signal() & 0xFFFF)); 
                } else {
                    queue2.offer(instruction);
                }
            }

            // AND instruction
            if (instruction.contains("AND")) {
                String[] args = instruction.split("\\s+");

                String arg1 = args[0];
                String arg2 = args[2];
                String arg3 = args[4];

                int signal1 = 0;
                int signal2 = 0;
                if (wires2.containsKey(arg1)) {
                    signal1 = wires2.get(arg1).signal();
                } else {
                    signal1 = Integer.parseInt(arg1);
                }
                if (wires2.containsKey(arg2)) {
                    signal2 = wires2.get(arg2).signal();
                } else {
                    signal2 = Integer.parseInt(arg2);
                }

                if (signal1 >= 0 && signal2 >= 0) {
                    wires2.put(arg3, new Wire(arg3, (signal1 & signal2) & 0xFFFF));
                } else {
                    queue2.offer(instruction);
                }
            }

            // OR instruction
            if (instruction.contains("OR")) {
                String[] args = instruction.split("\\s+");

                String arg1 = args[0];
                String arg2 = args[2];
                String arg3 = args[4];

                int signal1 = 0;
                int signal2 = 0;
                if (wires2.containsKey(arg1)) {
                    signal1 = wires2.get(arg1).signal();
                } else {
                    signal1 = Integer.parseInt(arg1);
                }
                if (wires2.containsKey(arg2)) {
                    signal2 = wires2.get(arg2).signal();
                } else {
                    signal2 = Integer.parseInt(arg2);
                }

                if (signal1 >= 0 && signal2 >= 0) {
                    wires2.put(arg3, new Wire(arg3, (signal1 | signal2) & 0xFFFF));
                } else {
                    queue2.offer(instruction);
                }
            }

            // LSHIFT instruction
            if (instruction.contains("LSHIFT")) {
                String[] args = instruction.split("\\s+");
                String id1 = args[0];
                int shift = Integer.parseInt(args[2]);
                String id2 = args[4];

                Wire wire1 = wires2.get(id1);
                if (wire1.signal() >= 0) {
                    wires2.put(id2, new Wire(id2, (wire1.signal() << shift) & 0xFFFF));
                } else {
                    queue2.offer(instruction);
                }
            }

            // RSHIFT instruction
            if (instruction.contains("RSHIFT")) {
                String[] args = instruction.split("\\s+");
                String id1 = args[0];
                int shift = Integer.parseInt(args[2]);
                String id2 = args[4];

                Wire wire1 = wires2.get(id1);
                if (wire1.signal() >= 0) {
                    wires2.put(id2, new Wire(id2, (wire1.signal() >> shift) & 0xFFFF));
                } else {
                    queue2.offer(instruction);
                }
            }

            // WIRE TO WIRE instruction
            if (instruction.matches("[a-z]+\\s+->\\s+[a-z]+")) {

                String[] args = instruction.split("\\s+");
                String id1 = args[0];
                String id2 = args[2];

                Wire wire1 = wires2.get(id1);

                if (wire1.signal() >= 0) {
                    wires2.put(id2, new Wire(id2, wire1.signal()));
                } else {
                    queue2.offer(instruction);
                }
            } 
        }
        this.wires2 = wires2;

    }

    @Override
    public String solvePart1() {
        return Integer.toString(this.wires.get("a").signal());
    }

    @Override
    public String solvePart2() {
        return Integer.toString(this.wires2.get("a").signal());
    }
}
