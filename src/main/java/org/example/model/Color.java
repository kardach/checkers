package org.example.model;

public enum Color {
    BLACK { public Color opposite() { return WHITE; }},
    WHITE { public Color opposite() { return BLACK; }};

    abstract public  Color opposite();
}
