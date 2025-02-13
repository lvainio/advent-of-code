package leo.aoc.year2016.day1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import leo.aoc.AbstractSolver;
import leo.aoc.util.Direction;

public class Solver extends AbstractSolver {

    private record Node(int x, int y) {}

    private record Instruction(char turn, int steps) {}

    private List<Instruction> instructions;

    public Solver(String input) {
        super(input);
        
        this.instructions = Arrays.stream(input.split(", ")).map(e -> {
            char turn = e.charAt(0);
            int steps = Integer.parseInt(e.substring(1));
            return new Instruction(turn, steps);
        }).toList();
    }

    @Override
    public String solvePart1() {
        Node currentPosition = new Node(0, 0);
        Direction currentDirection = Direction.NORTH;
        for (Instruction instruction : this.instructions) {
            if (instruction.turn == 'L') {
                currentDirection = currentDirection.turnLeft(90);
            } else {
                currentDirection = currentDirection.turnRight(90);
            }
            int newX = currentPosition.x() + currentDirection.getDx() * instruction.steps();
            int newY = currentPosition.y() + currentDirection.getDy() * instruction.steps();
            currentPosition = new Node(newX, newY);
        }
        return Integer.toString(Math.abs(currentPosition.x()) + Math.abs(currentPosition.y()));
    }

    @Override
    public String solvePart2() {
        Node currentPosition = new Node(0, 0);
        Direction currentDirection = Direction.NORTH;
        HashSet<Node> visited = new HashSet<>();
        visited.add(currentPosition);
        for (Instruction instruction : this.instructions) {
            if (instruction.turn == 'L') {
                currentDirection = currentDirection.turnLeft(90);
            } else {
                currentDirection = currentDirection.turnRight(90);
            }
            for (int i = 0; i < instruction.steps(); i++) {
                int newX = currentPosition.x() + currentDirection.getDx();
                int newY = currentPosition.y() + currentDirection.getDy();
                currentPosition = new Node(newX, newY);
                if (visited.contains(currentPosition)) {
                    return Integer.toString(
                        Math.abs(currentPosition.x()) + 
                        Math.abs(currentPosition.y()));
                }
                visited.add(currentPosition);
            }
        }
        throw new IllegalStateException("Could not solve part2");
    }
}
