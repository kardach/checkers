package org.example.model.validators;

import org.example.model.Color;
import org.example.model.Game;
import org.example.model.Move;

public class JumpValidator {
    public static boolean validate(Game game, Move move) {
        if(game.getBoard().at(move.to()).hasPiece()) {
            return false;
        }
        int col = move.to().col() - move.from().col();
        int row = move.to().row() - move.from().row();
        if(game.getTurn() == Color.BLACK) {
            return (col == -1 || col == 1) && row == -1;
        } else {
            return (col == -1 || col == 1) && row == 1;
        }
    }
}
