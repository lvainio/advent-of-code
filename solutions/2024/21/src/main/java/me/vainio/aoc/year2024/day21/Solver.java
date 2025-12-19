package me.vainio.aoc.year2024.day21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import me.vainio.aoc.cache.AocCache;

public class Solver {
  private static final int YEAR = 2024;
  private static final int DAY = 21;

  private final List<String> codes;

  public static void main(final String[] args) {
    final AocCache cache = new AocCache();

    final String input = cache.getInput(YEAR, DAY);
    final Solver solver = new Solver(input);

    final String part1 = solver.solvePart1();
    final String part2 = solver.solvePart2();

    System.out.println(part1);
    System.out.println(part2);

    cache.saveAnswer(YEAR, DAY, 1, part1);
    cache.saveAnswer(YEAR, DAY, 2, part2);
  }

  public Solver(final String input) {
    this.codes = input.lines().toList();
  }

  public String solvePart1() {
    Keypad nkp = new Keypad(Keypad.NUMERIC_KEYPAD);
    Keypad dkp = new Keypad(Keypad.DIRECTIONAL_KEYPAD);

    long total = 0;
    for (String code : codes) {
      List<String> inputs = nkp.getAllInputSequences(code);

      long min = Long.MAX_VALUE;
      for (String input : inputs) {
        min = Math.min(min, dkp.computeLength(input, 2));
      }
      total += min * Integer.parseInt(code.substring(0, 3));
    }
    return Long.toString(total);
  }

  public String solvePart2() {
    Keypad nkp = new Keypad(Keypad.NUMERIC_KEYPAD);
    Keypad dkp = new Keypad(Keypad.DIRECTIONAL_KEYPAD);

    long total = 0;
    for (String code : codes) {
      List<String> inputs = nkp.getAllInputSequences(code);

      long min = Long.MAX_VALUE;
      for (String input : inputs) {
        min = Math.min(min, dkp.computeLength(input, 25));
      }
      total += min * Integer.parseInt(code.substring(0, 3));
    }
    return Long.toString(total);
  }

  private enum Direction {
    NORTH(-1, 0, '^'),
    SOUTH(1, 0, 'v'),
    WEST(0, -1, '<'),
    EAST(0, 1, '>');

    private final int dr;
    private final int dc;
    private final char symbol;

    Direction(int dr, int dc, char symbol) {
      this.dr = dr;
      this.dc = dc;
      this.symbol = symbol;
    }

    public int getDr() {
      return this.dr;
    }

    public int getDc() {
      return this.dc;
    }

    public char getSymbol() {
      return this.symbol;
    }
  }

  public static class Keypad {
    private record Node(int row, int col) {}

    private record BFSNode(Node node, String path) {}

    private record Pair(char from, char to) {}

    private record MemoPair(String sequence, int depth) {}

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

    private final HashMap<Pair, HashSet<String>> shortestPaths;

    private final HashMap<Pair, Integer> shortestLengths;

    private final Map<MemoPair, Long> memMap;

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

        while (!queue.isEmpty()) {
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
              BFSNode newNode = new BFSNode(new Node(newRow, newCol), path + dir.getSymbol());
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
      for (Character[] characters : keypad) {
        for (int col1 = 0; col1 < keypad[0].length; col1++) {
          Character c1 = characters[col1];
          if (c1 == null) {
            continue;
          }
          for (Character[] value : keypad) {
            for (int col2 = 0; col2 < keypad[0].length; col2++) {
              Character c2 = value[col2];
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
          sum += this.shortestLengths.get(pair);
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
          min = Math.min(min, computeLength(seq, depth - 1));
        }
        length += min;

        from = to;
      }
      this.memMap.put(mPair, length);
      return length;
    }
  }
}
