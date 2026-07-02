package checkers.model;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
    TOP,
    TOP_RIGHT,
    RIGHT,
    BOTTOM_RIGHT,
    BOTTOM,
    BOTTOM_LEFT,
    LEFT,
    TOP_LEFT;

    public static List<Direction> getOrthogonal() {
        return new ArrayList<>(List.of(TOP, RIGHT, BOTTOM, LEFT));
    }

    public static List<Direction> getDiagonal() {
        return new ArrayList<>(List.of(TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT, TOP_LEFT));
    }
}
