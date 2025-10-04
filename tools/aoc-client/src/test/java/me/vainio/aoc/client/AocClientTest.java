package me.vainio.aoc.client;

import me.vainio.aoc.cache.AocCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class AocClientTest {

    @TempDir
    private Path tempDir;
    
    private AocCache cache;
    private AocClient client;
    private final String dummySessionCookie = "dummy_session_cookie_12345";
    
    @BeforeEach
    void setUp() {
        cache = new AocCache(tempDir);
        client = new AocClient(dummySessionCookie, cache, "https://example.com");
    }
    
    @Test
    void testConstructorWithDefaultValues() {
        AocClient defaultClient = new AocClient(dummySessionCookie);
        assertNotNull(defaultClient);
    }
    
    @Test
    void testConstructorWithCache() {
        AocClient clientWithCache = new AocClient(dummySessionCookie, cache);
        assertNotNull(clientWithCache);
    }
    
    @Test
    void testConstructorWithAllParameters() {
        AocClient fullClient = new AocClient(dummySessionCookie, cache, "https://test.com");
        assertNotNull(fullClient);
    }
    
    @Test
    void testGetInputFromCache() {
        int year = 2024;
        int day = 1;
        String expectedInput = "Test input from cache";
        
        // Pre-populate cache
        cache.saveInput(year, day, expectedInput);
        
        // Should get from cache without making HTTP request
        String actualInput = client.getInput(year, day);
        
        assertEquals(expectedInput, actualInput);
    }
    
    @Test
    void testSubmissionResultCreation() {
        String answer = "42";
        String message = "Test message";
        boolean correct = true;
        
        AocClient.SubmissionResult result = new AocClient.SubmissionResult(correct, message, answer);
        
        assertTrue(result.isCorrect());
        assertEquals(message, result.getMessage());
        assertEquals(answer, result.getSubmittedAnswer());
    }
    
    @Test
    void testSubmissionResultToString() {
        String answer = "123";
        String message = "Correct!";
        boolean correct = true;
        
        AocClient.SubmissionResult result = new AocClient.SubmissionResult(correct, message, answer);
        String resultString = result.toString();
        
        assertTrue(resultString.contains("correct=true"));
        assertTrue(resultString.contains("message='Correct!'"));
        assertTrue(resultString.contains("answer='123'"));
    }
    
    @Test
    void testSubmissionResultEquals() {
        String answer1 = "456";
        String answer2 = "456";
        String message = "Wrong answer";
        boolean correct = false;
        
        AocClient.SubmissionResult result1 = new AocClient.SubmissionResult(correct, message, answer1);
        AocClient.SubmissionResult result2 = new AocClient.SubmissionResult(correct, message, answer2);
        
        // Test that they have the same properties
        assertEquals(result1.isCorrect(), result2.isCorrect());
        assertEquals(result1.getMessage(), result2.getMessage());
        assertEquals(result1.getSubmittedAnswer(), result2.getSubmittedAnswer());
    }
    
    @Test
    void testSubmissionResultWithIncorrectAnswer() {
        String answer = "wrong";
        String message = "Answer is too low";
        boolean correct = false;
        
        AocClient.SubmissionResult result = new AocClient.SubmissionResult(correct, message, answer);
        
        assertFalse(result.isCorrect());
        assertEquals(message, result.getMessage());
        assertEquals(answer, result.getSubmittedAnswer());
    }
    
    @Test
    void testCacheIntegration() {
        int year = 2023;
        int day = 25;
        String input = "Sample puzzle input\n1 2 3\n4 5 6";
        
        // Save input to cache
        cache.saveInput(year, day, input);
        
        // Client should retrieve from cache
        String retrievedInput = client.getInput(year, day);
        
        assertEquals(input, retrievedInput);
    }
    
    @Test
    void testMultipleInputRetrieval() {
        int year1 = 2023, day1 = 1;
        int year2 = 2024, day2 = 15;
        String input1 = "Input for 2023 day 1";
        String input2 = "Input for 2024 day 15";
        
        // Pre-populate cache
        cache.saveInput(year1, day1, input1);
        cache.saveInput(year2, day2, input2);
        
        // Retrieve both
        assertEquals(input1, client.getInput(year1, day1));
        assertEquals(input2, client.getInput(year2, day2));
    }
}
