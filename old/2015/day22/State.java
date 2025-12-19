package leo.aoc.year2015.day22;

import java.util.HashSet;

public record State(boolean isPlayerTurn, HashSet<Effect> effects, Player player, Boss boss) {}
