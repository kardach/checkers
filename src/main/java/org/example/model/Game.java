package org.example.model;

import org.example.options.Variant;

public class Game {
    private final String variantName;
    private final Board board;
    private final Move move;

    public Game(Variant variant) {
        variantName = variant.name();
        board = new Board(variant.boardSize(), variant.lightSquareOnNearRight());
        move = new Move();
        int piecesPerSide = variant.piecesPerSide();
        int boardSize = variant.boardSize();
        int row = 0;
        int col;
        if(variant.piecesPlacement() == Variant.PiecesPlacement.ON_BLACK && variant.lightSquareOnNearRight() ||
        variant.piecesPlacement() == Variant.PiecesPlacement.ON_WHITE && !variant.lightSquareOnNearRight()) {
            col = 1;
        } else {
            col = 0;
        }

        for(int i = 0; i < piecesPerSide; i++) {
            board.at(row, col).placePiece(new Piece(Piece.Color.WHITE));
            board.at(boardSize - row - 1, boardSize - col - 1).placePiece(new Piece(Piece.Color.BLACK));
            System.out.println(row + " " + col);
            col += 2;
            if(col >= boardSize) {
                row += 1;
                col = (col + 1) % 2;
            }
        }
    }

    public String getVariantName() {
        return variantName;
    }

    public Board getBoard() {
        return board;
    }
}
