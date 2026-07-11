package checkers.model;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record PiecePlacement(Color color, Type type, Position position) {
}
