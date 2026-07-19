package checkers.model;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record Position(int row, int col) {

    private record Difference(int diffRow, int diffCol) {

        public boolean isOrthogonal() {
                return diffRow == 0 && diffCol != 0 || diffRow != 0 && diffCol == 0;
            }

            public boolean isDiagonal() {
                return Math.abs(diffRow) == Math.abs(diffCol) && diffRow != 0 && diffCol != 0;
            }

            public boolean isNone() {
                return diffRow == 0 && diffCol == 0;
            }

            public boolean isOther() {
                return !isNone() && !isDiagonal() && !isOrthogonal();
            }
        }

    private Difference getDifference(Position other) {
        return new Difference(row - other.row, col - other.col);
    }

    public Direction getDirection(Position other) {
        Difference difference = getDifference(other);
        if (difference.isOrthogonal()) {
            if (difference.diffRow() < 0) {
                return Direction.BOTTOM;
            } else if (difference.diffRow() > 0) {
                return Direction.TOP;
            } else if (difference.diffCol() < 0) {
                return Direction.RIGHT;
            } else if (difference.diffCol() > 0) {
                return Direction.LEFT;
            }
        } else if (difference.isDiagonal()) {
            if (difference.diffRow() < 0 && difference.diffCol() < 0) {
                return Direction.BOTTOM_RIGHT;
            } else if (difference.diffRow() > 0 && difference.diffCol() < 0) {
                return Direction.TOP_RIGHT;
            } else if (difference.diffRow() > 0 && difference.diffCol() > 0) {
                return Direction.TOP_LEFT;
            } else if (difference.diffRow() < 0 && difference.diffCol() > 0) {
                return Direction.BOTTOM_LEFT;
            }
        } else if (difference.isNone()) {
            return Direction.NONE;
        }
        return Direction.OTHER;
    }

    public Position translate(Direction direction, int amount) {
        return switch (direction) {
            case TOP            -> new Position(row - amount, col);
            case TOP_RIGHT      -> new Position(row - amount, col + amount);
            case RIGHT          -> new Position(row, col + amount);
            case BOTTOM_RIGHT   -> new Position(row + amount, col + amount);
            case BOTTOM         -> new Position(row + amount, col);
            case BOTTOM_LEFT    -> new Position(row + amount, col - amount);
            case LEFT           -> new Position(row, col - amount);
            case TOP_LEFT       -> new Position(row - amount, col - amount);
            case NONE           -> this;
            case OTHER          -> throw new UnsupportedOperationException("Translation in other direction is not supported");
        };
    }
}