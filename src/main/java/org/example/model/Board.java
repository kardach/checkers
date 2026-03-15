package org.example.model;

public class Board {
    private final int size;
    private final Square[][] squares;

    public Board(int size, boolean lightSquareOnNearRight) {
        this.size = size;
        squares = new Square[size][size];
        for(int row = 0; row < size; row++) {
            for(int col = 0; col < size; col++) {
                if(lightSquareOnNearRight) {
                    if((row + col) % 2 == 0) {
                        squares[row][col] = new Square(Square.Color.WHITE);
                    } else {
                        squares[row][col] = new Square(Square.Color.BLACK);
                    }
                } else {
                    if((row + col) % 2 == 0) {
                        squares[row][col] = new Square(Square.Color.BLACK);
                    } else {
                        squares[row][col] = new Square(Square.Color.WHITE);
                    }
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Square at(int row, int col) {
        return squares[row][col];
    }

    Square at(Move.Position position) {
        return squares[position.row()][position.col()];
    }
}
