package com.example.day15_2024;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    @Test
    public void fromChar_shouldReturnCorrectCellType() {
        assertEquals(Cell.WALL, Cell.fromChar('#'), "Failed for WALL");
        assertEquals(Cell.BOX, Cell.fromChar('O'), "Failed for BOX");
        assertEquals(Cell.EMPTY, Cell.fromChar('.'), "Failed for EMPTY");
        assertEquals(Cell.ROBOT, Cell.fromChar('@'), "Failed for ROBOT");
    }

    @Test
    public void getSymbol_shouldReturnTheCorrectSymbol() {
        assertEquals('#', Cell.WALL.getSymbol(), "Failed for WALL");
        assertEquals('O', Cell.BOX.getSymbol(), "Failed for BOX");
        assertEquals('.', Cell.EMPTY.getSymbol(), "Failed for EMPTY");
        assertEquals('@', Cell.ROBOT.getSymbol(), "Failed for ROBOT");
    }

    @Test
    public void fromChar_shouldThrowExceptionForInvalidChar() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Cell.fromChar('X');
        });
        assertEquals("Invalid character: X", exception.getMessage());
    }
}
