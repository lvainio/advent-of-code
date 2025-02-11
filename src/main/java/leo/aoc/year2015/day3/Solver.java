package leo.aoc.year2015.day3;

import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private enum Move {
        NORTH(0, 1),
        SOUTH(0, -1),
        WEST(-1, 0),
        EAST(1, 0);
    
        private final int dx;
        private final int dy;
    
        Move(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    
        public int getDx() {
            return dx;
        }
    
        public int getDy() {
            return dy;
        }
    
        public static Move fromChar(char c) {
            return switch(c) {
                case '^' -> NORTH;
                case 'v' -> SOUTH;
                case '<' -> WEST;
                case '>' -> EAST;
                default -> throw new IllegalArgumentException("Invalid move: " + c);
            };
        }
    }

    private record Point(int x, int y) {}

    public class Santa {

        private Point position;
        private HashSet<Point> visited;

        public Santa() {
            this.position = new Point(0, 0);
            this.visited = new HashSet<>();
            this.visited.add(this.position);
        }

        public void move(Move move) {
            int x = this.position.x();
            int y = this.position.y();
            int dx = move.getDx();
            int dy = move.getDy();
            int newX = x + dx;
            int newY = y + dy;
            this.position = new Point(newX, newY);
            this.visited.add(position);
        }

        public int getNumVisited() {
            return this.visited.size();
        }

        public HashSet<Point> getVisited() {
            return this.visited;
        }
    }

    private List<Move> moves;

    public Solver(String input) {
        super(input);
        
        this.moves = input.chars().mapToObj(c -> 
                Move.fromChar((char) c)
            ).toList(); 
    }

    @Override
    public String solvePart1() {
        Santa santa1 = new Santa();
        moves.forEach(santa1::move);
        return Integer.toString(santa1.getNumVisited());
    }

    @Override
    public String solvePart2() {
        Santa santa2 = new Santa();
        Santa roboSanta = new Santa();
        
        IntStream.range(0, moves.size())
            .filter(i -> i % 2 == 0)
            .mapToObj(moves::get)
            .forEach(santa2::move);

        IntStream.range(0, moves.size())
            .filter(i -> i % 2 == 1)
            .mapToObj(moves::get)
            .forEach(roboSanta::move);

        HashSet<Point> set1 = santa2.getVisited();
        HashSet<Point> set2 = roboSanta.getVisited();

        HashSet<Point> combined = new HashSet<>(set1);
        combined.addAll(set2);

        int numUnique = combined.size();
        
        return Integer.toString(numUnique);
    } 
}
