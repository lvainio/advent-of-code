package com.example.day20_2024;

public record Cheat(Node startNode, Node endNode) {
    public int getManhattanDistance() {
        return Math.abs(startNode.row() - endNode.row()) + Math.abs(startNode.col() - endNode.col());
    }
}
