package me.vainio.aoc.client;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "aoc-client", mixinStandardHelpOptions = true, version = "1.0.0")
final class ArgsParser implements Runnable {
    
    @Option(names = {"-y", "--year"}, description = "Year (2015-2025)", required = true)
    private int year;
    
    @Option(names = {"-d", "--day"}, description = "Day (1-25)", required = true)
    private int day;
    
    @Option(names = {"-f", "--fetch-input"}, description = "Fetch input data (default)")
    private boolean fetchInput;
    
    @Option(names = {"-p", "--post-answer"}, description = "Post answer to AoC (not yet implemented)")
    private boolean postAnswer;

    private AocConfig config;

    @Override
    public void run() {
        if (year < 2015 || year > 2025) {
            throw new IllegalArgumentException("Year must be between 2015 and 2025, got: " + year);
        }
        
        if (day < 1 || day > 25) {
            throw new IllegalArgumentException("Day must be between 1 and 25, got: " + day);
        }
        
        if (!fetchInput && !postAnswer) {
            fetchInput = true;
        }
        
        this.config = new AocConfig(year, day, fetchInput, postAnswer);
    }
    
    public AocConfig getConfig() {
        return config;
    }
}
