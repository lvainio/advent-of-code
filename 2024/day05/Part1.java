import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Part1 {

    public void solve(
        HashMap<Integer, HashSet<Integer>> orderingRulesMap, 
        List<List<Integer>> updates
    ) {
        int sum = 0;
        for (List<Integer> update : updates) {
            if (isUpdateCorrectlyOrdered(orderingRulesMap, update)) {
                sum += update.get(update.size() / 2);
            }
        }
        System.out.println("Part1: " + sum);
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
}
