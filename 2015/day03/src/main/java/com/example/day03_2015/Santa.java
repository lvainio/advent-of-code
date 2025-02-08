package com.example.day03_2015;

import java.util.HashSet;

public class Santa {

    private Point position;

    private HashSet<Point> visited;

    public Santa() {
        this.position = new Point(0, 0);
        this.visited = new HashSet<>();
        this.visited.add(this.position);
    }

    public void move(Move move) {
        int x = this.position.x();
        int y = this.position.y();
        int dx = move.getDx();
        int dy = move.getDy();
        int newX = x + dx;
        int newY = y + dy;
        this.position = new Point(newX, newY);
        this.visited.add(position);
    }

    public int getNumVisited() {
        return this.visited.size();
    }

    public HashSet<Point> getVisited() {
        return this.visited;
    }
}
