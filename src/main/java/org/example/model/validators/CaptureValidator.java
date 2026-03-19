package org.example.model.validators;

import org.example.model.Color;
import org.example.model.Game;
import org.example.model.Move;

public class CaptureValidator {
    public static boolean validate(Game game, Move.SubMove subMove) {
        if(game.getBoard().at(subMove.to()).hasPiece()) {
            return false;
        }
        int col = subMove.to().col() - subMove.from().col();
        int row = subMove.to().row() - subMove.from().row();
        Move.Position jumpedOver = new Move.Position(subMove.from().row() + row / 2,
                subMove.from().col() + col / 2);
        if(game.getTurn() == Color.BLACK) {
            return (col == -2 || col == 2) && row == -2 && game.getBoard().at(jumpedOver).hasPiece()
                    && game.getBoard().at(jumpedOver).getPiece().getColor() == Color.WHITE;
        } else {
            return (col == -2 || col == 2) && row == 2 && game.getBoard().at(jumpedOver).hasPiece()
                    && game.getBoard().at(jumpedOver).getPiece().getColor() == Color.BLACK;
        }
    }
}
