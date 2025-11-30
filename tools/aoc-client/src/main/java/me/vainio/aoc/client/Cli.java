package me.vainio.aoc.client;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;
import java.time.Year;
import java.time.LocalDate;
import java.time.Month;

@Command(
    name = "aoc-client", 
    mixinStandardHelpOptions = false, 
    version = "1.0.0",
    header = {
        "",
        "** Advent of Code Client **",
        "",
        "A command-line tool for interacting with Advent of Code.",
        "Fetch daily puzzle input data and submit your solutions",
        "directly from the terminal to adventofcode.com.",
        "",
        "Fetching saves input data to:",
        " - <home>/.aoc_cache/<year>/<day>/input.txt",
        "Posting answers expects solutions in:",
        " - <home>/.aoc_cache/<year>/<day>/part1.txt",
        " - <home>/.aoc_cache/<year>/<day>/part2.txt",
        ""
    },
    footer = {
        "",
        "Examples:",
        "  aoc-client 2024 1                    # Fetch input for 2024 day 1", 
        "  aoc-client 2024 1 -1                 # Submit part 1 answer",
        "  aoc-client 2024 1 -2                 # Submit part 2 answer",
        ""
    },
    usageHelpWidth = 80
)
final class Cli implements Runnable {
    
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Show this help message and exit.")
    private boolean helpRequested;
    
    @Option(names = {"-v", "--version"}, versionHelp = true, description = "Print version information and exit.")
    private boolean versionRequested;
    
    @Parameters(index = "0", description = "Advent of Code year.")
    private int year;
    
    @Parameters(index = "1", description = "Advent of Code day.")
    private int day;
    
    @Option(names = {"-f", "--fetch"}, description = "Fetch input data from AoC (default behavior).")
    private boolean fetchInput;

    @Option(names = {"-1", "--part1"}, description = "Submit part 1 answer to Advent of Code.")
    private boolean postPart1;

    @Option(names = {"-2", "--part2"}, description = "Submit part 2 answer to Advent of Code.")
    private boolean postPart2;
    
    @Spec
    CommandSpec spec;
    
    private AocConfig config;

    @Override
    public void run() {
        final int minYear = 2015;
        final int maxYear = Year.now().getValue();

        final int minDay = 1;
        final int maxDay = 25;
        
        if (year < minYear || year > maxYear) {
            throw new ParameterException(spec.commandLine(),
                    String.format("Invalid value '%d' for parameter 'year': " +
                            "year must be between %d and %d.", year, minYear, maxYear));
        }

        if (day < minDay || day > maxDay) {
            throw new ParameterException(spec.commandLine(),
                    String.format("Invalid value '%d' for parameter 'day': " +
                            "day must be between %d and %d.", day, minDay, maxDay));
        }
        
        if (year == maxYear) {
            LocalDate today = LocalDate.now();
            if (today.getMonth() != Month.DECEMBER) {
                throw new ParameterException(spec.commandLine(),
                        String.format("Advent of Code %d hasn't started yet.", year));
            }
            if (today.getDayOfMonth() < day) {
                throw new ParameterException(spec.commandLine(),
                        String.format("Day %d is not available yet (today is December %d).", day, today.getDayOfMonth()));
            }
        }
        
        if (!fetchInput && !postPart1 && !postPart2) {
            fetchInput = true;
        }
        
        this.config = new AocConfig(year, day, fetchInput, postPart1, postPart2);
    }
    
    public AocConfig getConfig() {
        return config;
    }
}
