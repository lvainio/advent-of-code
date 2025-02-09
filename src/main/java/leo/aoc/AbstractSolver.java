package leo.aoc;

/**
 * Abstract class that serves as a base for solving Advent of Code (AOC) problems.
 * 
 * This class provides a common structure for all solver classes, ensuring that
 * each solver has the necessary methods to solve both parts of the problem. 
 * It also handles the input data, which is shared across all solver classes.
 */
public abstract class AbstractSolver {
    
    /** 
     * The input data for the problem.
     */
    protected final String input;

    /**
     * Constructs an instance of AbstractSolver with the given input data.
     *
     * @param input The input data for the day's problem.
     */
    public AbstractSolver(String input) {
        this.input = input;
    }

    /**
     * Solves Part 1 of the problem.
     * 
     * This method should be implemented by subclasses to provide the solution for Part 1.
     * The implementation will depend on the specific problem for the given day.
     * 
     * @return The solution for Part 1.
     */
    public abstract String solvePart1();

    /**
     * Solves Part 2 of the problem.
     * 
     * This method should be implemented by subclasses to provide the solution for Part 2.
     * The implementation will depend on the specific problem for the given day.
     * 
     * @return The solution for Part 2.
     */
    public abstract String solvePart2();
}
