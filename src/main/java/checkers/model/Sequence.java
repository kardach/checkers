package checkers.model;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public class Sequence {

    private final List<Move> moves;
    @Nullable
    private Position start;

    public Sequence() {
        moves = new ArrayList<>();
    }

    public void add(int row, int col) {
        add(new Position(row, col));
    }

    public void add(Position position) {
        if (start == null) {
            start = position;
        } else {
            Position lastPosition;
            if (moves.isEmpty()) {
                lastPosition = start;
                if (!lastPosition.equals(position)) {
                    moves.add(new Move(lastPosition, position));
                }
            } else {
                lastPosition = moves.getLast().to();
                if (moves.getLast().from().equals(position)) {
                    moves.removeLast();
                } else {
                    moves.add(new Move(lastPosition, position));
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

    public boolean contains(Move other) {
        return moves.stream().anyMatch(move -> move.equals(other));
    }

    public @Nullable Position getStart() {
        return start;
    }

    public List<Move> getMoves() {
        return moves;
    }
}
