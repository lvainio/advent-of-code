package leo.aoc.year2015.day16;

import java.util.HashMap;
import java.util.List;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private List<String> sues;

    public Solver(String input) {
        super(input);

        this.sues = input.lines().toList();
    }

    @Override
    public String solvePart1() {
        HashMap<String, Integer> itemCounts = new HashMap<>();
        itemCounts.put("children", 3);
        itemCounts.put("cats", 7);
        itemCounts.put("samoyeds", 2);
        itemCounts.put("pomeranians", 3);
        itemCounts.put("akitas", 0);
        itemCounts.put("vizslas", 0);
        itemCounts.put("goldfish", 5);
        itemCounts.put("trees", 3);
        itemCounts.put("cars", 2);
        itemCounts.put("perfumes", 1);

        String sue = this.sues.stream().filter(line -> {
            String[] parts = line.split("\\s+");

            String item1 = parts[2].substring(0, parts[2].length() - 1);
            int count1 = Integer.parseInt(parts[3].substring(0, parts[3].length() - 1));

            String item2 = parts[4].substring(0, parts[4].length() - 1);
            int count2 = Integer.parseInt(parts[5].substring(0, parts[5].length() - 1));

            String item3 = parts[6].substring(0, parts[6].length() - 1);
            int count3 = Integer.parseInt(parts[7]);

            return itemCounts.get(item1) == count1
                    && itemCounts.get(item2) == count2
                    && itemCounts.get(item3) == count3;
        }).findFirst().orElse(null);

        return sue.split(":?\\s+")[1];
    }

    @Override
    public String solvePart2() {
        HashMap<String, Integer> itemCounts = new HashMap<>();
        itemCounts.put("children", 3);
        itemCounts.put("cats", 7);
        itemCounts.put("samoyeds", 2);
        itemCounts.put("pomeranians", 3);
        itemCounts.put("akitas", 0);
        itemCounts.put("vizslas", 0);
        itemCounts.put("goldfish", 5);
        itemCounts.put("trees", 3);
        itemCounts.put("cars", 2);
        itemCounts.put("perfumes", 1);

        String sue = this.sues.stream().filter(line -> {
            String[] parts = line.split("\\s+");

            String item1 = parts[2].substring(0, parts[2].length() - 1);
            int count1 = Integer.parseInt(parts[3].substring(0, parts[3].length() - 1));

            String item2 = parts[4].substring(0, parts[4].length() - 1);
            int count2 = Integer.parseInt(parts[5].substring(0, parts[5].length() - 1));

            String item3 = parts[6].substring(0, parts[6].length() - 1);
            int count3 = Integer.parseInt(parts[7]);

            List<String> items = List.of(item1, item2, item3);
            List<Integer> counts = List.of(count1, count2, count3);
            for (int i = 0; i < 3; i++) {
                String item = items.get(i);
                int count = counts.get(i);
                if (item.equals("cats") || item.equals("trees")) {
                    if (count < itemCounts.get(item)) {
                        return false;
                    }
                } else if (item.equals("pomeranians") || item.equals("goldfish")) {
                    if (count > itemCounts.get(item)) {
                        return false;
                    }
                } else {
                    if (count != itemCounts.get(item)) {
                        return false;
                    }
                }
            }
            return true;
        }).findFirst().orElse(null);
        return sue.split(":?\\s+")[1];
    }
}
