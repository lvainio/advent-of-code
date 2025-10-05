package me.vainio.aoc.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import picocli.CommandLine;
import picocli.CommandLine.ParameterException;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class CLITest {
    
    private AocConfig parseAndGetConfig(String... args) {
        CLI cli = new CLI();
        CommandLine cmd = new CommandLine(cli);
        cmd.parseArgs(args);
        cli.run();
        return cli.getConfig();
    }
    
    private void assertParseThrows(Class<? extends Exception> expectedException, String... args) {
        CLI cli = new CLI();
        CommandLine cmd = new CommandLine(cli);
        assertThrows(expectedException, () -> {
            cmd.parseArgs(args);
            cli.run();
        });
    }

    @Test
    @DisplayName("Valid year and day should create correct config with default fetch behavior")
    void testValidArgsWithDefaults() {
        AocConfig config = parseAndGetConfig("2023", "5");
        
        assertEquals(2023, config.year());
        assertEquals(5, config.day());
        assertTrue(config.fetchInput()); // Default behavior
        assertFalse(config.postPart1());
        assertFalse(config.postPart2());
    }

    @Test
    @DisplayName("Explicit fetch flag should work correctly")
    void testExplicitFetchFlag() {
        AocConfig config = parseAndGetConfig("2022", "10", "-f");
        
        assertEquals(2022, config.year());
        assertEquals(10, config.day());
        assertTrue(config.fetchInput());
        assertFalse(config.postPart1());
        assertFalse(config.postPart2());
    }

    @Test
    @DisplayName("Part 1 submission flag should work correctly")
    void testPart1Flag() {
        AocConfig config = parseAndGetConfig("2021", "15", "-1");
        
        assertEquals(2021, config.year());
        assertEquals(15, config.day());
        assertFalse(config.fetchInput()); // Not default when other flags are present
        assertTrue(config.postPart1());
        assertFalse(config.postPart2());
    }

    @Test
    @DisplayName("Part 2 submission flag should work correctly")
    void testPart2Flag() {
        AocConfig config = parseAndGetConfig("2020", "25", "-2");
        
        assertEquals(2020, config.year());
        assertEquals(25, config.day());
        assertFalse(config.fetchInput()); // Not default when other flags are present
        assertFalse(config.postPart1());
        assertTrue(config.postPart2());
    }

    @Test
    @DisplayName("Multiple flags should work together")
    void testMultipleFlags() {
        AocConfig config = parseAndGetConfig("2019", "1", "-f", "-1", "-2");
        
        assertEquals(2019, config.year());
        assertEquals(1, config.day());
        assertTrue(config.fetchInput());
        assertTrue(config.postPart1());
        assertTrue(config.postPart2());
    }

    @Test
    @DisplayName("Year too low should throw ParameterException")
    void testYearTooLow() {
        assertParseThrows(ParameterException.class, "2014", "1");
    }

    @Test
    @DisplayName("Year too high should throw ParameterException")  
    void testYearTooHigh() {
        int futureYear = LocalDate.now().getYear() + 1;
        assertParseThrows(ParameterException.class, String.valueOf(futureYear), "1");
    }

    @Test
    @DisplayName("Day too low should throw ParameterException")
    void testDayTooLow() {
        assertParseThrows(ParameterException.class, "2023", "0");
    }

    @Test
    @DisplayName("Day too high should throw ParameterException")
    void testDayTooHigh() {
        assertParseThrows(ParameterException.class, "2023", "26");
    }

    @Test
    @DisplayName("Current year before December should throw ParameterException")
    void testCurrentYearBeforeDecember() {
        LocalDate today = LocalDate.now();
        if (today.getMonth() != Month.DECEMBER) {
            int currentYear = today.getYear();
            assertParseThrows(ParameterException.class, String.valueOf(currentYear), "1");
        }
        // If it's December, this test doesn't apply
    }

    @Test
    @DisplayName("Current year in December with future day should throw ParameterException")
    void testCurrentYearFutureDay() {
        LocalDate today = LocalDate.now();
        if (today.getMonth() == Month.DECEMBER && today.getDayOfMonth() < 25) {
            int currentYear = today.getYear();
            int futureDay = today.getDayOfMonth() + 1;
            assertParseThrows(ParameterException.class, String.valueOf(currentYear), String.valueOf(futureDay));
        }
        // If not in December or it's already past day 25, this test doesn't apply
    }

    @Test
    @DisplayName("Current year in December with valid day should work")
    void testCurrentYearValidDay() {
        LocalDate today = LocalDate.now();
        if (today.getMonth() == Month.DECEMBER) {
            int currentYear = today.getYear();
            int validDay = Math.min(today.getDayOfMonth(), 25);
            
            AocConfig config = parseAndGetConfig(String.valueOf(currentYear), String.valueOf(validDay));
            
            assertEquals(currentYear, config.year());
            assertEquals(validDay, config.day());
            assertTrue(config.fetchInput());
        }
        // If not in December, this test doesn't apply
    }

    @Test
    @DisplayName("Missing arguments should throw exception")
    void testMissingArguments() {
        assertParseThrows(CommandLine.MissingParameterException.class);
        assertParseThrows(CommandLine.MissingParameterException.class, "2023");
    }

    @Test
    @DisplayName("Long option names should work")
    void testLongOptions() {
        AocConfig config = parseAndGetConfig("2023", "12", "--fetch", "--part1", "--part2");
        
        assertEquals(2023, config.year());
        assertEquals(12, config.day());
        assertTrue(config.fetchInput());
        assertTrue(config.postPart1());
        assertTrue(config.postPart2());
    }
}
