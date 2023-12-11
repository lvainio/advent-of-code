import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            int part1 = 0;
            int part2 = 0;
            while ((line = br.readLine()) != null) {
                Pattern pattern = Pattern.compile("^(\\d+)-(\\d+) (\\w): (.+)$");
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    int n1 = Integer.parseInt(matcher.group(1));
                    int n2 = Integer.parseInt(matcher.group(2));
                    char c = matcher.group(3).charAt(0);
                    String password = matcher.group(4);

                    int count = 0;
                    for (int i = 0; i < password.length(); i++) {
                        if (password.charAt(i) == c) {
                            count++;
                        }
                    }
                    if (n1 <= count && count <= n2) {
                        part1++;
                    }
                    if (password.charAt(n1-1) == c ^ password.charAt(n2-1) == c) {
                        part2++;
                    }
                }
            }
            System.out.println("part1: " + part1);
            System.out.println("part2: " + part2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}