package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Move {
    private final List<SubMove> subMoves;
    private Position start;

    public Move() {
        subMoves = new ArrayList<>();
    }

    public void add(int row, int col) {
        if(start == null) {
            start = new Position(row, col);
        } else {
            Position lastPosition;
            if(subMoves.isEmpty()) {
                lastPosition = start;
            } else {
                lastPosition = subMoves.getLast().to;
            }
            subMoves.add(new SubMove(lastPosition, new Position(row, col)));
        }
    }

    public void clear() {
        start = null;
        subMoves.clear();
    }

    public boolean isEmpty() {
        return subMoves.isEmpty();
    }

    public List<SubMove> getSubMoves() {
        return subMoves;
    }

    public record Position(int row, int col) {}

    public record SubMove(Position from, Position to) {}
}
