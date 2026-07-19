package checkers.model;

import java.util.List;

public enum Direction {
    TOP,
    TOP_RIGHT,
    RIGHT,
    BOTTOM_RIGHT,
    BOTTOM,
    BOTTOM_LEFT,
    LEFT,
    TOP_LEFT,
    NONE,
    OTHER;

    public static List<Direction> getOrthogonal() {
        return List.of(TOP, RIGHT, BOTTOM, LEFT);
    }

    public static List<Direction> getDiagonal() {
        return List.of(TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT, TOP_LEFT);
    }
}
