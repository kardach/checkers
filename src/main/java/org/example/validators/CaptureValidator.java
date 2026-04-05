package org.example.validators;

import org.example.model.*;

public class CaptureValidator {
    public static boolean validate(Game game, Move move) {
        Position from = move.from();
        Position to = move.to();
        int max = game.getBoard().getSize() - 1;

        if(from.row() < 0 || from.row() > max || from.col() < 0 || from.col() > max || to.row() < 0 || to.row() > max
                || to.col() < 0 || to.col() > max) {
            return false;
        }

        if(game.getBoard().at(to).hasPiece() || !game.getBoard().at(from).hasPiece()) {
            return false;
        }

        Piece piece = game.getBoard().at(from).getPiece();
        int diffCol = to.col() - from.col();
        int diffRow = to.row() - from.row();

        int col = move.to().col() - move.from().col();
        int row = move.to().row() - move.from().row();

        Position jumpedOver = new Position(move.from().row() + row / 2,
                move.from().col() + col / 2);
        if(piece.getType() == Type.MAN) {
            if(!game.getBoard().at(jumpedOver).hasPiece()) {
                return false;
            }

            Piece capturedPiece = game.getBoard().at(jumpedOver).getPiece();

            if(game.canMenCaptureBackwards()) {
                return Math.abs(diffCol) == 2 && Math.abs(diffRow) == 2 && piece.getColor() != capturedPiece.getColor();
            } else {
                return  Math.abs(diffCol) == 2
                        && game.getTurn() == Color.BLACK ? diffRow == -2 : diffRow == 2
                        && piece.getColor() != capturedPiece.getColor();
            }
        } else {
            if(game.areFlyingKingsAllowed()) {
                if (diffCol != diffRow && diffCol != -diffRow) {
                    return false;
                }

                boolean pathClear = true;
                int captureCount = 0;
                int steps = diffCol > 0 ? diffCol : -diffCol;
                int stepCol = diffCol / steps;
                int stepRow = diffRow / steps;

                for(int i = 1; i < steps ; i++) {
                    Position fliedOver = new Position(from.row() + stepRow * i, from.col() + stepCol * i);
                    Piece capturedPiece;
                    if(game.getBoard().at(fliedOver).hasPiece()) {
                        capturedPiece = game.getBoard().at(fliedOver).getPiece();
                        pathClear &= piece.getColor() != capturedPiece.getColor();
                        captureCount += piece.getColor() != capturedPiece.getColor() ? 1 : 0;
                    }
                }

                return pathClear && captureCount == 1;
            } else {
                if(!game.getBoard().at(jumpedOver).hasPiece()) {
                    return false;
                }

                Piece capturedPiece = game.getBoard().at(jumpedOver).getPiece();

                return Math.abs(diffCol) == 2 && Math.abs(diffRow) == 2 && piece.getColor() != capturedPiece.getColor();            }
        }
    }
}
