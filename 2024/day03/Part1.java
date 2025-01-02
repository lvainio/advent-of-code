import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
    public void solve(String content) {
        String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        int sum = 0;
        while (matcher.find()) {
            int factor1 = Integer.parseInt(matcher.group(1));
            int factor2 = Integer.parseInt(matcher.group(2));

            sum += factor1 * factor2;
        }
        
        System.out.println("Part1: " + sum);
    }
}
