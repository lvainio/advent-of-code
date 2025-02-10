package leo.aoc.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    public void testTurnRight() {
        assertSame(Direction.EAST, Direction.NORTH.turnRight(90));
        assertSame(Direction.WEST, Direction.NORTH.turnRight(-90));
        assertSame(Direction.WEST, Direction.EAST.turnRight(180 + 360));
        assertSame(Direction.SOUTHEAST, Direction.NORTHEAST.turnRight(90));

        assertThrows(IllegalArgumentException.class, () -> Direction.NORTH.turnRight(36));
        assertThrows(IllegalArgumentException.class, () -> Direction.EAST.turnRight(-38));
    }

    @Test
    public void testTurnLeft() {
        assertSame(Direction.WEST, Direction.NORTH.turnLeft(90));
        assertSame(Direction.EAST, Direction.NORTH.turnLeft(-90));
        assertSame(Direction.WEST, Direction.EAST.turnLeft(180 + 360));
        assertSame(Direction.NORTHWEST, Direction.NORTHEAST.turnLeft(90));

        assertThrows(IllegalArgumentException.class, () -> Direction.NORTH.turnLeft(36)); 
        assertThrows(IllegalArgumentException.class, () -> Direction.EAST.turnLeft(-38)); 
    }
}
