package me.vainio.aoc.client;

import java.net.http.HttpClient;
import me.vainio.aoc.cache.AocCache;
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

    System.out.println("\nAdvent of Code Client\n");
    System.out.println(config + "\n");

    final AocCache cache = new AocCache();
    final HttpClient httpClient = HttpClient.newHttpClient();
    final AocClient aocClient = new AocClient(cache, sessionCookie, httpClient);

    if (config.fetchInput()) {
      System.out.println("Fetching input...");
      try {
        aocClient.fetchInput(config.year(), config.day());
      } catch (Exception e) {
        System.err.println("Failed to fetch input: " + e.getMessage());
        System.exit(1);
      }
    }

    if (config.postPart1()) {
      System.out.println("Posting part1...");
      try {
        aocClient.submitAnswer(config.year(), config.day(), 1);
      } catch (Exception e) {
        System.err.println("Failed to submit part 1 answer: " + e.getMessage());
        System.exit(1);
      }
    }

    if (config.postPart2()) {
      System.out.println("Posting part2...");
      try {
        aocClient.submitAnswer(config.year(), config.day(), 2);
      } catch (Exception e) {
        System.err.println("Failed to submit part 2 answer: " + e.getMessage());
        System.exit(1);
      }
    }
  }
}
