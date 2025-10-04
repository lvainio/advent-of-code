package me.vainio.aoc.client;

public class Main {
    public static void main(String[] args) {

        // FIXME: add args parsing class. Need year, day. Also need
        // to separate posting results from input retrieval. 

        System.out.println("*-----------------------------*");
        System.out.println("*     Advent of Code Client   *");
        System.out.println("*-----------------------------*\n");
        System.out.println("Input for year " + args[0] + ", day " + args[1] + ":\n");

        final String year = args[0];
        final String day = args[1];

        final String sessionCookie = System.getenv("AOC_SESSION").trim();

        final String input = AocClient.downloadInput(year, day, sessionCookie);

        System.out.println(input);
    }
}