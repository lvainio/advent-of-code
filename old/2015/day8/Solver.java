package leo.aoc.year2015.day8;

import java.util.List;
import java.util.regex.Pattern;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private List<String> strings;

  public Solver(String input) {
    super(input);
    this.strings = input.lines().toList();
  }

  @Override
  public String solvePart1() {
    int charsOfCode = this.strings.stream().mapToInt(this::countCharsOfCode).sum();
    int charsInMemory = this.strings.stream().mapToInt(this::countCharsInMemory).sum();
    return Integer.toString(charsOfCode - charsInMemory);
  }

  @Override
  public String solvePart2() {
    int charsOfCode = this.strings.stream().mapToInt(this::countCharsOfCode).sum();
    int charsOfEncoded = this.strings.stream().mapToInt(this::countEncodedChars).sum();
    return Integer.toString(charsOfEncoded - charsOfCode);
  }

  public int countCharsOfCode(String str) {
    return str.length();
  }

  public int countCharsInMemory(String str) {
    str = str.substring(1, str.length() - 1);

    String hexEscape = "\\\\x[0-9a-f]{2}";
    String backSlashEscape = "\\\\";
    String doubleQuoteEscape = "\\\"";

    int i = 0;
    int count = 0;
    while (i < str.length()) {
      // hex match
      if (i <= str.length() - 4) {
        String subString = str.substring(i, i + 4);
        if (Pattern.compile(hexEscape).matcher(subString).find()) {
          i += 4;
          count++;
          continue;
        }
      }
      // backslash and quote
      if (i <= str.length() - 2) {
        String subString = str.substring(i);
        if (subString.startsWith(backSlashEscape) || subString.startsWith(doubleQuoteEscape)) {
          i += 2;
          count++;
          continue;
        }
      }
      i++;
      count++;
    }
    return count;
  }

  private int countEncodedChars(String str) {
    String strEncoded = encode(str);
    return countCharsOfCode(strEncoded);
  }

  private String encode(String str) {
    StringBuilder sb = new StringBuilder();
    sb.append('"');
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '\\') {
        sb.append("\\\\");
      } else if (str.charAt(i) == '"') {
        sb.append("\\\"");
      } else {
        sb.append(str.charAt(i));
      }
    }
    sb.append('"');
    return sb.toString();
  }
}
