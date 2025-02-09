package leo.aoc;

public class Main {

    public static void main(String[] args) {
        String year = null;
        String day = null;

        for (int i = 0; i < args.length; i+=2) {
            switch (args[i]) {
                case "-year" -> year = args[i + 1];
                case "-day" -> day = args[i + 1];
                default -> throw new IllegalArgumentException();
            }
        }

        System.out.println();
        System.out.println("*-----------------------------*");
        System.out.println("*   Solving Advent of Code!   *");
        System.out.println("*-----------------------------*");
        System.out.println("  Year:  " + year);
        System.out.println("  Day:   " + day);
        System.out.println("*-----------------------------*");
        System.out.println();

        String sessionCookie = System.getenv("AOC_SESSION");
        System.out.println("SESSION COOKIE: " + sessionCookie);
    }
}
