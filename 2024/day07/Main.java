import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Path inputFilePath = Path.of("input.txt");

        long part1 = 0;
        long part2 = 0;
        try {
            String[] lines = Files.readString(inputFilePath).split("\r?\n");

            for (String line : lines) {
                String[] equation = line.split(":");

                long testValue = Long.parseLong(equation[0].trim());
                long[] nums = Arrays.stream(equation[1].trim().split(" "))
                     .mapToLong(Long::parseLong)  
                     .toArray();  

                if (isValidEquationPart1(testValue, nums, nums[0], 1)) {
                    part1 += testValue;
                }
                if (isValidEquationPart2(testValue, nums, nums[0], 1)) {
                    part2 += testValue;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read file " + inputFilePath.toString());
        }
        System.out.println("Part1: " + part1);
        System.out.println("Part2: " + part2);
    }

    public static boolean isValidEquationPart1(long testValue, long[] nums, long value, int idx) {
        if (testValue < value) {
            return false;
        } else if (idx == nums.length) { 
            return testValue == value;
        } else {
             return isValidEquationPart1(testValue, nums, value + nums[idx], idx+1) ||
                        isValidEquationPart1(testValue, nums, value * nums[idx], idx+1);
        }
    }

    public static boolean isValidEquationPart2(long testValue, long[] nums, long value, int idx) {
        if (testValue < value) {
            return false;
        } else if (idx == nums.length) { 
            return testValue == value;
        } else {
             return isValidEquationPart2(testValue, nums, value + nums[idx], idx+1) ||
                        isValidEquationPart2(testValue, nums, value * nums[idx], idx+1) ||
                        isValidEquationPart2(testValue, nums, Long.parseLong(value + "" + nums[idx]), idx+1);
        }
    }
}
