package org.example.model.validators;

import org.example.model.*;

public class CaptureValidator {
    public static boolean validate(Game game, Move move) {
        if(game.getBoard().at(move.to()).hasPiece()) {
            return false;
        }
        int col = move.to().col() - move.from().col();
        int row = move.to().row() - move.from().row();
        Position jumpedOver = new Position(move.from().row() + row / 2,
                move.from().col() + col / 2);
        if(game.getTurn() == Color.BLACK) {
            return (col == -2 || col == 2) && row == -2 && game.getBoard().at(jumpedOver).hasPiece()
                    && game.getBoard().at(jumpedOver).getPiece().getColor() == Color.WHITE;
        } else {
            return (col == -2 || col == 2) && row == 2 && game.getBoard().at(jumpedOver).hasPiece()
                    && game.getBoard().at(jumpedOver).getPiece().getColor() == Color.BLACK;
        }
    }
}
