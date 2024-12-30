import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String inputFilePath = "input.txt";
        List<List<Integer>> reports = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Integer> report = new ArrayList<>();
                String[] levels = line.split(" ");
                for (String level : levels) {
                    try {
                        report.add(Integer.valueOf(level));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number: " + level);
                    }
                }
                reports.add(report);
            }
        } catch (IOException e) {
            System.out.println("Could not read file " + inputFilePath);
        }

        Part1 p1 = new Part1();
        Part2 p2 = new Part2();

        p1.solve(reports);
        p2.solve(reports);
    }
}