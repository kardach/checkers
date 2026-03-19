package org.example.model;

import org.example.model.validators.CaptureValidator;
import org.example.model.validators.JumpValidator;
import org.example.options.Variant;

import java.util.List;

public class Game {
    private final String variantName;
    private final Board board;
    private final Sequence sequence;
    private Color turn;

    public Game(Variant variant) {
        variantName = variant.name();
        board = new Board(variant.boardSize(), variant.lightSquareOnNearRight());
        sequence = new Sequence();
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

    public Sequence getMove() {
        return sequence;
    }

    public Color getTurn() {
        return turn;
    }

    public boolean validateSubMove(int row, int col) {
        if(!sequence.isStarted()) {
            return board.at(row, col).hasPiece()  && turn == board.at(row,col).getPiece().getColor();
        } else if(sequence.isStarted() && sequence.isEmpty()){
            Move move = new Move(sequence.getStart(), new Position(row, col));
            return JumpValidator.validate(this, move) || CaptureValidator.validate(this, move);
        } else {
            Move move = new Move(sequence.getMoves().getLast().to(), new Position(row, col));
            Move first = sequence.getMoves().getFirst();
            return !JumpValidator.validate(this, first) && CaptureValidator.validate(this, move);
        }
    }

    private void changeTurn() {
        if(turn == Color.BLACK) {
            turn = Color.WHITE;
        } else {
            turn = Color.BLACK;
        }
    }

    public void performMove() {
        List<Move> moves = sequence.getMoves();
        for(Move move : moves) {
            System.out.println(move);
            int row = (move.from().row() + move.to().row()) / 2;
            int col = (move.from().col() + move.to().col()) / 2;
            if(row != 0 && col != 0) {
                board.at(row, col).removePiece();
            }
        }
        Piece piece = board.at(moves.getFirst().from()).removePiece();
        board.at(moves.getLast().to()).placePiece(piece);
        changeTurn();
        sequence.clear();
    }
}
