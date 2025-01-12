package com.example.day13_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.regex.*;

public class InputParser {

    private List<ClawMachine> clawMachines = null;

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
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
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private int getNext(Matcher matcher) {
        matcher.find();
        return Integer.parseInt(matcher.group());
    }

    public List<ClawMachine> getClawMachines() {
        return this.clawMachines;
    }
}
