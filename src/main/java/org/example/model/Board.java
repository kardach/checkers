package org.example.model;

import java.util.ArrayList;

public class Board {
    private final int size;
    private final Square[][] squares;
    private int blackPieces;
    private int whitePieces;

    private Board(int size, boolean lightSquareOnNearRight) {
        this.size = size;
        squares = new Square[size][size];
        for(int row = 0; row < size; row++) {
            for(int col = 0; col < size; col++) {
                if(lightSquareOnNearRight) {
                    squares[row][col] = new Square((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);
                } else {
                    squares[row][col] = new Square((row + col) % 2 == 0 ? Color.BLACK : Color.WHITE);
                }
            }
        }
    }

    public Board(int size, boolean lightSquareOnNearRight, ArrayList<CustomPiecePlacement> piecePlacements) {
        this(size, lightSquareOnNearRight);

        for(var piecePlacement : piecePlacements) {
            at(piecePlacement.row(), piecePlacement.col()).placePiece(
                    new Piece(piecePlacement.color(), piecePlacement.type()));

            if(piecePlacement.color() == Color.BLACK) {
                blackPieces++;
            } else {
                whitePieces++;
            }
        }
    }

    public Board(int size, boolean lightSquareOnNearRight, int piecesPerSide, PiecePlacement piecePlacement) {
        this(size, lightSquareOnNearRight);

        int row = 0;
        int col = piecePlacement == PiecePlacement.ON_BLACK && lightSquareOnNearRight
                || piecePlacement == PiecePlacement.ON_WHITE && !lightSquareOnNearRight ? 1 : 0;
        for(int i = 0; i < piecesPerSide; i++) {
            squares[row][col].placePiece(new Piece(Color.WHITE, Type.MAN));
            squares[size - row -1][size - col -1].placePiece(new Piece(Color.BLACK, Type.MAN));

            col += 2;
            if(col >= size) {
                row += 1;
                col = (col + 1) % 2;
            }
        }

        blackPieces = piecesPerSide;
        whitePieces = piecesPerSide;
    }

    public int getSize() {
        return size;
    }

    public int getBlackPieceCount() {
        return blackPieces;
    }

    public int getWhitePieceCount() {
        return whitePieces;
    }

    public Square at(int row, int col) {
        return squares[row][col];
    }

    public Square at(Position position) {
        return squares[position.row()][position.col()];
    }
}
