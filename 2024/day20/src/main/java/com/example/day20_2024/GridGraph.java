package com.example.day20_2024;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class GridGraph {

    private Cell[][] grid;

    private Node startNode;
    private Node endNode;

    public GridGraph(Cell[][] grid, Node startNode, Node endNode) {
        this.grid = grid;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public int getRaceTrackLength() {
        int count = 0;
        for (int row = 0; row < this.grid.length; row++) {
            for (int col = 0; col < this.grid[0].length; col++) {
                if (this.grid[row][col] == Cell.EMPTY) {
                    count++;
                }
            }
        }
        return count - 1;
    }

    public int search() {
        int trackLength = getRaceTrackLength();

        var usedCheats = new HashSet<Cheat>();
        int numCheatsTried = 0;

        int count = 0;

        while (usedCheats.size() == numCheatsTried) {
            int length = bfs(usedCheats);
            if (trackLength - length >= 100) {
                count++;
            }
            numCheatsTried++;
        }

        return count;
    }

    public int bfs(HashSet<Cheat> usedCheats) {
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> visited = new HashSet<>();

        queue.offer(this.startNode);
        visited.add(this.startNode);

        int steps = 0;
        boolean cheatUsed = false;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node currentNode = queue.poll();

                if (currentNode.equals(this.endNode)) {
                    return steps;
                }

                Cheat cheat = null;
                if (!cheatUsed) {
                    cheat = getCheatIfExists(currentNode, visited, usedCheats); 
                }

                if (cheat != null && !cheatUsed) {
                    queue.offer(cheat.endNode());
                    visited.add(cheat.endNode());
                    usedCheats.add(cheat);
                    cheatUsed = true;
                    steps++;
                } else {
                    for (Direction direction : Direction.values()) {
                        int newRow = currentNode.row() + direction.getDr();
                        int newCol = currentNode.col() + direction.getDc();

                        Node newNode = new Node(newRow, newCol);
                        if (newRow >= 0
                            && newRow < this.grid.length
                            && newCol >= 0
                            && newCol < this.grid[0].length
                            && !visited.contains(newNode)
                            && this.grid[newRow][newCol] == Cell.EMPTY) {
                                queue.offer(newNode);
                                visited.add(newNode);
                        }
                    }
                }
            }
            steps++;
        }
        throw new IllegalStateException("No path found");
    }

    public Cheat getCheatIfExists(Node node, HashSet<Node> visited, HashSet<Cheat> usedCheats) {
        for (CheatDirection cd : CheatDirection.values()) {
            int newRow = node.row() + cd.getDr();
            int newCol = node.col() + cd.getDc();

            Node newNode = new Node(newRow, newCol);
            Cheat cheat = new Cheat(node, newNode);

            if (newRow >= 0
                && newRow < this.grid.length
                && newCol >= 0
                && newCol < this.grid[0].length
                && !visited.contains(newNode)
                && !usedCheats.contains(cheat)
                && this.grid[newRow][newCol] == Cell.EMPTY) {
                    return cheat;
            }
        }
        return null;
    }

    public Cheat getCheatInRadius(Node node, HashSet<Cheat> usedCheats) {
        
    }
}
