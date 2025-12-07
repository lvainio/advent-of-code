package me.vainio.aoc.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DirectionTest {

    @ParameterizedTest
    @CsvSource({
        "EAST, 45, SOUTHEAST",
        "NORTHEAST, 45, EAST", 
        "NORTH, 45, NORTHEAST",
        "NORTHWEST, 45, NORTH",
        "WEST, 45, NORTHWEST",
        "SOUTHWEST, 45, WEST",
        "SOUTH, 45, SOUTHWEST", 
        "SOUTHEAST, 45, SOUTH",
        "EAST, 90, SOUTH",
        "NORTHEAST, 90, SOUTHEAST",
        "NORTH, 90, EAST",
        "NORTHWEST, 90, NORTHEAST", 
        "WEST, 90, NORTH",
        "SOUTHWEST, 90, NORTHWEST",
        "SOUTH, 90, WEST",
        "SOUTHEAST, 90, SOUTHWEST",
        "EAST, 135, SOUTHWEST",
        "NORTH, 135, SOUTHEAST",
        "WEST, 135, NORTHEAST",
        "SOUTH, 135, NORTHWEST",
        "EAST, 180, WEST",
        "NORTHEAST, 180, SOUTHWEST",
        "NORTH, 180, SOUTH", 
        "NORTHWEST, 180, SOUTHEAST",
        "WEST, 180, EAST",
        "SOUTHWEST, 180, NORTHEAST",
        "SOUTH, 180, NORTH",
        "SOUTHEAST, 180, NORTHWEST",
        "EAST, 270, NORTH",
        "NORTH, 270, WEST",
        "WEST, 270, SOUTH",
        "SOUTH, 270, EAST",
        "EAST, 315, NORTHEAST",
        "NORTH, 315, NORTHWEST", 
        "WEST, 315, SOUTHWEST",
        "SOUTH, 315, SOUTHEAST",
        "EAST, 360, EAST",
        "NORTH, 360, NORTH",
        "WEST, 360, WEST", 
        "SOUTH, 360, SOUTH",
        "NORTHEAST, 360, NORTHEAST",
        "NORTHWEST, 360, NORTHWEST",
        "SOUTHWEST, 360, SOUTHWEST",
        "SOUTHEAST, 360, SOUTHEAST",
        "EAST, -45, NORTHEAST",
        "EAST, -90, NORTH", 
        "EAST, -135, NORTHWEST",
        "EAST, -180, WEST",
        "NORTH, -90, WEST",
        "WEST, -90, SOUTH",
        "SOUTH, -90, EAST",
        "NORTHEAST, -45, NORTH",
        "SOUTHEAST, -90, NORTHEAST",
        "EAST, 450, SOUTH",
        "EAST, 720, EAST",
        "EAST, 810, SOUTH",
        "NORTH, 495, SOUTHEAST",
        "WEST, 585, SOUTHEAST",
        "EAST, -450, NORTH",
        "NORTH, -405, NORTHWEST",
        "SOUTH, -540, NORTH"
    })
    void testTurnRight(Direction start, int degrees, Direction expected) {
        assertEquals(expected, start.turnRight(degrees));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 30, 60, 75, 89, 91, 120, 150, 200, 300, 400})
    void testTurnRightInvalidAngles(int invalidAngle) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> Direction.EAST.turnRight(invalidAngle));
    }

    @ParameterizedTest
    @CsvSource({
        "EAST, 45, NORTHEAST",
        "NORTHEAST, 45, NORTH",
        "NORTH, 45, NORTHWEST",
        "NORTHWEST, 45, WEST",
        "WEST, 45, SOUTHWEST",
        "SOUTHWEST, 45, SOUTH",
        "SOUTH, 45, SOUTHEAST", 
        "SOUTHEAST, 45, EAST",
        "EAST, 90, NORTH",
        "NORTHEAST, 90, NORTHWEST",
        "NORTH, 90, WEST",
        "NORTHWEST, 90, SOUTHWEST", 
        "WEST, 90, SOUTH",
        "SOUTHWEST, 90, SOUTHEAST",
        "SOUTH, 90, EAST",
        "SOUTHEAST, 90, NORTHEAST",
        "EAST, 135, NORTHWEST",
        "NORTH, 135, SOUTHWEST",
        "WEST, 135, SOUTHEAST",
        "SOUTH, 135, NORTHEAST",
        "EAST, 180, WEST",
        "NORTHEAST, 180, SOUTHWEST",
        "NORTH, 180, SOUTH", 
        "NORTHWEST, 180, SOUTHEAST",
        "WEST, 180, EAST",
        "SOUTHWEST, 180, NORTHEAST",
        "SOUTH, 180, NORTH",
        "SOUTHEAST, 180, NORTHWEST",
        "EAST, 270, SOUTH",
        "NORTH, 270, EAST",
        "WEST, 270, NORTH",
        "SOUTH, 270, WEST",
        "EAST, 315, SOUTHEAST",
        "NORTH, 315, NORTHEAST", 
        "WEST, 315, NORTHWEST",
        "SOUTH, 315, SOUTHWEST",
        "EAST, 360, EAST",
        "NORTH, 360, NORTH",
        "WEST, 360, WEST", 
        "SOUTH, 360, SOUTH",
        "NORTHEAST, 360, NORTHEAST",
        "NORTHWEST, 360, NORTHWEST",
        "SOUTHWEST, 360, SOUTHWEST",
        "SOUTHEAST, 360, SOUTHEAST",
        "EAST, -45, SOUTHEAST",
        "EAST, -90, SOUTH", 
        "EAST, -135, SOUTHWEST",
        "EAST, -180, WEST",
        "NORTH, -90, EAST",
        "WEST, -90, NORTH",
        "SOUTH, -90, WEST",
        "NORTHEAST, -45, EAST",
        "SOUTHEAST, -90, SOUTHWEST",
        "EAST, 450, NORTH",
        "EAST, 720, EAST",
        "EAST, 810, NORTH",
        "NORTH, 495, SOUTHWEST",
        "WEST, 585, NORTHEAST",
        "EAST, -450, SOUTH",
        "NORTH, -405, NORTHEAST",
        "SOUTH, -540, NORTH"
    })
    void testTurnLeft(Direction start, int degrees, Direction expected) {
        assertEquals(expected, start.turnLeft(degrees));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 30, 60, 75, 89, 91, 120, 150, 200, 300, 400})
    void testTurnLeftInvalidAngles(int invalidAngle) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> Direction.EAST.turnLeft(invalidAngle));
    }

    @ParameterizedTest
    @CsvSource({
        "EAST, 1",
        "NORTHEAST, 1", 
        "NORTH, 0",
        "NORTHWEST, -1",
        "WEST, -1",
        "SOUTHWEST, -1",
        "SOUTH, 0",
        "SOUTHEAST, 1"
    })
    void testGetDx(Direction direction, int expectedDx) {
        assertEquals(expectedDx, direction.getDx());
    }

    @ParameterizedTest
    @CsvSource({
        "EAST, 0",
        "NORTHEAST, 1",
        "NORTH, 1", 
        "NORTHWEST, 1",
        "WEST, 0",
        "SOUTHWEST, -1",
        "SOUTH, -1",
        "SOUTHEAST, -1"
    })
    void testGetDy(Direction direction, int expectedDy) {
        assertEquals(expectedDy, direction.getDy());
    }

    @ParameterizedTest
    @CsvSource({
        "EAST, 0",
        "NORTHEAST, -1",
        "NORTH, -1",
        "NORTHWEST, -1", 
        "WEST, 0",
        "SOUTHWEST, 1",
        "SOUTH, 1",
        "SOUTHEAST, 1"
    })
    void testGetDr(Direction direction, int expectedDr) {
        assertEquals(expectedDr, direction.getDr());
    }

    @ParameterizedTest
    @CsvSource({
        "EAST, 1",
        "NORTHEAST, 1", 
        "NORTH, 0",
        "NORTHWEST, -1",
        "WEST, -1",
        "SOUTHWEST, -1",
        "SOUTH, 0",
        "SOUTHEAST, 1"
    })
    void testGetDc(Direction direction, int expectedDc) {
        assertEquals(expectedDc, direction.getDc());
    }
}
