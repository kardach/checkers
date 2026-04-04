package org.example.model;

import org.example.validators.CaptureValidator;
import org.example.validators.JumpValidator;
import org.example.options.Variant;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final String variantName;
    private final Board board;
    private final Sequence sequence;
    private Color turn;
    private final int kingsRange;
    private final Variant.Crowning crowning;
    private final boolean backwardsCapture;
    private final boolean flyingKings;
    private int blackPieces;
    private int whitePieces;
    private Color winner;

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

        kingsRange = variant.flyingKings() ? boardSize : 2;
        crowning = variant.crowning();
        backwardsCapture = variant.menCaptureBackwards();
        flyingKings = variant.flyingKings();
        blackPieces = variant.piecesPerSide();
        whitePieces = variant.piecesPerSide();
    }

    public boolean isBackwardsCaptureAllowed() {
        return backwardsCapture;
    }

    public boolean areFlyingKingsAllowed() {
        return flyingKings;
    }

    public boolean isFinished() {
        return winner != null;
    }

    public Color getWinner() {
        return winner;
    }

    public String getVariantName() {
        return variantName;
    }

    public Board getBoard() {
        return board;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public Color getTurn() {
        return turn;
    }

    public List<Move> getLegalMoves(int fromRow, int fromCol) {
        List<Move> moves = new ArrayList<>();
        if (!board.at(fromRow, fromCol).hasPiece()) {
            return moves;
        }
        Piece piece = board.at(fromRow, fromCol).getPiece();
        Position[] diagonals = {new Position(1, 1), new Position(1, -1),
                new Position(-1, 1), new Position(-1, -1)};
        int multiplierMax = piece.getType() == Piece.Type.MAN ? 2 : kingsRange;

        for(int i = 0; i < 4; i++) {
            for(int multiplier = 1 ; multiplier <= multiplierMax; multiplier++) {
                int toRow = fromRow + diagonals[i].row() * multiplier;
                int toCol = fromCol + diagonals[i].col() * multiplier;
                Move possibleMove = new Move(new Position(fromRow, fromCol), new Position(toRow, toCol));
                if(JumpValidator.validate(this, possibleMove)
                        || CaptureValidator.validate(this, possibleMove)) {
                    moves.add(possibleMove);
                }
            }
        }
        return moves;
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
            int diffRow = move.to().row() - move.from().row();
            int diffCol = move.to().col() - move.from().col();
            int steps = diffCol > 0 ? diffCol : -diffCol;
            int stepCol = diffCol / steps;
            int stepRow = diffRow / steps;

            for(int i = 1; i < steps ; i++) {
                int row = move.from().row() + stepRow * i;
                int col = move.from().col() + stepCol * i;
                if(board.at(row, col).hasPiece()) {
                    Piece capturedPiece = board.at(row, col).removePiece();
                    if(capturedPiece.getColor() == Color.BLACK) {
                        blackPieces--;
                    } else {
                        whitePieces--;
                    }
                }
            }
        }
        Piece piece = board.at(moves.getFirst().from()).removePiece();
        if(crowning == Variant.Crowning.ON_FINISH
                && (moves.getLast().to().row() == 0 && piece.getColor() == Color.BLACK
                || moves.getLast().to().row() == board.getSize() - 1 && piece.getColor() == Color.WHITE)) {
            piece.promote();
        }
        board.at(moves.getLast().to()).placePiece(piece);
        changeTurn();
        sequence.clear();

        if(blackPieces == 0) {
            winner = Color.WHITE;
        } else if(whitePieces == 0) {
            winner = Color.BLACK;
        }
    }
}
