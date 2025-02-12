package leo.aoc.year2015.day9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    HashMap<String, HashMap<String, Integer>> cityGraph;

    public Solver(String input) {
        super(input);
        
        HashMap<String, HashMap<String, Integer>> cityGraph = new HashMap<>();
        input.lines().forEach(line -> {
            String[] parts = line.split("\\s+");

            String city1 = parts[0];
            String city2 = parts[2];
            int distance = Integer.parseInt(parts[4]);

            cityGraph.putIfAbsent(city1, new HashMap<>());
            cityGraph.putIfAbsent(city2, new HashMap<>());

            cityGraph.get(city1).put(city2, distance);
            cityGraph.get(city2).put(city1, distance);
        });
        this.cityGraph = cityGraph;
    }

    @Override
    public String solvePart1() {
        Set<String> cities = this.cityGraph.keySet();

        List<List<String>> allPaths = new ArrayList<>();
        generateAllPaths(new ArrayList<>(), cities, allPaths);

        int minCost = allPaths.stream()
            .mapToInt(this::computeCost)
            .min()
            .orElse(Integer.MAX_VALUE);

        return Integer.toString(minCost);
    }

    @Override
    public String solvePart2() {
        Set<String> cities = this.cityGraph.keySet();

        List<List<String>> allPaths = new ArrayList<>();
        generateAllPaths(new ArrayList<>(), cities, allPaths);

        int maxCost = allPaths.stream()
            .mapToInt(this::computeCost)
            .max()
            .orElse(Integer.MAX_VALUE);
            
        return Integer.toString(maxCost);
    }

    public void generateAllPaths(
        List<String> path, 
        Set<String> citiesLeft, 
        List<List<String>> allPaths
    ) {
        if (citiesLeft.size() == 0) {
            allPaths.add(path);
        }
        for (String city : citiesLeft) {
            List<String> newPath = new ArrayList<>(path);
            newPath.add(city);
            Set<String> newCitiesLeft = new HashSet<>(citiesLeft);
            newCitiesLeft.remove(city);
            generateAllPaths(newPath, newCitiesLeft, allPaths);
        }
    }

    public int computeCost(List<String> path) {
        int totalCost = 0;
        for (int i = 0; i < path.size()-1; i++) {
            String city1 = path.get(i);
            String city2 = path.get(i+1);
            totalCost += this.cityGraph.get(city1).get(city2);
        }
        return totalCost;
    }
}
