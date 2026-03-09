package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Move {
    private List<SubMove> subMoves;
    private Position start;

    public Move() {
        subMoves = new ArrayList<>();
    }

    public void begin(int x, int y) {
        start = new Position(x, y);
    }

    public void add(int x, int y) {
        Position lastPosition;
        if(subMoves.isEmpty()) {
            lastPosition = start;
        } else {
            lastPosition = subMoves.getLast().to;
        }
        subMoves.add(new SubMove(lastPosition, new Position(x, y)));
    }

    public void clear() {
        subMoves.clear();
    }

    record Position(int x, int y) {}

    record SubMove(Position from, Position to) {}
}
