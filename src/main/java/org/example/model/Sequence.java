package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Sequence {
    private final List<Move> moves;
    private Position start;

    public Sequence() {
        moves = new ArrayList<>();
    }

    public void add(int row, int col) {
//        System.out.println(row + " " + col);
        if(start == null) {
            start = new Position(row, col);
        } else {
            Position lastPosition;
            if(moves.isEmpty()) {
                lastPosition = start;
            } else {
                lastPosition = moves.getLast().to();
            }
            moves.add(new Move(lastPosition, new Position(row, col)));
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
