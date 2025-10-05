package me.vainio.aoc.client;

import picocli.CommandLine;

public final class Main {

    public static void main(String[] args) {
        final CLI cli = new CLI();
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
        
        System.out.println("*-----------------------------*");
        System.out.println("*     Advent of Code Client   *");
        System.out.println("*-----------------------------*\n");

        System.out.println("  Year:  " + config.year());
        System.out.println("  Day:   " + config.day());
        System.out.println("  Fetch input: " + config.fetchInput());
        System.out.println("  Post part1: " + config.postPart1());
        System.out.println("  Post part2: " + config.postPart2());
        System.out.println();
        
        final AocClient aocClient = new AocClient();
        System.out.println("FETCHING IF SET");
        System.out.println("POST IF SET");
    }
}