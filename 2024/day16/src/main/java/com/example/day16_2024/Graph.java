package com.example.day16_2024;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {

    private final HashMap<Node, HashSet<Node>> graph;
    private final Node startNode;
    private final Node endNode;

    private HashSet<Node> bestPathsNodes = new HashSet<>();

    public Graph(HashMap<Node, HashSet<Node>> graph, Node starNode, Node endNode) {
        this.graph = graph;
        this.startNode = starNode;
        this.endNode = endNode;
    }

    public int getNumBestPathNodes() {
        return this.bestPathsNodes.size();
    }

    public int findLowestScore() {
        HashMap<Node, Integer> scores = new HashMap<>();    

        dfs(this.startNode, Direction.EAST, 0, scores, new HashSet<>());

        return scores.get(endNode);
    }

    private void dfs(Node node, Direction direction, int score, HashMap<Node, Integer> scores, HashSet<Node> path) {
        if (node.equals(this.endNode)) {
            System.out.println("SCORE: " + score);

            if (!scores.containsKey(node) || score < scores.get(node)) {
                scores.put(node, score);
                this.bestPathsNodes = path;
            } else if (score == scores.get(node)) {
                this.bestPathsNodes.addAll(path);
            }
            return;
        }

        if ((scores.containsKey(node) && score <= scores.get(node)) || !scores.containsKey(node)) {
            scores.put(node, score);
            
        } else {
            return;
        }

        for (Node neighbor : this.graph.get(node)) {
            int costToNeighbor = getCostToNode(node, neighbor, direction);
            Direction directionToNeighbor = getDirectionToNode(node, neighbor);

            HashSet<Node> pathCopy = new HashSet<>(path);
            HashSet<Node> pathToNeighbor = getPathToNeighbor(node, neighbor);
            pathCopy.addAll(pathToNeighbor);

            dfs(neighbor, directionToNeighbor, score+costToNeighbor, scores, pathCopy);
        }
    }

    private int getCostToNode(Node from, Node to, Direction direction) {
        int numSteps = Math.abs(from.row() - to.row()) + Math.abs(from.col() - to.col());

        Direction directionToNode = getDirectionToNode(from, to);

        int turnCost = 0;
        if (directionToNode == direction) {
            turnCost += 0;
        } else if ((direction == Direction.NORTH && directionToNode == Direction.SOUTH) 
                    || (direction == Direction.SOUTH && directionToNode == Direction.NORTH)
                    || (direction == Direction.EAST && directionToNode == Direction.WEST)
                    || (direction == Direction.WEST && directionToNode == Direction.EAST)) {
                        turnCost += 2000;
        } else {
            turnCost += 1000;
        }

        return numSteps + turnCost;
    }

    private Direction getDirectionToNode(Node from, Node to) {
        if (from.col() < to.col()) {
            return Direction.EAST;
        } else if (from.col() > to.col()) {
            return Direction.WEST;
        } else if (from.row() < to.row()) {
            return Direction.SOUTH;
        } else {
            return Direction.NORTH;
        }
    }

    private HashSet<Node> getPathToNeighbor(Node from, Node to) {
        HashSet<Node> path = new HashSet<>();

        boolean isRowAligned = from.row() == to.row();
        boolean isColAligned = from.col() == to.col();
        
        if (isRowAligned) {
            int row = from.row();
            int minCol = Math.min(from.col(), to.col());
            int maxCol = Math.max(from.col(), to.col());
            for (int col = minCol; col <= maxCol; col++) {
                path.add(new Node(row, col));
            }
        }

        if (isColAligned) {
            int col = from.col();
            int minRow = Math.min(from.row(), to.row());
            int maxRow = Math.max(from.row(), to.row());
            for (int row = minRow; row <= maxRow; row++) {
                path.add(new Node(row, col));
            }
        }

        return path;
    }
}
