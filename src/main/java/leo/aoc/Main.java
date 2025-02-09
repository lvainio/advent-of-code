package leo.aoc;

public class Main {

    public static void main(String[] args) {
        String year = null;
        String day = null;

        for (int i = 0; i < args.length; i+=2) {
            switch (args[i]) {
                case "-year" -> year = args[i + 1];
                case "-day" -> day = args[i + 1];
                default -> 
                    throw new IllegalArgumentException("Invalid command line flag: " + args[i]);
            }
        }

        System.out.println();
        System.out.println("*-----------------------------*");
        System.out.println("*   Solving Advent of Code!   *");
        System.out.println("*-----------------------------*");
        System.out.println();
        System.out.println("  Year:  " + year);
        System.out.println("  Day:   " + day);
        System.out.println();

        final String sessionCookie = System.getenv("AOC_SESSION");

        if (sessionCookie != null && !sessionCookie.isEmpty()) {
            System.out.println("  AOC Session cookie found!");
        } else {
            System.err.println("ERROR: no session cookie found!");
            System.err.println("HINT: make sure the AOC_SESSION environment variable is set.");
            System.exit(1);
        }
        System.out.println();



        System.out.println("*-----------------------------*");
    }
}
