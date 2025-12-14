package leo.aoc.year2015.day11;

import java.util.HashMap;
import java.util.Map;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
  private static final int PASSWORD_LENGTH = 8;

  private static final Map<Character, Integer> CHAR_TO_INT = createCharToIntMap();
  private static final Map<Integer, Character> INT_TO_CHAR = createIntToCharMap();

  private String part1Password = null;

  public Solver(String input) {
    super(input);
  }

  @Override
  public String solvePart1() {
    String result = computeNextPassword(input);
    this.part1Password = result;
    return result;
  }

  @Override
  public String solvePart2() {
    return computeNextPassword(this.part1Password);
  }

  private static Map<Character, Integer> createCharToIntMap() {
    Map<Character, Integer> map = new HashMap<>();
    for (int i = 0; i < LETTERS.length(); i++) {
      map.put(LETTERS.charAt(i), i);
    }
    return map;
  }

  private static Map<Integer, Character> createIntToCharMap() {
    Map<Integer, Character> map = new HashMap<>();
    for (int i = 0; i < LETTERS.length(); i++) {
      map.put(i, LETTERS.charAt(i));
    }
    return map;
  }

  private String computeNextPassword(String password) {
    password = incrementPassword(password);
    while (!verifyPassword(password)) {
      password = incrementPassword(password);
    }
    return password;
  }

  private String incrementPassword(String password) {
    boolean carryOver = true;
    int idx = PASSWORD_LENGTH - 1;
    StringBuilder incrementedPassword = new StringBuilder(password);
    while (carryOver) {
      if (password.charAt(idx) == 'z') {
        incrementedPassword.setCharAt(idx, 'a');
        idx--;
      } else {
        int charValue = CHAR_TO_INT.get(password.charAt(idx));
        char newChar = INT_TO_CHAR.get(charValue + 1);
        incrementedPassword.setCharAt(idx, newChar);
        carryOver = false;
      }
    }
    return incrementedPassword.toString();
  }

  private boolean verifyPassword(String password) {
    return containsIncrementingTriple(password)
        && !containsForbiddenLetters(password)
        && containsPairs(password);
  }

  private boolean containsIncrementingTriple(String password) {
    for (int i = 0; i < password.length() - 2; i++) {
      int charValue1 = CHAR_TO_INT.get(password.charAt(i));
      int charValue2 = CHAR_TO_INT.get(password.charAt(i + 1));
      int charValue3 = CHAR_TO_INT.get(password.charAt(i + 2));
      if (charValue2 - charValue1 == 1 && charValue3 - charValue2 == 1) {
        return true;
      }
    }
    return false;
  }

  private boolean containsForbiddenLetters(String password) {
    return password.indexOf('i') != -1
        || password.indexOf('o') != -1
        || password.indexOf('l') != -1;
  }

  private boolean containsPairs(String password) {
    int numDoubles = 0;
    for (int i = 0; i < password.length() - 1; i++) {
      if (password.charAt(i) == password.charAt(i + 1)) {
        numDoubles++;
        i++;
      }
    }
    return numDoubles >= 2;
  }
}
