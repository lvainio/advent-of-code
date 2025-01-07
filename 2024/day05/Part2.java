import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Part2 {
    public void solve(
        HashMap<Integer, HashSet<Integer>> orderingRulesMap, 
        List<List<Integer>> updates
    ) {
        int sum = updates.stream()
            .filter(update -> !isUpdateCorrectlyOrdered(orderingRulesMap, update))
            .map(update -> orderUpdate(orderingRulesMap, update))
            .map(update -> update.get(update.size() / 2))
            .reduce(0, Integer::sum);

        System.out.println("Part2: " + sum);
    }

    private boolean isUpdateCorrectlyOrdered(
        HashMap<Integer, HashSet<Integer>> orderingRulesMap, 
        List<Integer> update
    ) {
        for (int i = 0; i < update.size(); i++) {
            for (int j = i + 1; j < update.size(); j++) {
                if (orderingRulesMap.get(update.get(j)).contains(update.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Integer> orderUpdate(
        HashMap<Integer, HashSet<Integer>> orderingRulesMap, 
        List<Integer> update
    ) {
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
