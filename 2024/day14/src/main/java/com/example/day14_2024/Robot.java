package com.example.day14_2024;

public class Robot {
    private int x;
    private int y;

    private final int vx;
    private final int vy;

    public Robot(int x, int y, int vx, int vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public void step(int width, int height) {
        this.x = (this.x + this.vx + width) % width;
        this.y = (this.y + this.vy + height) % height;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isInNwQuadrant(int width, int height) {
        int xMid = width / 2;
        int yMid = height / 2;
        return this.x < xMid && this.y < yMid;
    }

    public boolean isInNeQuadrant(int width, int height) {
        int xMid = width / 2;
        int yMid = height / 2;
        return this.x > xMid && this.y < yMid;
    }

    public boolean isInSwQuadrant(int width, int height) {
        int xMid = width / 2;
        int yMid = height / 2;
        return this.x < xMid && this.y > yMid;
    }

    public boolean isInSeQuadrant(int width, int height) {
        int xMid = width / 2;
        int yMid = height / 2;
        return this.x > xMid && this.y > yMid;
    }

    @Override
    public String toString() {
        return "Robot( (x, y)=(" + this.x + ", " + this.y + "), (vx, vy)=(" + this.vx + ", " + this.vy + ") )";
    }
}
