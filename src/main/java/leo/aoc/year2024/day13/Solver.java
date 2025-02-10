package leo.aoc.year2024.day13;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private record ClawMachine(
        long ax,
        long ay, 
        long bx, 
        long by, 
        long px,
        long py
    ) {
        public long calculateCheapestWin() {
            double aPresses = (double) (this.by * this.px - this.bx * this.py) / 
                                (this.ax * this.by - this.ay * this.bx);
            double bPresses = (double) (this.px - aPresses * this.ax) / this.bx;
            if (aPresses >= 0.0 && bPresses >= 0.0 && aPresses % 1.0 == 0.0 && bPresses % 1 == 0.0) {
                return (long) (aPresses * 3.0 + bPresses);
            }
            return 0;
        }

        public ClawMachine addLongToPrizeCoordinates(long num) {
            return new ClawMachine(
                this.ax, 
                this.ay, 
                this.bx, 
                this.by, 
                this.px + num, 
                this.py + num);
        }
    }

    private final List<ClawMachine> clawMachines;

    public Solver(String input) {
        super(input);

        List<String> lines = input.lines().collect(Collectors.toList());
        List<ClawMachine> clawMachines = IntStream.iterate(0, idx -> idx < lines.size(), idx -> idx + 4)
            .mapToObj(idx -> {
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcherButtonA = pattern.matcher(lines.get(idx));
                int ax = this.getNext(matcherButtonA);
                int ay = this.getNext(matcherButtonA);
                Matcher matcherButtonB = pattern.matcher(lines.get(idx+1));
                int bx = this.getNext(matcherButtonB);
                int by = this.getNext(matcherButtonB);
                Matcher matcherPrice = pattern.matcher(lines.get(idx+2));
                int px = this.getNext(matcherPrice);
                int py = this.getNext(matcherPrice);
                return new ClawMachine(ax, ay, bx, by, px, py);
            }).collect(Collectors.toList());
        this.clawMachines = clawMachines;
    }

    private int getNext(Matcher matcher) {
        matcher.find();
        return Integer.parseInt(matcher.group());
    }

    @Override
    public String solvePart1() {
        long sum = this.clawMachines.stream()
            .mapToLong(ClawMachine::calculateCheapestWin)
            .sum();
        return Long.toString(sum);
    }

    @Override
    public String solvePart2() {
        long sum = this.clawMachines.stream()
            .map(machine -> machine.addLongToPrizeCoordinates(10000000000000L))
            .mapToLong(ClawMachine::calculateCheapestWin)
            .sum();
        return Long.toString(sum);
    }  
}
