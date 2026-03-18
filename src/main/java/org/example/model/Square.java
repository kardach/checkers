package org.example.model;

public class Square {
    private final Color color;
    private Piece piece;

    public Square(Color color) {
        this(color, null);
    }

    public Square(Color color, Piece piece) {
        this.color = color;
        this.piece = piece;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public Piece removePiece() {
        Piece temp = piece;
        piece = null;
        return temp;
    }

    public void placePiece(Piece piece) {
        this.piece = piece;
    }
}
