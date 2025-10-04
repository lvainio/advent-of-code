package me.vainio.aoc.client;

import me.vainio.aoc.cache.AocCache;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

/**
 * Client for interacting with Advent of Code website.
 * Handles downloading inputs, submitting answers, and managing session authentication.
 */
public class AocClient {
    
    private final HttpClient httpClient;
    private final String sessionCookie;
    private final AocCache cache;
    private final String baseUrl;
    
    public AocClient(String sessionCookie) {
        this(sessionCookie, new AocCache(), "https://adventofcode.com");
    }
    
    public AocClient(String sessionCookie, AocCache cache) {
        this(sessionCookie, cache, "https://adventofcode.com");
    }
    
    public AocClient(String sessionCookie, AocCache cache, String baseUrl) {
        this.sessionCookie = sessionCookie;
        this.cache = cache;
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    }
    
    /**
     * Gets the input for a specific day, first checking cache, then downloading if needed.
     */
    public String getInput(int year, int day) {
        // Try to get from cache first
        try {
            return cache.getInput(year, day);
        } catch (RuntimeException e) {
            // Not in cache, download from website
            String input = downloadInput(year, day);
            cache.saveInput(year, day, input);
            return input;
        }
    }
    
    /**
     * Downloads the input for a specific day from the AoC website.
     */
    public String downloadInput(int year, int day) {
        String url = String.format("%s/%d/day/%d/input", baseUrl, year, day);
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Cookie", "session=" + sessionCookie)
            .header("User-Agent", "AocClient/1.0 (leo.vainio@spotify.com)")
            .timeout(Duration.ofSeconds(30))
            .GET()
            .build();
            
        try {
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
                
            if (response.statusCode() != 200) {
                throw new RuntimeException(String.format(
                    "Failed to download input for %d day %d: HTTP %d", 
                    year, day, response.statusCode()));
            }
            
            return response.body().trim();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to download input", e);
        }
    }
    
    /**
     * Submits an answer for a specific part.
     */
    public SubmissionResult submitAnswer(int year, int day, int part, String answer) {
        // Check if we already have this answer cached
        try {
            String cachedAnswer = cache.getAnswer(year, day, part);
            if (cachedAnswer.equals(answer)) {
                return new SubmissionResult(true, "Already submitted (cached)", answer);
            }
        } catch (RuntimeException e) {
            // Not cached, proceed with submission
        }
        
        String url = String.format("%s/%d/day/%d/answer", baseUrl, year, day);
        String formData = String.format("level=%d&answer=%s", part, answer);
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Cookie", "session=" + sessionCookie)
            .header("User-Agent", "AocClient/1.0 (leo.vainio@spotify.com)")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .timeout(Duration.ofSeconds(30))
            .POST(HttpRequest.BodyPublishers.ofString(formData))
            .build();
            
        try {
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
                
            if (response.statusCode() != 200) {
                return new SubmissionResult(false, 
                    String.format("HTTP error: %d", response.statusCode()), 
                    answer);
            }
            
            String responseBody = response.body();
            boolean correct = responseBody.contains("That's the right answer!");
            
            if (correct) {
                cache.saveAnswer(year, day, part, answer);
            }
            
            String message = parseSubmissionMessage(responseBody)
                .orElse("Unknown response");
                
            return new SubmissionResult(correct, message, answer);
            
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to submit answer", e);
        }
    }
    
    private Optional<String> parseSubmissionMessage(String responseBody) {
        if (responseBody.contains("That's the right answer!")) {
            return Optional.of("Correct answer!");
        } else if (responseBody.contains("That's not the right answer")) {
            if (responseBody.contains("too high")) {
                return Optional.of("Answer is too high");
            } else if (responseBody.contains("too low")) {
                return Optional.of("Answer is too low");
            } else {
                return Optional.of("Wrong answer");
            }
        } else if (responseBody.contains("You gave an answer too recently")) {
            return Optional.of("Rate limited - wait before submitting again");
        } else if (responseBody.contains("already complete")) {
            return Optional.of("Already completed");
        }
        return Optional.empty();
    }
    
    public static class SubmissionResult {
        private final boolean correct;
        private final String message;
        private final String submittedAnswer;
        
        public SubmissionResult(boolean correct, String message, String submittedAnswer) {
            this.correct = correct;
            this.message = message;
            this.submittedAnswer = submittedAnswer;
        }
        
        public boolean isCorrect() {
            return correct;
        }
        
        public String getMessage() {
            return message;
        }
        
        public String getSubmittedAnswer() {
            return submittedAnswer;
        }
        
        @Override
        public String toString() {
            return String.format("SubmissionResult{correct=%s, message='%s', answer='%s'}", 
                correct, message, submittedAnswer);
        }
    }
}
