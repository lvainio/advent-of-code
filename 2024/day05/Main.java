import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Path inputFilePath = Path.of("input.txt");

        try {
            String[] content = Files.readString(inputFilePath).split("\r?\n\r?\n");
            
            List<List<Integer>> orderingRules = Arrays.stream(content[0].split("\r?\n"))
                .map(line -> Arrays.stream(line.split("\\|")) 
                                .map(Integer::parseInt)   
                                .collect(Collectors.toList())) 
                .collect(Collectors.toList());

            HashMap<Integer, HashSet<Integer>> orderingRulesMap = new HashMap<>();
            for (List<Integer> tuple : orderingRules) {
                int x = tuple.get(0);
                int y = tuple.get(1);
                orderingRulesMap
                    .computeIfAbsent(x, k -> new HashSet<>())  
                    .add(y);
            }

            List<List<Integer>> updates = Arrays.stream(content[1].split("\r?\n"))
                .map(line -> Arrays.stream(line.split(",")) 
                                .map(Integer::parseInt)   
                                .collect(Collectors.toList())) 
                .collect(Collectors.toList()); 

            Part1 p1 = new Part1();
            Part2 p2 = new Part2();

            p1.solve(orderingRulesMap, updates);
            p2.solve();

        } catch (IOException e) {
            System.out.println("Could not read file " + inputFilePath.toString());
        }
    }
}
