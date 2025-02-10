package leo.aoc.year2024.day2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private List<List<Integer>> reports;

    public Solver(String input) {
        super(input);

        this.reports = this.input.lines()
                .map(line -> line.split("\\s+")) 
                .map(nums -> 
                    List.of(nums).stream().map(Integer::parseInt).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    @Override
    public String solvePart1() {
        long numSafe = this.reports.stream().filter(report -> isSafePart1(report)).count();
        return Long.toString(numSafe);
    }

    @Override
    public String solvePart2() {
        long numSafe = this.reports.stream().filter(report -> isSafePart2(report)).count();
        return Long.toString(numSafe);
    }

    private boolean isSafePart1(List<Integer> report) {
        return (isAscending(report) || isDescending(report)) && hasSafeDiffs(report);
    }

    private boolean isSafePart2(List<Integer> report) {
        if ((isAscending(report) || isDescending(report)) && hasSafeDiffs(report)) {
            return true;
        }
        for (int i = 0; i < report.size(); i++) {
            List<Integer> subReport = new ArrayList<>(report);
            subReport.remove(i); 
            if ((isAscending(subReport) || isDescending(subReport)) && hasSafeDiffs(subReport)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAscending(List<Integer> report) {
        if (report.size() <= 1) {
            return true; 
        }
        for (int i = 0; i < report.size() - 1; i++) {
            int first = report.get(i);        
            int second = report.get(i + 1);   
            if (first >= second) {
                return false;
            }
        }
        return true;
    }

    private boolean isDescending(List<Integer> report) {
        if (report.size() <= 1) {
            return true; 
        }
        for (int i = 0; i < report.size() - 1; i++) {
            int first = report.get(i);        
            int second = report.get(i + 1);   
            if (first <= second) {
                return false;
            }
        }
        return true;
    }

    private boolean hasSafeDiffs(List<Integer> report) {
        if (report.size() <= 1) {
            return true; 
        }
        for (int i = 0; i < report.size() - 1; i++) {
            int first = report.get(i);        
            int second = report.get(i + 1);   
            int diff = Math.abs(first - second);
            if (diff < 1 || diff > 3) {
                return false;
            }
        }
        return true;
    }
}
