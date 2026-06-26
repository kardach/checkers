package org.example.model;

public record Position(int row, int col) {

        public Direction getDirection(Position other) {
            int diffRow = row - other.row;
            int diffCol = col - other.col;
            Direction direction = null;

            if (diffRow < 0) {
                if (diffCol < 0) {
                    direction = Direction.BOTTOM_RIGHT;
                } else if (diffCol > 0) {
                    direction = Direction.BOTTOM_LEFT;
                } else {
                    direction = Direction.BOTTOM;
                }
            } else if (diffRow > 0) {
                if (diffCol < 0) {
                    direction = Direction.TOP_RIGHT;
                } else if (diffCol > 0) {
                    direction = Direction.TOP_LEFT;
                } else {
                    direction = Direction.TOP;
                }
            } else {
                if (diffCol < 0) {
                    direction = Direction.RIGHT;
                } else if (diffCol > 0) {
                    direction = Direction.LEFT;
                }
            }
            return direction;
        }

        public Position translate(Direction direction, int amount) {
            return switch (direction) {
                case TOP -> new Position(row - amount, col);
                case TOP_RIGHT -> new Position(row - amount, col + amount);
                case RIGHT -> new Position(row, col + amount);
                case BOTTOM_RIGHT -> new Position(row + amount, col + amount);
                case BOTTOM -> new Position(row + amount, col);
                case BOTTOM_LEFT -> new Position(row + amount, col - amount);
                case LEFT -> new Position(row, col - amount);
                case TOP_LEFT -> new Position(row - amount, col - amount);
            };
        }
}