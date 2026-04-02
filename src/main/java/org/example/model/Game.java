package org.example.model;

import org.example.model.validators.CaptureValidator;
import org.example.model.validators.JumpValidator;
import org.example.options.Variant;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final String variantName;
    private final Board board;
    private final Sequence sequence;
    private Color turn;
    private final int kingsRange;
    private Variant.Crowning crowning;

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
        if(board.at(fromRow, fromCol).hasPiece()) {
            Piece piece = board.at(fromRow, fromCol).getPiece();
            Position[] diagonals = {new Position(1, 1), new Position(1, -1),
                    new Position(-1, 1), new Position(-1, -1)};
            int multiplierMax;
            if(piece.getType() == Piece.Type.MAN) {
                multiplierMax = 2;
            } else {
                multiplierMax =  kingsRange;
            }
            for(int multiplier = 1 ; multiplier <= multiplierMax; multiplier++) {
                for(int i = 0; i < 4; i++) {
                    int toRow = fromRow + diagonals[i].row() * multiplier;
                    int toCol = fromCol + diagonals[i].col() * multiplier;
                    Move possibleMove = new Move(new Position(fromRow, fromCol), new Position(toRow, toCol));
                    if(0 <= toRow && toRow <= board.getSize() - 1 && 0 <= toCol && toCol <= board.getSize() - 1
                            && (JumpValidator.validate(this, possibleMove)
                            || CaptureValidator.validate(this, possibleMove))) {
                        moves.add(possibleMove);
                    }
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
        System.out.println(moves);
        for(Move move : moves) {
            int diffRow = move.from().row() - move.to().row();
            int diffCol = move.from().col() - move.to().col();
            System.out.println(diffRow + " " + diffCol);
            if(Math.abs(diffRow) == 2 && Math.abs(diffCol) == 2) {
                board.at(move.from().row() + diffRow / 2, move.from().col() + diffCol / 2).removePiece();
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
    }
}
