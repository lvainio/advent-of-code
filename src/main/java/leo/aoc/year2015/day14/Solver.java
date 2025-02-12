package leo.aoc.year2015.day14;

import java.util.HashMap;
import java.util.List;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private record Reindeer(String name, int speed, int flyTime, int restTime) {}

    private List<Reindeer> reindeers;

    public Solver(String input) {
        super(input);
        
        this.reindeers = input.lines().map(line -> {
            String[] parts = line.split("\\s+");
            String name = parts[0];
            int speed = Integer.parseInt(parts[3]);
            int flyTime = Integer.parseInt(parts[6]);
            int restTime = Integer.parseInt(parts[13]);
            return new Reindeer(name, speed, flyTime, restTime);
        }).toList();
    }

    @Override
    public String solvePart1() {
        int winningDistance = this.reindeers.stream()
            .mapToInt(reindeer -> getDistanceAfterSeconds(reindeer, 2503))
            .max()
            .orElse(Integer.MIN_VALUE);
        return Integer.toString(winningDistance);
    }

    @Override
    public String solvePart2() {
        HashMap<Reindeer, Integer> scores = new HashMap<>();
        for (Reindeer reindeer : reindeers) {
            scores.put(reindeer, 0);
        }
        for (int i = 1; i <= 2503; i++) {
            int seconds = i;
            int leadingDistance = this.reindeers.stream()
                .mapToInt(reindeer -> getDistanceAfterSeconds(reindeer, seconds))
                .max()
                .orElse(Integer.MIN_VALUE);
            for (Reindeer reindeer : reindeers) {
                int dist = getDistanceAfterSeconds(reindeer, i);
                if (dist == leadingDistance) {
                    scores.put(reindeer, scores.get(reindeer)+1);
                }
            }
        }
        int maxScore = 0;
        for (Integer n : scores.values()) {
            maxScore = Math.max(maxScore, n);
        }
        return Integer.toString(maxScore);
    }

    private int getDistanceAfterSeconds(Reindeer reindeer, int seconds) {
        int speed = reindeer.speed();
        int flyTime = reindeer.flyTime();
        int restTime = reindeer.restTime();

        int distanceTravelled = 0;
        for (int i = 0; i < seconds; i++) {
            if (i % (flyTime + restTime) < flyTime) {
                distanceTravelled += speed;
            }
        }
        return distanceTravelled;
    }
}
