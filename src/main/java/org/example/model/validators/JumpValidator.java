package org.example.model.validators;

import org.example.model.Board;
import org.example.model.Color;
import org.example.model.Game;
import org.example.model.Move;

public class JumpValidator {
    public static boolean validate(Game game, Move.SubMove subMove) {
        if(game.getBoard().at(subMove.to()).hasPiece()) {
            return false;
        }
        int col = subMove.to().col() - subMove.from().col();
        int row = subMove.to().row() - subMove.from().row();
        if(game.getTurn() == Color.BLACK) {
            return (col == -1 || col == 1) && row == -1;
        } else {
            return (col == -1 || col == 1) && row == 1;
        }
    }
}
