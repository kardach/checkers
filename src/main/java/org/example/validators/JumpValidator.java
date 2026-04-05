package org.example.validators;

import org.example.model.*;

final public class JumpValidator {
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

        if(piece.getType() == Type.MAN) {
            if(game.getTurn() == Color.BLACK) {
                return (diffCol == -1 || diffCol == 1) && diffRow == -1;
            } else {
                return (diffCol == -1 || diffCol == 1) && diffRow == 1;
            }
        } else {
            if(game.areFlyingKingsAllowed()) {
                if (diffCol != diffRow && diffCol != -diffRow) {
                    return false;
                }

                boolean pathClear = true;
                int steps = diffCol > 0 ? diffCol : -diffCol;
                int stepCol = diffCol / steps;
                int stepRow = diffRow / steps;

                for(int i = 1; i < steps ; i++) {
                    pathClear &= !game.getBoard().at(from.row() + stepRow * i, from.col() + stepCol * i)
                            .hasPiece();
                }

                return pathClear;
            } else {
                return (diffCol == -1 || diffCol == 1) && (diffRow == -1 || diffRow ==1);
            }
        }
    }
}
