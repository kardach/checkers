package checkers.model;

import org.jspecify.annotations.NullMarked;

@NullMarked
public enum Color {
    BLACK("Black") {
        @Override
        public Color opposite() {
            return WHITE;
        }
    },
    WHITE("White") {
        @Override
        public Color opposite() {
            return BLACK;
        }
    };

    private final String color;

    Color(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Color[%s]".formatted(color);
    }

    abstract public Color opposite();
}
