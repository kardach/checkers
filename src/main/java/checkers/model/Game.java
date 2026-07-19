package checkers.model;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

@NullMarked
public class Game {
    private final String name;
    private final Board board;
    private final MovePlanner movePlanner;
    private final CaptureConstrain captureConstrain;
    private final PromotionConstrain promotionConstrain;
    private Color turn;
    @Nullable
    private Winner winner;

    public Game(String name, Board board, Color firstMove, CaptureConstrain captureConstrain, PromotionConstrain promotionConstrain) {
        this.name = name;
        this.board = board;
        this.captureConstrain = captureConstrain;
        this.promotionConstrain = promotionConstrain;
        this.movePlanner = new MovePlanner();
        this.turn = firstMove;
    }

    public String getName() {
        return name;
    }

    public boolean isFinished() {
        return winner != null;
    }

    public Winner getWinner() {
        if (winner == null) {
            throw new IllegalStateException("Game is not finished");
        }
        return winner;
    }

    public Color getTurn() {
        return turn;
    }

    private void changeTurn() {
        turn = turn.opposite();
    }

    public List<Position> getAvailableStartPositions() {
        if (movePlanner.isStarted()) {
            throw new IllegalStateException("Game is already started");
        }
        return board.getCurrentPiecePositions().entrySet()
                .stream()
                .filter(entry -> entry.getKey().getColor() == turn)
                .filter(entry -> !entry.getKey().getValidPositions().isEmpty())
                .map(Map.Entry::getValue)
                .toList();
    }

    public List<Position> getValidPositions(Position position) {
        return List.copyOf(movePlanner.getValidPositions());
    }

    public Queue<PossibleMove> getPlannedMoves() {
        return movePlanner.getMoves();
    }

    public void addPositionToMovePlan(Position position) {
        movePlanner.add(position);
    }

    public void executePlannedMoves() {
        if (movePlanner.isEmpty()) {
            throw new IllegalStateException("No moves were planned");
        }
        if (captureConstrain == CaptureConstrain.ALL && !movePlanner.getValidPositions().isEmpty()) {
            throw new IllegalStateException("Capture is still possible");
        } else if (captureConstrain == CaptureConstrain.MAX && isCaptureMaximal()) {
            throw new IllegalStateException("Capture is not maximal");
        }
        for (PossibleMove possibleMove : movePlanner.getMoves()) {
            Board.Piece piece = board.at(possibleMove.from()).removePiece();
            board.at(possibleMove.to()).placePiece(piece);
            for (SideEffect sideEffect : possibleMove.getSideEffects()) {
                sideEffect.perform();
            }
        }
        movePlanner.clear();
    }

    private boolean isCaptureMaximal() {
        List<Queue<PossibleMove>> container = new ArrayList<>();
        for(Position position : getAvailableStartPositions()) {
            MovePlanner movePlanner = new MovePlanner();
            movePlanner.add(position);
            search(movePlanner, container);
        }
        return container.contains(movePlanner.getMoves());
    }

    private void search(MovePlanner movePlanner, List<Queue<PossibleMove>> best) {
        Position currentPositon = movePlanner.getMoves().getLast().to();
        for (Position position : movePlanner.getValidPositions()) {
            movePlanner.add(position);
            search(movePlanner, best);
            movePlanner.add(currentPositon);
        }
        Queue<PossibleMove> current = movePlanner.getMoves();
        if (best.isEmpty() || best.getFirst().size() == current.size()) {
            best.add(current);
        } else if (best.getFirst().size() < current.size()) {
            best.clear();
            best.add(current);
        }
    }

    public void reset() {
        board.reset();
        winner = null;
    }

    private class MovePlanner {

        @Nullable
        private Position start;
        private final Deque<PossibleMove> moves;

        private MovePlanner() {
            moves = new ArrayDeque<>();
        }

        private boolean isStarted() {
            return start != null;
        }

        private boolean isEmpty() {
            return moves.isEmpty();
        }

        private void clear() {
            start = null;
            moves.clear();
        }

        private Deque<PossibleMove> getMoves() {
            return moves;
        }

        private void add(Position position) {
            if (start == null) {
                start = position;
            } else {
                Position lastPosition;
                if (moves.isEmpty()) {
                    lastPosition = start;
                    if (!lastPosition.equals(position)) {
                        moves.add(determinePossibleMove(lastPosition, position));
                    }
                } else {
                    lastPosition = moves.peekLast().to();
                    if (moves.peek().from().equals(position)) {
                        moves.removeLast();
                    } else {
                        moves.add(determinePossibleMove(lastPosition, position));
                    }
                }
            }
        }

        private Set<Position> getValidPositions() {
            if (!isStarted()) {
                return board.getCurrentPiecePositions().values().stream().collect(Collectors.toUnmodifiableSet());
            } else {
                if (!isEmpty()) {
                    return board.at(start).getPiece().getValidPositions();
                } else {
                    int promotionRow = turn == Color.BLACK ? 0 : board.getSize();
                    if (promotionConstrain == PromotionConstrain.TERMINATE && moves.getLast().to().col() == promotionRow) {
                        return Set.of();
                    }
                    if (moves.getFirst() instanceof Jump) {
                        return Set.of();
                    }
                    Set<Position> temp = board.at(moves.getLast().to()).getPiece().getValidPositions();
                    temp.add(moves.getLast().from());
                    return temp;
                }
            }
        }

        private PossibleMove determinePossibleMove(Position from, Position to) {
            int promotionRow = turn == Color.BLACK ? 0 : board.getSize();
            Direction direction = from.getDirection(to);
            Position temp;
            Position piecePosition = null;
            do {
                temp = from.translate(direction, 1);
                if (board.at(temp).hasPiece()) {
                    piecePosition = temp;
                }
            } while(!temp.equals(to));
            if (to.row() == promotionRow) {
                if (piecePosition == null) {
                    return new Jump(from, to, new Promotion(to));
                } else {
                    if (promotionConstrain == PromotionConstrain.ON_FINISH) {
                        return new Capture(from, to, new PieceCapture(piecePosition));
                    } else {
                        return new Capture(from, to, new PieceCapture(piecePosition), new Promotion(to));
                    }
                }
            } else {
                if (piecePosition == null) {
                    return new Jump(from, to);
                } else {
                    return new Capture(from, to, new PieceCapture(piecePosition));
                }
            }
        }
    }

    public enum Winner {
        BLACK,
        WHITE,
        DRAW
    }

    private abstract static class SideEffect {

        private final Position position;

        SideEffect(Position position) {
            this.position = position;
        }

        Position getPosition() {
            return position;
        }

        abstract void perform();

        abstract void undo();
    }

    private class Promotion extends SideEffect {

        Promotion(Position position) {
            super(position);
        }

        @Override
        void perform() {
            board.at(getPosition()).getPiece().promote();
        }

        @Override
        void undo() {
            board.at(getPosition()).getPiece().demote();
        }
    }

    private class PieceCapture extends SideEffect {

        private Board.@Nullable Piece piece;

        PieceCapture(Position position) {
            super(position);
        }

        @Override
        void perform() {
            piece = board.at(getPosition()).removePiece();
        }

        @Override
        void undo() {
            if (piece == null) {
                throw new IllegalStateException("Piece was null");
            }
            board.at(getPosition()).placePiece(piece);
        }
    }

    public static sealed class PossibleMove permits Jump, Capture {

        private final Position from;
        private final Position to;
        final List<SideEffect> sideEffects;

        private PossibleMove(Position from, Position to) {
            sideEffects = new ArrayList<>();
            this.from = from;
            this.to = to;
        }

        private PossibleMove(Position from, Position to, SideEffect sideEffect) {
            sideEffects = new ArrayList<>();
            this.from = from;
            this.to = to;
            sideEffects.add(sideEffect);
        }

        Position from() {
            return from;
        }
        Position to() {
            return to;
        }

        private boolean hasSideEffects() {
            return sideEffects.isEmpty();
        }

        private List<SideEffect> getSideEffects() {
            return sideEffects;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, sideEffects);
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            } else if (!(object instanceof PossibleMove other)) {
                return false;
            } else {
                return Objects.equals(from, other.from) && Objects.equals(to, other.to) && Objects.equals(getSideEffects(), other.getSideEffects());
            }
        }
    }

    private static non-sealed class Jump extends PossibleMove {

        private Jump(Position from, Position to) {
            super(from, to);
        }

        private Jump(Position from, Position to, Promotion promotion) {
            super(from, to, promotion);
        }
    }

    private static non-sealed class Capture extends PossibleMove {

        private Capture(Position from, Position to, PieceCapture capture) {
            super(from, to, capture);
        }

        private Capture(Position from, Position to, PieceCapture capture, Promotion promotion) {
            super(from, to, promotion);
            sideEffects.add(capture);
        }
    }
}