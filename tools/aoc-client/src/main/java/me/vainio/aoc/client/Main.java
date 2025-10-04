package me.vainio.aoc.client;

import picocli.CommandLine;

public final class Main {

    public static void main(String[] args) {
        final ArgsParser parser = new ArgsParser();
        final int exitCode = new CommandLine(parser).execute(args);
        if (exitCode != 0 || parser.getConfig() == null) {
            System.exit(exitCode);
        }
        final AocConfig config = parser.getConfig();
        
        String sessionCookie = System.getenv("AOC_SESSION");
        if (sessionCookie == null || sessionCookie.trim().isEmpty()) {
            System.err.println("ERROR: AOC_SESSION environment variable is not set");
            System.exit(1);
        }
        sessionCookie = sessionCookie.trim();
        
        System.out.println("*-----------------------------*");
        System.out.println("*     Advent of Code Client   *");
        System.out.println("*-----------------------------*\n");
        
        final AocClient aocClient = new AocClient();
        System.out.println("FETCHING IF SET");
        System.out.println("POST IF SET");
    }
}