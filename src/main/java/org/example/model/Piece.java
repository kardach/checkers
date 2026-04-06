package org.example.model;

public class Piece {

    private Type type;
    private final Color color;

    public Piece(Color color) {
        this(color, Type.MAN);
    }

    public Piece(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    public Piece(Piece piece) {
        this.color = piece.color;
        this.type = piece.type;
    }

    public Type getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public void promote() {
        type = Type.KING;
    }
}
