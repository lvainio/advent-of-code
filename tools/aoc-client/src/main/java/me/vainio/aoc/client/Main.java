package me.vainio.aoc.client;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "aoc-client", mixinStandardHelpOptions = true, version = "1.0.0")
public class Main implements Callable<Integer> {
    
    @Option(names = {"-y", "--year"}, description = "Year (2015-2030)", required = true)
    private int year;
    
    @Option(names = {"-d", "--day"}, description = "Day (1-25)", required = true)
    private int day;
    
    @Option(names = {"-f", "--fetch-input"}, description = "Fetch input data (default)")
    private boolean fetchInput;
    
    @Option(names = {"-p", "--post-answer"}, description = "Post answer to AoC (not yet implemented)")
    private boolean postAnswer;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        if (year < 2015 || year > 2025) {
            System.err.println("ERROR: Year must be between 2015 and 2025, got: " + year);
            return 1;
        }
        
        if (day < 1 || day > 25) {
            System.err.println("ERROR: Day must be between 1 and 25, got: " + day);
            return 1;
        }
        
        // Default to fetch input if no operation specified
        if (!fetchInput && !postAnswer) {
            fetchInput = true;
        }
        
        // Validate session cookie
        final String sessionCookie = System.getenv("AOC_SESSION");
        if (sessionCookie == null || sessionCookie.trim().isEmpty()) {
            System.err.println("ERROR: AOC_SESSION environment variable is not set");
            return 1;
        }
        
        System.out.println("*-----------------------------*");
        System.out.println("*     Advent of Code Client   *");
        System.out.println("*-----------------------------*\n");
        
        if (fetchInput) {
            fetchInput(String.valueOf(year), String.valueOf(day), sessionCookie.trim());
        } else if (postAnswer) {
            System.out.println("Answer posting functionality not yet implemented.");
            System.out.println("Year: " + year + ", Day: " + day);
        }
        
        return 0;
    }
    
    private void fetchInput(String year, String day, String sessionCookie) {
        System.out.println("Input for year " + year + ", day " + day + ":\n");
        final String input = AocClient.downloadInput(year, day, sessionCookie);
        System.out.println(input);
    }
}