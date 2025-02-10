package leo.aoc.year2024.day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private HashMap<Integer, HashSet<Integer>> orderingRulesMap;
    private List<List<Integer>> updates;

    public Solver(String input) {
        super(input);

        String[] content = input.split("\r?\n\r?\n");
            
        List<List<Integer>> orderingRules = Arrays.stream(content[0].split("\r?\n"))
            .map(line -> Arrays.stream(line.split("\\|")) 
                            .map(Integer::parseInt)   
                            .collect(Collectors.toList())) 
            .collect(Collectors.toList());

        this.orderingRulesMap = new HashMap<>();
        for (List<Integer> tuple : orderingRules) {
            int x = tuple.get(0);
            int y = tuple.get(1);
            orderingRulesMap
                .computeIfAbsent(x, _ -> new HashSet<>())  
                .add(y);
        }

        this.updates = Arrays.stream(content[1].split("\r?\n"))
            .map(line -> Arrays.stream(line.split(",")) 
                            .map(Integer::parseInt)   
                            .collect(Collectors.toList())) 
            .collect(Collectors.toList()); 
    }
    
    @Override
    public String solvePart1() {
        int sum = this.updates.stream()
            .filter(update -> isUpdateCorrectlyOrdered(update))
            .map(update -> update.get(update.size() / 2))
            .reduce(0, Integer::sum);
        return Integer.toString(sum);
    }

    @Override
    public String solvePart2() {
        int sum = this.updates.stream()
            .filter(update -> !isUpdateCorrectlyOrdered(update))
            .map(update -> orderUpdate(update))
            .map(update -> update.get(update.size() / 2))
            .reduce(0, Integer::sum);
        return Integer.toString(sum);
    }

    private boolean isUpdateCorrectlyOrdered(List<Integer> update) {
        for (int i = 0; i < update.size(); i++) {
            for (int j = i + 1; j < update.size(); j++) {
                if (this.orderingRulesMap.get(update.get(j)).contains(update.get(i))) {
                    return false;
                }
            }
        }
        return true;
    } 

    private List<Integer> orderUpdate(List<Integer> update) {
        List<Integer> updateOrdered = new ArrayList<>(update);
        updateOrdered.sort((num1, num2) -> {
            if (orderingRulesMap.get(num1) != null && orderingRulesMap.get(num1).contains(num2)) {
                return -1; 
            } else if (orderingRulesMap.get(num2) != null && orderingRulesMap.get(num2).contains(num1)) {
                return 1;  
            }
            return 0;  
        });
        return updateOrdered;
    }
}
