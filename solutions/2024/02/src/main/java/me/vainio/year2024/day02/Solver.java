package me.vainio.year2024.day02;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solver {
    
    final private List<List<Integer>> reports;

    public static void main(String[] args) {  
        try {
            final String input = new String(System.in.readAllBytes(), "UTF-8").trim();
            final Solver solver = new Solver(input);
            System.out.println(solver.solvePart1());
            System.out.println(solver.solvePart2());
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
            System.exit(1);
        }
    }

    public Solver(String input) {
        this.reports = input.lines()
                .map(line -> line.split("\\s+")) 
                .map(nums -> 
                    List.of(nums).stream().map(Integer::parseInt).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public String solvePart1() {
        final long numSafe = this.reports.stream().filter(report -> isSafePart1(report)).count();
        return Long.toString(numSafe);
    }

    public String solvePart2() {
        final long numSafe = this.reports.stream().filter(report -> isSafePart2(report)).count();
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
            final int first = report.get(i);        
            final int second = report.get(i + 1);   
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
            final int first = report.get(i);        
            final int second = report.get(i + 1);   
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
            final int first = report.get(i);        
            final int second = report.get(i + 1);   
            final int diff = Math.abs(first - second);
            if (diff < 1 || diff > 3) {
                return false;
            }
        }
        return true;
    }
}
