package com.example.day21_2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Keypad {

    private record Node(int row, int col) {};

    private record BFSNode(Node node, String path) {};
    
    private record Pair(char from, char to) {};

    private record MemoPair(String sequence, int depth) {};

    public static final Character[][] NUMERIC_KEYPAD = {
        {'7', '8', '9'},
        {'4', '5', '6'},
        {'1', '2', '3'},
        {null, '0', 'A'}
    };

    public static final Character[][] DIRECTIONAL_KEYPAD = {
        {null, '^', 'A'},
        {'<', 'v', '>'}
    };

    private HashMap<Pair, HashSet<String>> shortestPaths;

    private HashMap<Pair, Integer> shortestLengths;

    private Map<MemoPair, Long> memMap;

    public Keypad(Character[][] keypad) {
        this.shortestPaths = computeShortestPaths(keypad);
        this.shortestLengths = computeShortestLengths();
        this.memMap = new HashMap<>();
    }

    private HashMap<Pair, HashSet<String>> computeShortestPaths(Character[][] keypad) {
        HashMap<Pair, HashSet<String>> shortestPaths = new HashMap<>();
        List<Pair> pairs = getKeyPairs(keypad);

        for (Pair pair : pairs) {
            Node start = nodeFromKey(pair.from(), keypad);
            Node end = nodeFromKey(pair.to(), keypad);

            Queue<BFSNode> queue = new LinkedList<>();
            HashSet<String> paths = new HashSet<>();
            queue.offer(new BFSNode(start, ""));
            int shortestLength = Integer.MAX_VALUE;

            while(!queue.isEmpty()) {
                BFSNode current = queue.poll();
                Node position = current.node();
                String path = current.path();

                if (path.length() > shortestLength) {
                    break;
                }

                if (position.equals(end)) {
                    paths.add(path + 'A');
                    shortestLength = path.length() + 1;
                }

                for (Direction dir : Direction.values()) {
                    int newRow = position.row() + dir.getDr();
                    int newCol = position.col() + dir.getDc();
                    if (newRow >= 0
                        && newRow < keypad.length
                        && newCol >= 0
                        && newCol < keypad[0].length
                        && keypad[newRow][newCol] != null) {
                            BFSNode newNode = new BFSNode(new Node(newRow, newCol), path+dir.getSymbol());
                            queue.offer(newNode);
                    }
                }
            }
            shortestPaths.put(pair, paths);
        }
        return shortestPaths;
    }

    private List<Pair> getKeyPairs(Character[][] keypad) {
        List<Pair> pairs = new ArrayList<>();
        for (int row1 = 0; row1 < keypad.length; row1++) {
            for (int col1 = 0; col1 < keypad[0].length; col1++) {
                Character c1 = keypad[row1][col1];
                if (c1 == null) {
                    continue;
                }
                for (int row2 = 0; row2 < keypad.length; row2++) {
                    for (int col2 = 0; col2 < keypad[0].length; col2++) {
                        Character c2 = keypad[row2][col2];
                        if (c2 == null) {
                            continue;
                        }
                        pairs.add(new Pair(c1, c2));
                        if (!c1.equals(c2)) {
                            pairs.add(new Pair(c2, c1));
                        }
                    }
                }
            }
        }
        return pairs;
    }

    private Node nodeFromKey(char c, Character[][] keypad) {
        for (int row = 0; row < keypad.length; row++) {
            for (int col = 0; col < keypad[0].length; col++) {
                if (keypad[row][col] != null && c == keypad[row][col]) {
                    return new Node(row, col);
                }
            }
        }
        throw new IllegalArgumentException("Keypad does not contain key: " + c);
    }

    private HashMap<Pair, Integer> computeShortestLengths() {
        HashMap<Pair, Integer> shortestLengths = new HashMap<>();
        for (Map.Entry<Pair, HashSet<String>> entry : shortestPaths.entrySet()) {
            Pair key = entry.getKey();  
            HashSet<String> value = entry.getValue(); 
            shortestLengths.put(key, value.iterator().next().length());
        }
        return shortestLengths;
    }

    public List<String> getAllInputSequences(String sequence) {
        List<String> sequences = new ArrayList<>();
        sequences.add("");

        char from = 'A';
        for (int i = 0; i < sequence.length(); i++) {
            char to = sequence.charAt(i);
            Pair pair = new Pair(from, to);
            
            HashSet<String> paths = this.shortestPaths.get(pair);
            List<String> newSequences = new ArrayList<>();
            for (String seq : sequences) {
                for (String path : paths) {
                    newSequences.add(seq + path);
                }
            }
            sequences = newSequences;
            from = to;
        }
        return sequences;
    }

    public long computeLength(String sequence, int depth) {
        MemoPair mPair = new MemoPair(sequence, depth);
        if (this.memMap.containsKey(mPair)) {
            return memMap.get(mPair);
        }

        if (depth == 1) {
            char from = 'A';
            long sum = 0;
            for (int i = 0; i < sequence.length(); i++) {
                char to = sequence.charAt(i);
                Pair pair = new Pair(from, to);
                sum+=this.shortestLengths.get(pair);
                from = to;
            }
            this.memMap.put(mPair, sum);
            return sum;
        }
        long length = 0;
        char from = 'A';
        for (int i = 0; i < sequence.length(); i++) {
            char to = sequence.charAt(i);
            Pair pair = new Pair(from, to);

            long min = Long.MAX_VALUE;
            for (String seq : this.shortestPaths.get(pair)) {
                min = Math.min(min, computeLength(seq, depth-1));
            }
            length += min;

            from = to;
        }
        this.memMap.put(mPair, length);
        return length;
    }
}
