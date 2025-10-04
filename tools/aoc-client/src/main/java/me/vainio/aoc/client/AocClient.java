package me.vainio.aoc.client;

import me.vainio.aoc.cache.AocCache;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

public class AocClient {
    private static final String BASE_URL = "https://adventofcode.com/";
    
    public static String downloadInput(String year, String day, String cookie) {
        String dayWithoutLeadingZeros = String.valueOf(Integer.parseInt(day));
        URI uri = URI.create("https://adventofcode.com/" + year + "/day/" + dayWithoutLeadingZeros + "/input");
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
}
