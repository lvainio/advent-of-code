package leo.aoc;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        HashMap<String, String> parsedArgs = parseArgs(args);
        final String year = parsedArgs.get("year");
        final String day = parsedArgs.get("day");

        validateYear(year);
        validateDay(day);

        System.out.println("\n*-----------------------------*");
        System.out.println("*   Solving Advent of Code!   *");
        System.out.println("*-----------------------------*\n");
        System.out.println("  Year:  " + year);
        System.out.println("  Day:   " + day + "\n");

        final String sessionCookie = retrieveSessionCookie();

        final String input = retrieveInput(year, day, sessionCookie);

        String solutionPart1 = null;
        String solutionPart2 = null;

        Long durationPart1 = 0L;
        Long durationPart2 = 0L;

        try {
            String className = "leo.aoc.year" + year + ".day" + day + ".Solver"; 
            Class<?> solverClass = Class.forName(className);
            if (AbstractSolver.class.isAssignableFrom(solverClass)) {
                AbstractSolver solverInstance = 
                    (AbstractSolver) solverClass.getConstructor(String.class).newInstance(input);

                long startPart1 = System.nanoTime();
                solutionPart1 = solverInstance.solvePart1();
                long endPart1 = System.nanoTime();
                durationPart1 = endPart1 - startPart1;

                long startPart2 = System.nanoTime();
                solutionPart2 = solverInstance.solvePart2();
                long endPart2 = System.nanoTime();
                durationPart2 = endPart2 - startPart2;
            } else {
                System.err.println("ERROR: The class does not implement AbstractSolver!");
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        System.out.println("  Part1: " + solutionPart1);
        System.out.println("  part2: " + solutionPart2 + "\n");
        System.out.println("  Time part1: " + durationPart1 / 1_000_000.0 + " ms");
        System.out.println("  Time part2: " + durationPart2 / 1_000_000.0 + " ms\n");
        System.out.println("*-----------------------------*\n");
    }

    private static HashMap<String, String> parseArgs(String[] args) {
        HashMap<String, String> parsedArgs = new HashMap<>();
        for (int i = 0; i < args.length; i+=2) {
            switch (args[i]) {
                case "-year" -> parsedArgs.put("year", args[i + 1]);
                case "-day" -> parsedArgs.put("day", args[i + 1]);
                default -> 
                    throw new IllegalArgumentException("Invalid command line flag: " + args[i]);
            }
        }
        return parsedArgs;
    }

    private static void validateYear(String yearStr) {
        try {
            int year = Integer.parseInt(yearStr);
            if (year < 2015 || year > 2024) {
                throw new IllegalArgumentException("Year must be between 2015 and 2024.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid year format. Must be a number.");
        }
    }

    private static void validateDay(String dayStr) {
        try {
            int day = Integer.parseInt(dayStr);
            if (day < 1 || day > 25) {
                throw new IllegalArgumentException("Day must be between 1 and 25.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid day format. Must be a number.");
        }
    }

    private static String retrieveSessionCookie() {
        String sessionCookie = System.getenv("AOC_SESSION");
        if (sessionCookie == null || sessionCookie.isEmpty()) {
            throw new IllegalArgumentException("AOC_SESSION environment variable not set.");
        }
        return sessionCookie;
    }

    private static String retrieveInput(String year, String day, String cookie) {
        URI uri = URI.create("https://adventofcode.com/" + year + "/day/" + day + "/input");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Cookie", "session=" + cookie)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
        final int statusCode = response.statusCode();
        if (statusCode != 200) {
            System.err.println("ERROR: failed to fetch input data, status code: " + statusCode);
            System.exit(1);
        }
        final String input = response.body().trim();
        if (input == null || input.isEmpty()) {
            System.err.println("ERROR: failed to retrieve input data, input:" + input);
            System.exit(1);
        }
        System.out.println("  AOC input retrieved!\n");
        return input; 
    }
}
