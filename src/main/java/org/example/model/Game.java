package org.example.model;

import org.example.options.Variant;

import java.util.List;

public class Game {
    private final String variantName;
    private final Board board;
    private final Move move;
    private Color turn;

    public Game(Variant variant) {
        variantName = variant.name();
        board = new Board(variant.boardSize(), variant.lightSquareOnNearRight());
        move = new Move();
        if(variant.firstMove() == Variant.FirstMove.BLACK) {
            turn = Color.BLACK;
        } else {
            turn = Color.WHITE;
        }
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
            board.at(row, col).placePiece(new Piece(Color.WHITE));
            board.at(boardSize - row - 1, boardSize - col - 1).placePiece(new Piece(Color.BLACK));
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

    public Move getMove() {
        return move;
    }

    public Color getTurn() {
        return turn;
    }

    private void changeTurn() {
        if(turn == Color.BLACK) {
            turn = Color.WHITE;
        } else {
            turn = Color.BLACK;
        }
    }

    public void performMove() {
        List<Move.SubMove> subMoves = move.getSubMoves();
        for(Move.SubMove subMove : subMoves) {
            System.out.println(subMove);
        }
        move.clear();
        changeTurn();
//        Piece piece = board.at(subMoves.getFirst().from()).removePiece();
//        board.at(subMoves.getLast().to()).placePiece(piece);
    }
}
