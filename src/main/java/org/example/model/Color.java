package org.example.model;

public enum Color {
    BLACK("Black") { public Color opposite() { return WHITE; }},
    WHITE("White") { public Color opposite() { return BLACK; }};

    private final String color;

    private Color(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Color[%s]".formatted(color);
    }

    abstract public  Color opposite();
}
