package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sequence {
    private final List<Move> moves;
    private Position start;

    public Sequence() {
        moves = new ArrayList<>();
    }

    public void add(int row, int col) {
        if(start == null) {
            start = new Position(row, col);
        } else {
            Position lastPosition;
            if(moves.isEmpty()) {
                lastPosition = start;
                moves.add(new Move(lastPosition, new Position(row, col)));

            } else {
                lastPosition = moves.getLast().to();
                if(moves.getLast().from().equals(new Position(row, col))) {
                    moves.removeLast();
                } else {
                    moves.add(new Move(lastPosition, new Position(row, col)));
                }
            }
        }
    }

    public void clear() {
        start = null;
        moves.clear();
    }

    public boolean isStarted() {
        return start != null;
    }

    public boolean isEmpty() {
        return moves.isEmpty();
    }

    public Position getStart() {
        return start;
    }

    public List<Move> getMoves() {
        return moves;
    }
}
