package org.example.model;

import java.util.Optional;

public class Square {
    public enum Color {
        BLACK,
        WHITE,
    }

    private final Color color;
    private Optional<Piece> piece;

    public Square(Color color, Piece piece) {
        this.color = color;
        this.piece = Optional.of(piece);
    }

    public Square(Color color) {
        this.color = color;
        piece = Optional.empty();
    }

    public Color getColor() {
        return color;
    }

    public Optional<Piece> getPiece() {
        return piece;
    }

    public Optional<Piece> removePiece() {
        Optional<Piece> temp = piece;
        piece = Optional.empty();
        return temp;
    }

    public void placePiece(Piece piece) {
        this.piece = Optional.of(piece);
    }
}
