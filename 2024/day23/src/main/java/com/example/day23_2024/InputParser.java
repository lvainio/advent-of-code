package com.example.day23_2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

public class InputParser {

    private HashMap<String, HashSet<String>> network = null;

    public HashMap<String, HashSet<String>> getNetwork() {
        return this.network;
    }
    
    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            HashMap<String, HashSet<String>> network = new HashMap<>();
            reader.lines().forEach(line -> {
                String[] edge = line.split("-");
                String n1 = edge[0].trim();
                String n2 = edge[1].trim();
                network.computeIfAbsent(n1, _ -> new HashSet<>()).add(n2);
                network.computeIfAbsent(n2, _ -> new HashSet<>()).add(n1);
            });
            this.network = network;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
