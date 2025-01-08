package com.example.day08_2024;

public record Node(int row, int col) {

    public boolean isInsideGrid(int numRows, int numCols) {
        return !(row < 0 || row >= numRows ||
                col < 0 || col >= numCols);
    }
}
