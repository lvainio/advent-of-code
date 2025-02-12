package leo.aoc.year2015.day12;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    public Solver(String input) {
        super(input);
    }

    @Override
    public String solvePart1() {
        Pattern pattern = Pattern.compile("[-]?\\d+");
        Matcher matcher = pattern.matcher(this.input);

        int sum = 0;
        while (matcher.find()) {
            sum += Integer.parseInt(matcher.group());
        }
        return Integer.toString(sum);
    }

    @Override
    public String solvePart2() {
        ObjectMapper objectMapper = new ObjectMapper();
        int total = 0;
        try {
            JsonNode jsonNode = objectMapper.readTree(this.input);
            total = sum(jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return Integer.toString(total);
    }

    public int sum(JsonNode node) {
        if (node.isInt()) {
            return node.asInt();
        }
        if (node.isTextual()) {
            return 0;
        }
        if (node.isArray()) {
            int sum = 0;
            for (JsonNode childNode : node) {
                sum += sum(childNode);
            }
            return sum;
        }
        if (node.isObject()) {
            // Check if any value is "red", return 0 in that case.
            Iterator<Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Entry<String, JsonNode> field = fields.next();
                JsonNode value = field.getValue();
                if (value.isTextual() && value.textValue().equals("red")) {
                    return 0;
                }
            }
            // No value was red, search children.
            fields = node.fields();
            int sum = 0;
            while (fields.hasNext()) {
                Entry<String, JsonNode> field = fields.next();
                JsonNode value = field.getValue();
                sum += sum(value);
            }
            return sum;
        }
        throw new IllegalStateException("Something has not been accounted for!");
    }
}
