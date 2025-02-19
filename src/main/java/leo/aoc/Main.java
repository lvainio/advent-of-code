package leo.aoc;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    private static final String CACHE_DIR = "aoc_cache";

    public static void main(String[] args) {
        HashMap<String, String> parsedArgs = parseArgs(args);
        
        final String year = parsedArgs.get("year");
        final String day = parsedArgs.get("day");
        final boolean postPart1 = parsedArgs.containsKey("p1");
        final boolean postPart2 = parsedArgs.containsKey("p2");

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

        if (postPart1) {
            System.out.println("  Posting part1");
            postAnswer(year, day, "1", solutionPart1, sessionCookie);
            System.out.println();
        }
        if (postPart2) {
            System.out.println("  Posting part2");
            postAnswer(year, day, "2", solutionPart2, sessionCookie);
            System.out.println();
        }
        
        System.out.println("  Part1: " + solutionPart1);
        System.out.println("  part2: " + solutionPart2 + "\n");
        System.out.println("  Time part1: " + durationPart1 / 1_000_000.0 + " ms");
        System.out.println("  Time part2: " + durationPart2 / 1_000_000.0 + " ms\n");
        System.out.println("*-----------------------------*\n");
    }

    private static HashMap<String, String> parseArgs(String[] args) {
        HashMap<String, String> parsedArgs = new HashMap<>();
        final String year = System.getProperty("year");
        final String day = System.getProperty("day");
        parsedArgs.put("year", year);
        parsedArgs.put("day", day);
        if (Arrays.asList(args).contains("-p1")) {
            parsedArgs.put("p1", "true");
        }
        if (Arrays.asList(args).contains("-p2")) {
            parsedArgs.put("p2", "true");
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
        final String fileName = CACHE_DIR + "/year" + year + "day" + day + ".txt";
        File cacheFile = new File(fileName);

        String input = null;
        if (cacheFile.exists()) {
            try {
                input = new String(Files.readAllBytes(cacheFile.toPath()), StandardCharsets.UTF_8);
                System.out.println("  Input retrieved from cache!\n");
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } else {
            input = retrieveInputFromAPI(year, day, cookie);
            try {
                Files.createDirectories(Paths.get(CACHE_DIR));
                Files.write(Paths.get(fileName), input.getBytes(StandardCharsets.UTF_8));
                System.out.println("  Input retrieved from API!\n");
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return input;
    }

    private static String retrieveInputFromAPI(String year, String day, String cookie) {
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
        return input; 
    }

    private static void postAnswer(
        String year, 
        String day, 
        String part, 
        String answer,
        String cookie
    ) {
        try {
            URI uri = URI.create("https://adventofcode.com/" + year + "/day/" + day + "/answer");
            String payload = "level=" + URLEncoder.encode(part, StandardCharsets.UTF_8) +
                              "&answer=" + URLEncoder.encode(answer, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Cookie", "session=" + cookie)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("  Answer posted successfully!");
            } else {
                System.out.println("  Failed to post answer. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
