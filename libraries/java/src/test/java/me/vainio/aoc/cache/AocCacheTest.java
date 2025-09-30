package me.vainio.aoc.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class AocCacheTest {

    @TempDir
    private Path tempDir;

    private AocCache cache;

    @BeforeEach
    void setUp() {
        cache = new AocCache(tempDir);
    }
     
    @Test
    void testSaveAndGetInput() {
        int year = 2024;
        int day = 1;
        String expectedInput = "Test input for day 1\nLine 2\nLine 3";
        
        cache.saveInput(year, day, expectedInput);
        
        String actualInput = cache.getInput(year, day);
        
        assertEquals(expectedInput, actualInput);
    }
    
    @Test
    void testSaveAndGetAnswerPart1() {
        int year = 2024;
        int day = 1;
        int part = 1;
        String expectedAnswer = "42";
        
        cache.saveAnswer(year, day, part, expectedAnswer);
        
        String actualAnswer = cache.getAnswer(year, day, part);
        
        assertEquals(expectedAnswer, actualAnswer);
    }
    
    @Test
    void testSaveAndGetAnswerPart2() {
        int year = 2023;
        int day = 25;
        int part = 2;
        String expectedAnswer = "999";
        
        cache.saveAnswer(year, day, part, expectedAnswer);
        
        String actualAnswer = cache.getAnswer(year, day, part);
        
        assertEquals(expectedAnswer, actualAnswer);
    }
    
    @Test
    void testMultipleYearsAndDays() {
        String input2023Day1 = "Input for 2023 day 1";
        String input2024Day15 = "Input for 2024 day 15";
        String answer2023Day1Part1 = "123";
        String answer2024Day15Part2 = "456";
        
        cache.saveInput(2023, 1, input2023Day1);
        cache.saveInput(2024, 15, input2024Day15);
        cache.saveAnswer(2023, 1, 1, answer2023Day1Part1);
        cache.saveAnswer(2024, 15, 2, answer2024Day15Part2);
        
        assertEquals(input2023Day1, cache.getInput(2023, 1));
        assertEquals(input2024Day15, cache.getInput(2024, 15));
        assertEquals(answer2023Day1Part1, cache.getAnswer(2023, 1, 1));
        assertEquals(answer2024Day15Part2, cache.getAnswer(2024, 15, 2));
    }
    
    @Test
    void testDayPaddingInPath() throws IOException {
        int year = 2024;
        int day = 5;
        String input = "Test padding";
        
        cache.saveInput(year, day, input);
        
        Path expectedPath = tempDir.resolve("2024")
                                    .resolve("05")
                                    .resolve("input.txt");
        
        assertTrue(Files.exists(expectedPath), "File should exist with padded day directory");
        assertEquals(input, Files.readString(expectedPath));
    }
    
    @Test
    void testOverwriteExistingData() {
        int year = 2024;
        int day = 1;
        String originalInput = "Original input";
        String newInput = "Updated input";
        
        cache.saveInput(year, day, originalInput);
        assertEquals(originalInput, cache.getInput(year, day));
        
        cache.saveInput(year, day, newInput);
        assertEquals(newInput, cache.getInput(year, day));
    }
        
    @Test
    void testReadNonExistentInput() {
        assertThrows(RuntimeException.class, () -> {
            cache.getInput(9999, 99);
        });
    }
    
    @Test
    void testReadNonExistentAnswerPart1() {
        assertThrows(RuntimeException.class, () -> {
            cache.getAnswer(9999, 99, 1);
        });
    }
    
    @Test
    void testReadNonExistentAnswerPart2() {
        assertThrows(RuntimeException.class, () -> {
            cache.getAnswer(9999, 99, 2);
        });
    }
}
