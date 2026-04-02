package org.example.model;

public class Piece {
    public enum Type {
        MAN,
        KING,
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
        System.out.println("promote");
        type = Type.KING;
    }
}
