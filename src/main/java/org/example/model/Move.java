package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Move {
    private final List<SubMove> subMoves;
    private Position start;

    public Move() {
        subMoves = new ArrayList<>();
    }

    public void add(int x, int y) {
        if(start == null) {
            start = new Position(x, y);
        } else {
            Position lastPosition;
            if(subMoves.isEmpty()) {
                lastPosition = start;
            } else {
                lastPosition = subMoves.getLast().to;
            }
            subMoves.add(new SubMove(lastPosition, new Position(x, y)));
        }
    }

    public void clear() {
        start = null;
        subMoves.clear();
    }

    public boolean isEmpty() {
        return subMoves.isEmpty();
    }

    List<SubMove> getSubMoves() {
        return subMoves;
    }

    record Position(int x, int y) {}

    record SubMove(Position from, Position to) {}
}
