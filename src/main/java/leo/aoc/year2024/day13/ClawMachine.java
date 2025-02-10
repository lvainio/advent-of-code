package leo.aoc.year2024.day13;

public record ClawMachine(
    long ax,
    long ay, 
    long bx, 
    long by, 
    long px,
    long py
) {
    public long calculateCheapestWin() {
        double aPresses = (double) (this.by * this.px - this.bx * this.py) / 
                            (this.ax * this.by - this.ay * this.bx);
        double bPresses = (double) (this.px - aPresses * this.ax) / this.bx;
        if (aPresses >= 0.0 && bPresses >= 0.0 && aPresses % 1.0 == 0.0 && bPresses % 1 == 0.0) {
            return (long) (aPresses * 3.0 + bPresses);
        }
        return 0;
    }

    public ClawMachine addLongToPrizeCoordinates(long num) {
        return new ClawMachine(
            this.ax, 
            this.ay, 
            this.bx, 
            this.by, 
            this.px + num, 
            this.py + num);
    }
}
