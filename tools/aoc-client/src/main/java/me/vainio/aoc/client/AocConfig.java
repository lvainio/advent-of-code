package me.vainio.aoc.client;

record AocConfig(int year, int day, boolean fetchInput, boolean postPart1, boolean postPart2) {
    
    @Override
    public String toString() {
        return String.format("""
                AoC Configuration
                -----------------
                Year:        %d
                Day:         %d
                Fetch Input: %s
                Submit P1:   %s
                Submit P2:   %s
                -----------------""", 
                year, day, fetchInput, postPart1, postPart2);
    }
}
