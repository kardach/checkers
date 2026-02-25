package org.example.model;

public class Piece {
    public enum Type {
        MAN,
        KING,
    }

    public enum Color {
        BLACK,
        WHITE,
    }

    private Type type;
    private final Color color;

    public Piece(Color color) {
        type = Type.MAN;
        this.color = color;
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
