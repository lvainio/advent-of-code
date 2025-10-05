package me.vainio.aoc.client;

import picocli.CommandLine;

public final class Main {

    public static void main(String[] args) {
        final Cli cli = new Cli();
        final int exitCode = new CommandLine(cli).execute(args);
        if (exitCode != 0 || cli.getConfig() == null) {
            System.exit(exitCode);
        }
        final AocConfig config = cli.getConfig();
        
        String sessionCookie = System.getenv("AOC_SESSION");
        if (sessionCookie == null || sessionCookie.trim().isEmpty()) {
            System.err.println("ERROR: AOC_SESSION environment variable is not set");
            System.exit(1);
        }
        sessionCookie = sessionCookie.trim();
        
        System.out.println( "\nAdvent of Code Client\n");
        System.out.println(config + "\n");
        
        final AocClient aocClient = new AocClient();

        if (config.fetchInput()) {
            System.out.println("Fetching input...");
        }

        if (config.postPart1()) {
            System.out.println("Posting part1...");
        }

        if (config.postPart2()) {
            System.out.println("Posting part2...");
        }
    }
}