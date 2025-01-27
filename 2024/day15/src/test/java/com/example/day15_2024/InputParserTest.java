package com.example.day15_2024;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


public class InputParserTest {

    private static InputParser ip1 = new InputParser();
    private static InputParser ip2 = new InputParser();
    private static InputParser ip3 = new InputParser();

    @BeforeAll
    public static void setupOnce() {
        ip1.parseInputFile("test_input1.txt");
        ip2.parseInputFile("test_input2.txt");
    }

    @Test
    public void parseInputFile_shouldThrowExceptionWhenFileContainsInvalidCharacter() {
        assertThrows(IllegalArgumentException.class, () -> {
            ip3.parseInputFile("invalid_test_input1.txt");
        }, "Expected IllegalArgumentException for invalid_test_input1.txt");

        assertThrows(IllegalArgumentException.class, () -> {
            ip3.parseInputFile("invalid_test_input2.txt");
        }, "Expected IllegalArgumentException for invalid_test_input2.txt");
    }

    @Test
    public void getMap_shouldReturnCorrectMap() {
        Cell[][] expectedMap = {
            {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
            {Cell.WALL, Cell.EMPTY, Cell.EMPTY, Cell.BOX, Cell.EMPTY, Cell.BOX, Cell.EMPTY, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.ROBOT, Cell.EMPTY, Cell.BOX, Cell.EMPTY, Cell.EMPTY, Cell.WALL},
            {Cell.WALL, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.BOX, Cell.EMPTY, Cell.EMPTY, Cell.WALL},
            {Cell.WALL, Cell.EMPTY, Cell.WALL, Cell.EMPTY, Cell.BOX, Cell.EMPTY, Cell.EMPTY, Cell.WALL},
            {Cell.WALL, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.BOX, Cell.EMPTY, Cell.EMPTY, Cell.WALL},
            {Cell.WALL, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL}
        };
        assertArrayEquals(expectedMap, ip2.getMap1());
    }

    @Test
    public void getMoves_shouldReturnCorrectMoves() {
        List<Move> expectedMoves = List.of(
            Move.LEFT, Move.UP, Move.UP, Move.RIGHT, Move.RIGHT, Move.RIGHT, 
            Move.DOWN, Move.DOWN, Move.LEFT, Move.DOWN, Move.RIGHT, Move.RIGHT, Move.DOWN, 
            Move.LEFT, Move.LEFT
        );
        assertEquals(expectedMoves, ip2.getMoves());
    }
}
