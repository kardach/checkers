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
        Piece[] blackPieces = new Piece[piecesPerSide];
        Piece[] whitePieces = new Piece[piecesPerSide];

        for(int i = 0; i < piecesPerSide; i++) {
            blackPieces[i] = new Piece(Piece.Color.BLACK);
            whitePieces[i] = new Piece(Piece.Color.WHITE);
        }

        for(int i = 0; i < boardSize * boardSize / 2; i += 2) {
            board.at(i / boardSize, i % boardSize).placePiece(new Piece(Piece.Color.WHITE));
        }
    }

    public String getVariantName() {
        return variantName;
    }

    public Board getBoard() {
        return board;
    }
}
