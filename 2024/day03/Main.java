import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Path inputFilePath = Path.of("input.txt");

        try {
            String content = Files.readString(inputFilePath);

            Part1 p1 = new Part1();
            Part2 p2 = new Part2();

            p1.solve(content);
            p2.solve(content);

        } catch (IOException e) {
            System.out.println("Could not read file " + inputFilePath.toString());
        }
    }
}