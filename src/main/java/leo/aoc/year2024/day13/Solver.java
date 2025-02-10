package leo.aoc.year2024.day13;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

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
            .mapToLong(machine -> machine.calculateCheapestWin())
            .sum();
        return Long.toString(sum);
    }

    @Override
    public String solvePart2() {
        long sum = this.clawMachines.stream()
            .map(machine -> machine.addLongToPrizeCoordinates(10000000000000L))
            .mapToLong(machine -> machine.calculateCheapestWin())
            .sum();
        return Long.toString(sum);
    }  
}
