package com.example.day23_2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day23 {

    public static void main(String[] args) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        HashMap<String, HashSet<String>> network = parser.getNetwork();

        Set<Set<String>> triples = findFullyConnectedTriples(network);

        // Part1
        int count = 0;
        for (Set<String> triple : triples) {
            for (String node : triple) {
                if (node.startsWith("t")) {
                    count++;
                    break;
                }
            }
        }
        System.out.println("Part1: " + count);

        // Part2
        Set<String> largestClique = findLargestClique(network, 14);
        List<String> lanParty = new ArrayList<>(largestClique);
        Collections.sort(lanParty);
        System.out.println(lanParty.stream().collect(Collectors.joining(",")));
    }

    public static Set<Set<String>> findFullyConnectedTriples(
        HashMap<String, HashSet<String>> network) 
    {
        Set<Set<String>> triples = new HashSet<>();
        for (String node : network.keySet()) {
            for (String neighbor : network.get(node)) {
                HashSet<String> commonNeighbors = new HashSet<>(network.get(node));
                commonNeighbors.retainAll(network.get(neighbor));
                for (String commonNeighbor : commonNeighbors) {
                    triples.add(Set.of(node, neighbor, commonNeighbor));
                }
            }
        }
        return triples;
    }

    public static Set<String> findLargestClique(
        HashMap<String, HashSet<String>> network, 
        int maxCliqueSize
    ) {
        Set<String> largestClique = new HashSet<>();
        
        Set<String> R = new HashSet<>();
        Set<String> P = new HashSet<>(network.keySet());
        Set<String> X = new HashSet<>();

        bronKerbosch(network, R, P, X, largestClique);

        return largestClique;
    }

    private static void bronKerbosch(
        HashMap<String, HashSet<String>> network, 
        Set<String> R, 
        Set<String> P, 
        Set<String> X, 
        Set<String> largestClique
    ) {
        if (P.isEmpty() && X.isEmpty()) {
            if (R.size() > largestClique.size() ) {
                largestClique.clear();
                largestClique.addAll(R);
            }
        }

        Iterator<String> iterator = new HashSet<>(P).iterator();
        while (iterator.hasNext()) {
            String v = iterator.next();

            Set<String> newR = new HashSet<>(R);
            newR.add(v);

            Set<String> newP = new HashSet<>(P);
            newP.retainAll(network.get(v));

            Set<String> newX = new HashSet<>(X);
            newX.retainAll(network.get(v));

            bronKerbosch(network, newR, newP, newX, largestClique);

            P.remove(v);
            X.add(v);
        }
    }
}
