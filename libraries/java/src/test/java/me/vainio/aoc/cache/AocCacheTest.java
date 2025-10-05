package me.vainio.aoc.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        final int year = 2024;
        final int day = 1;
        final String expectedInput = "Test input for day 1\nLine 2\nLine 3";
        
        cache.saveInput(year, day, expectedInput);
        
        final String actualInput = cache.getInput(year, day);
        
        assertEquals(expectedInput, actualInput);
    }
    
    @Test
    void testSaveAndGetAnswerPart1() {
        final int year = 2024;
        final int day = 1;
        final int part = 1;
        final String expectedAnswer = "42";
        
        cache.saveAnswer(year, day, part, expectedAnswer);
        
        final String actualAnswer = cache.getAnswer(year, day, part);
        
        assertEquals(expectedAnswer, actualAnswer);
    }
    
    @Test
    void testSaveAndGetAnswerPart2() {
        final int year = 2023;
        final int day = 25;
        final int part = 2;
        final String expectedAnswer = "999";
        
        cache.saveAnswer(year, day, part, expectedAnswer);
        
        final String actualAnswer = cache.getAnswer(year, day, part);
        
        assertEquals(expectedAnswer, actualAnswer);
    }
    
    @Test
    void testMultipleYearsAndDays() {
        final String input2023Day1 = "Input for 2023 day 1";
        final String input2024Day15 = "Input for 2024 day 15";
        final String answer2023Day1Part1 = "123";
        final String answer2024Day15Part2 = "456";
        
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
        final int year = 2024;
        final int day = 5;
        final String input = "Test padding";
        
        cache.saveInput(year, day, input);
        
        final Path expectedPath = tempDir.resolve("2024")
                                    .resolve("05")
                                    .resolve("input.txt");
        
        assertTrue(Files.exists(expectedPath), "File should exist with padded day directory");
        assertEquals(input, cache.getInput(year, day));
    }
    
    @Test
    void testOverwriteExistingData() {
        final int year = 2024;
        final int day = 1;
        final String originalInput = "Original input";
        final String newInput = "Updated input";
        
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

    @Test
    void testThatConstructorThrowsNullPointerExceptionIfCacheDirIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new AocCache(null);
        });
    }

    @Test
    void testCacheWorksWhenDirectoryDoesNotExist() throws IOException {
        final Path nonExistentDir = tempDir.resolve("non-existent-cache-dir-1234567890");
        assertFalse(Files.exists(nonExistentDir), "Cache directory should not exist initially");        

        final AocCache cacheWithNonExistentDir = new AocCache(nonExistentDir);
        
        final int year = 2024;
        final int day = 1;
        final String input = "Test input in non-existent dir";
        final String answer = "42";
        
        cacheWithNonExistentDir.saveInput(year, day, input);
        cacheWithNonExistentDir.saveAnswer(year, day, 1, answer);
        
        assertTrue(Files.exists(nonExistentDir), "Cache directory should be created");
        assertTrue(Files.exists(nonExistentDir.resolve("2024").resolve("01")), 
                   "Year/day directories should be created");
        
        assertEquals(input, cacheWithNonExistentDir.getInput(year, day));
        assertEquals(answer, cacheWithNonExistentDir.getAnswer(year, day, 1));
    }

    @Test
    void testHasInputReturnsFalseWhenInputDoesNotExist() {
        final int year = 2024;
        final int day = 1;
        
        assertFalse(cache.hasInput(year, day));
    }

    @Test
    void testHasInputReturnsTrueAfterSavingInput() {
        final int year = 2024;
        final int day = 1;
        final String input = "Test input for hasInput";
        
        assertFalse(cache.hasInput(year, day));
        
        cache.saveInput(year, day, input);
        
        assertTrue(cache.hasInput(year, day));
    }

    @Test
    void testHasAnswerReturnsFalseWhenAnswerDoesNotExist() {
        final int year = 2024;
        final int day = 1;
        
        assertFalse(cache.hasAnswer(year, day, 1));
        assertFalse(cache.hasAnswer(year, day, 2));
    }

    @Test
    void testHasAnswerReturnsTrueAfterSavingAnswer() {
        final int year = 2024;
        final int day = 1;
        final String answer1 = "Answer for part 1";
        final String answer2 = "Answer for part 2";
        
        assertFalse(cache.hasAnswer(year, day, 1));
        assertFalse(cache.hasAnswer(year, day, 2));
        
        cache.saveAnswer(year, day, 1, answer1);
        
        assertTrue(cache.hasAnswer(year, day, 1));
        assertFalse(cache.hasAnswer(year, day, 2));
        
        cache.saveAnswer(year, day, 2, answer2);
        
        assertTrue(cache.hasAnswer(year, day, 1));
        assertTrue(cache.hasAnswer(year, day, 2));
    }
}
