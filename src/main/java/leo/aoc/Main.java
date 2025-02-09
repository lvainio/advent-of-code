package leo.aoc;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        String year = null;
        String day = null;

        for (int i = 0; i < args.length; i+=2) {
            switch (args[i]) {
                case "-year" -> year = args[i + 1];
                case "-day" -> day = args[i + 1];
                default -> 
                    throw new IllegalArgumentException("Invalid command line flag: " + args[i]);
            }
        }

        System.out.println("\n*-----------------------------*");
        System.out.println("*   Solving Advent of Code!   *");
        System.out.println("*-----------------------------*\n");
        System.out.println("  Year:  " + year);
        System.out.println("  Day:   " + day + "\n");

        final String sessionCookie = System.getenv("AOC_SESSION");

        if (sessionCookie == null || sessionCookie.isEmpty()) {
            System.err.println("ERROR: no session cookie found!");
            System.err.println("HINT: make sure the AOC_SESSION environment variable is set.");
            System.exit(1);
        }
        System.out.println("  AOC Session cookie found!\n");

        String input = null;
        try {
            input = retrieveInput(year, day, sessionCookie);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (input == null || input.isEmpty()) {
            System.err.println("ERROR: input could not be retrieved");
            System.err.println("HINT: make sure the AOC_SESSION environment variable is set correctly.");
            System.exit(1);
        }
        System.out.println("  AOC input retrieved!\n");

        String part1 = null;
        String part2 = null;
        try {
            String className = "leo.aoc.year" + year + ".day" + day + ".Solver"; 
            Class<?> solverClass = Class.forName(className);
            if (AbstractSolver.class.isAssignableFrom(solverClass)) {
                Object solverInstance = solverClass.getConstructor(String.class).newInstance(input);
                part1 = invokeMethod(solverInstance, "solvePart1");
                part2 = invokeMethod(solverInstance, "solvePart2");
            } else {
                System.err.println("ERROR: The class does not implement AbstractSolver!");
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("  Part1: " + part1);
        System.out.println("  part2: " + part2 + "\n");
        System.out.println("*-----------------------------*\n");
    }

    private static String retrieveInput(
        String year, 
        String day, 
        String cookie
    ) throws IOException, InterruptedException {
        URI uri = URI.create("https://adventofcode.com/" + year + "/day/" + day + "/input");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Cookie", "session=" + cookie)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Failed to fetch the input data, HTTP response code: " + response.statusCode());
        }
        return response.body().trim(); 
    }

    private static String invokeMethod(
        Object instance, 
        String methodName
    ) throws 
        NoSuchMethodException, 
        IllegalAccessException, 
        InvocationTargetException 
    {
        Method method = instance.getClass().getMethod(methodName);
        String result = (String) method.invoke(instance);
        return result; 
    }
}
