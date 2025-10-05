package me.vainio.aoc.client;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

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
        "Fetching saves input data to: home/.aoc_cache/<year>/<day>/input.txt",
        "Posting answers expects solutions in: home/.aoc_cache/<year>/<day>/",
        ""
    },
    footer = {
        "",
        "Examples:",
        "  aoc-client -y 2024 -d 1              # Fetch input for 2024 day 1", 
        "  aoc-client -y 2024 -d 1 -1           # Submit part 1 answer",
        "  aoc-client -y 2024 -d 1 -2           # Submit part 2 answer",
        ""
    },
    usageHelpWidth = 80
)
final class CLI implements Runnable {
    
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Show this help message and exit.")
    private boolean helpRequested;
    
    @Option(names = {"-v", "--version"}, versionHelp = true, description = "Print version information and exit.")
    private boolean versionRequested;
    
    @Option(names = {"-y", "--year"}, description = "Advent of Code year.", required = true)
    private int year;
    
    @Option(names = {"-d", "--day"}, description = "Advent of Code day.", required = true)
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
        if (year < 2015 || year > 2025) {
            throw new ParameterException(spec.commandLine(),
                    String.format("Invalid value '%d' for option '--year': " +
                            "year must be between 2015 and 2025.", year));
        }
        
        if (day < 1 || day > 25) {
            throw new ParameterException(spec.commandLine(),
                    String.format("Invalid value '%d' for option '--day': " +
                            "day must be between 1 and 25.", day));
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
