package checkers.model;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class Piece {

    private final Color color;
    private Type type;

    public Piece(Color color) {
        this(color, Type.MAN);
    }

    public Piece(Color color, Type type) {
        this.color = color;
        this.type = type;
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
