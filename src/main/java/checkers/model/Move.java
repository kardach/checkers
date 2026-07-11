package checkers.model;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record Move(Position from, Position to) {
}
