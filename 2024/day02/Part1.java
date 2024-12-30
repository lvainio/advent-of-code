import java.util.List;

public class Part1 {
    public void solve(List<List<Integer>> reports) {
        int numSafeReports = 0;
        for (List<Integer> report : reports) {
            if (isSafe(report)) {
                numSafeReports++;
            }
        }
        System.out.println("Part1: " + numSafeReports);
    }

    private boolean isSafe(List<Integer> report) {
        return (isAscending(report) || isDescending(report)) && hasSafeDiffs(report);
    }

    private boolean isAscending(List<Integer> report) {
        if (report.size() <= 1) {
            return true; 
        }
        for (int i = 0; i < report.size() - 1; i++) {
            int first = report.get(i);        
            int second = report.get(i + 1);   
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
            int first = report.get(i);        
            int second = report.get(i + 1);   
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
            int first = report.get(i);        
            int second = report.get(i + 1);   
            int diff = Math.abs(first - second);
            if (diff < 1 || diff > 3) {
                return false;
            }
        }
        return true;
    }
}
