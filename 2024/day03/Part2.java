import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {
    public void solve(String content) {
        String regex = "(mul\\((\\d{1,3}),(\\d{1,3})\\))|(do\\(\\))|(don't\\(\\))";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        int sum = 0;
        boolean mulEnabled = true;

        while (matcher.find()) {
            String instruction = matcher.group();

            switch (instruction) {
                case "do()" -> mulEnabled = true;
                case "don't()" -> mulEnabled = false;
                default -> {
                    if (mulEnabled) {
                        int factor1 = Integer.parseInt(matcher.group(2));
                        int factor2 = Integer.parseInt(matcher.group(3));
                        sum += factor1 * factor2;
                    }
                }
            }
        }
        System.out.println("Part2: " + sum);
    }
}
