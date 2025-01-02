import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Path inputFilePath = Path.of("input.txt");

        try {
            String[] lines = Files.readString(inputFilePath).split("\r?\n");
            char[][] grid = new char[lines.length][];

            for (int i = 0; i < lines.length; i++) {
                grid[i] = lines[i].toCharArray();
            }

            Part1 p1 = new Part1();
            Part2 p2 = new Part2();

            p1.solve(grid);
            p2.solve(grid);

        } catch (IOException e) {
            System.out.println("Could not read file " + inputFilePath.toString());
        }
    }
}
