package me.vainio.aoc.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import me.vainio.aoc.cache.AocCache;

final class AocClient {
  private static final String BASE_URL = "https://adventofcode.com/";

  private final AocCache cache;
  private final String sessionCookie;
  private final HttpClient httpClient;

  public AocClient(final AocCache cache, final String sessionCookie, final HttpClient httpClient) {
    this.cache = Objects.requireNonNull(cache);
    this.sessionCookie = Objects.requireNonNull(sessionCookie);
    this.httpClient = Objects.requireNonNull(httpClient);
  }

  public void fetchInput(final int year, final int day) throws IOException, InterruptedException {
    if (cache.hasInput(year, day)) {
      System.out.println("Input already cached");
      return;
    }
    System.out.println("Input not cached, fetching from API");
    final String input = fetchInputFromApi(year, day);
    cache.saveInput(year, day, input);
    System.out.println("Input fetched and cached");
  }

  public void submitAnswer(final int year, final int day, final int part)
      throws IOException, InterruptedException {
    if (!cache.hasAnswer(year, day, part)) {
      System.out.println("No answer cached for year " + year + " day " + day + " part " + part);
      return;
    }
    final String answer = cache.getAnswer(year, day, part);
    System.out.println("Found cached answer, submitting to API: " + answer);
    submitAnswerToApi(year, day, part, answer);
  }

  private String fetchInputFromApi(final int year, final int day)
      throws IOException, InterruptedException {
    final URI uri = URI.create(BASE_URL + year + "/day/" + day + "/input");

    final HttpRequest request =
        HttpRequest.newBuilder()
            .uri(uri)
            .header("Cookie", "session=" + sessionCookie)
            .GET()
            .build();

    final HttpResponse<String> response =
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    final int statusCode = response.statusCode();
    if (statusCode != 200) {
      throw new IOException("HTTP request failed with status code: " + statusCode);
    }

    final String input = response.body();
    if (input == null || input.trim().isEmpty()) {
      throw new IOException("Retrieved input data was empty");
    }

    return input.trim();
  }

  private void submitAnswerToApi(final int year, final int day, final int part, final String answer)
      throws IOException, InterruptedException {
    final URI uri = URI.create(BASE_URL + year + "/day/" + day + "/answer");

    final String formData = "level=" + part + "&answer=" + answer;

    final HttpRequest request =
        HttpRequest.newBuilder()
            .uri(uri)
            .header("Cookie", "session=" + sessionCookie)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(formData))
            .build();

    final HttpResponse<String> response =
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    final int statusCode = response.statusCode();
    if (statusCode != 200) {
      throw new IOException("HTTP request failed with status code: " + statusCode);
    }

    final String responseBody = response.body();
    if (responseBody == null) {
      throw new IOException("Response body was null");
    }
  }
}
