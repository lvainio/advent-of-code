package me.vainio.aoc.client;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import picocli.CommandLine.ParameterException;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CliTest {
    
    private AocConfig parseAndGetConfig(String... args) {
        Cli cli = new Cli();
        CommandLine cmd = new CommandLine(cli);
        cmd.parseArgs(args);
        cli.run();
        return cli.getConfig();
    }
    
    private void assertParseThrows(Class<? extends Exception> expectedException, String... args) {
        Cli cli = new Cli();
        CommandLine cmd = new CommandLine(cli);
        assertThrows(expectedException, () -> {
            cmd.parseArgs(args);
            cli.run();
        });
    }

    @Test
    void testValidArgsWithDefaults() {
        AocConfig config = parseAndGetConfig("2023", "5");
        
        assertEquals(2023, config.year());
        assertEquals(5, config.day());
        assertTrue(config.fetchInput());
        assertFalse(config.postPart1());
        assertFalse(config.postPart2());
    }

    @Test
    void testExplicitFetchFlag() {
        AocConfig config = parseAndGetConfig("2022", "10", "-f");
        
        assertEquals(2022, config.year());
        assertEquals(10, config.day());
        assertTrue(config.fetchInput());
        assertFalse(config.postPart1());
        assertFalse(config.postPart2());
    }

    @Test
    void testPart1Flag() {
        AocConfig config = parseAndGetConfig("2021", "15", "-1");
        
        assertEquals(2021, config.year());
        assertEquals(15, config.day());
        assertFalse(config.fetchInput());
        assertTrue(config.postPart1());
        assertFalse(config.postPart2());
    }

    @Test
    void testPart2Flag() {
        AocConfig config = parseAndGetConfig("2020", "25", "-2");
        
        assertEquals(2020, config.year());
        assertEquals(25, config.day());
        assertFalse(config.fetchInput());
        assertFalse(config.postPart1());
        assertTrue(config.postPart2());
    }

    @Test
    void testMultipleFlags() {
        AocConfig config = parseAndGetConfig("2019", "1", "-f", "-1", "-2");
        
        assertEquals(2019, config.year());
        assertEquals(1, config.day());
        assertTrue(config.fetchInput());
        assertTrue(config.postPart1());
        assertTrue(config.postPart2());
    }

    @Test
    void testLongOptions() {
        AocConfig config = parseAndGetConfig("2023", "12", "--fetch", "--part1", "--part2");
        
        assertEquals(2023, config.year());
        assertEquals(12, config.day());
        assertTrue(config.fetchInput());
        assertTrue(config.postPart1());
        assertTrue(config.postPart2());
    }

    @Test
    void testYearTooLow() {
        assertParseThrows(ParameterException.class, "2014", "1");
    }

    @Test
    void testYearTooHigh() {
        int futureYear = LocalDate.now().getYear() + 1;
        assertParseThrows(ParameterException.class, String.valueOf(futureYear), "1");
    }

    @Test
    void testDayTooLow() {
        assertParseThrows(ParameterException.class, "2023", "0");
    }

    @Test
    void testDayTooHigh() {
        assertParseThrows(ParameterException.class, "2023", "26");
    }

    @Test
    void testYearAndDayWrongOrder() {
        assertParseThrows(ParameterException.class, "1", "2023");
    }

    @Test
    void testUnknownFlag() {
        assertParseThrows(CommandLine.UnmatchedArgumentException.class, "2023", "5", "--unknown");
    }

    @Test
    void testUnknownShortOption() {
        assertParseThrows(CommandLine.UnmatchedArgumentException.class, "2023", "5", "-x");
    }

    @Test
    void testMissingArguments() {
        assertParseThrows(CommandLine.MissingParameterException.class);
        assertParseThrows(CommandLine.MissingParameterException.class, "2023");
    }
}
