import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Path inputFilePath = Path.of("input.txt");

        try {
            String[] rows = Files.readString(inputFilePath).split("\r?\n");

            char[][] grid = Arrays.stream(rows)
                      .map(String::toCharArray)  
                      .toArray(char[][]::new);
            
            Part1 p1 = new Part1(grid);
            Part2 p2 = new Part2(grid);

            p1.solve();
            p2.solve();

        } catch (IOException e) {
            System.out.println("Could not read file " + inputFilePath.toString());
        }
    }
}
