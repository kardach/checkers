package checkers.variants;

import checkers.model.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;

@NullMarked
public class GameBuilder {

    private GameBuilder() {}

    public static NameStep newBuilder() {
        return new Steps();
    }

    public interface NameStep {
        BoardStep name(String name);
    }

    public interface BoardStep {
        PieceRulesStep board(int size, Color nearRightSquareColor, int piecesPerSide, Placement placement);
        PieceRulesStep board(int size, Color nearRightSquareColor, List<PiecePlacement> initialPiecePositions);
    }

    public interface PieceRulesStep {
        FirstMoveStep pieceRules(boolean backCaptureAllowed, boolean flyingKinsAllowed);
    }

    public interface FirstMoveStep {
        GameRulesStep firstMove(Color color);
    }

    public interface GameRulesStep {
        BuildStep gameRules(CaptureConstrain captureConstrain, PromotionConstrain promotionConstrain);
    }

    public interface BuildStep {
        Game build();
    }

    private static class Steps implements NameStep, BoardStep, PieceRulesStep, FirstMoveStep, GameRulesStep, BuildStep {
        @Nullable
        private String name;
        private int size;
        @Nullable
        private Color nearRightSquareColor;
        @Nullable
        private List<PiecePlacement> initialPiecePositions;
        private int piecesPerSide;
        @Nullable
        private Placement placement;
        @Nullable
        private Set<PiecePlacement> piecePlacements;
        @Nullable
        private Color firstMove;
        private boolean backCaptureAllowed;
        private boolean flyingKingsAllowed;
        @Nullable
        private CaptureConstrain captureConstrain;
        @Nullable
        private PromotionConstrain promotionConstrain;

        @Override
        public BoardStep name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public PieceRulesStep board(int size, Color nearRightSquareColor, int piecesPerSide, Placement placement) {
            if (size <= 0) {
                throw new IllegalArgumentException("Board size must be greater than zero");
            }
            if (piecesPerSide <= 0) {
                throw new IllegalArgumentException("Pieces per side must be greater than zero");
            }
            this.size = size;
            this.nearRightSquareColor = nearRightSquareColor;
            this.piecesPerSide = piecesPerSide;
            this.placement = placement;
            return this;
        }

        @Override
        public PieceRulesStep board(int size, Color nearRightSquareColor, List<PiecePlacement> initialPiecePositions) {
            if (initialPiecePositions.isEmpty()) {
                throw new IllegalArgumentException("Initial piece positions must be non-empty");
            }
            this.size = size;
            this.nearRightSquareColor = nearRightSquareColor;
            this.initialPiecePositions = List.copyOf(initialPiecePositions);
            return this;
        }

        @Override
        public FirstMoveStep pieceRules(boolean backCaptureAllowed, boolean flyingKinsAllowed) {
            this.backCaptureAllowed = backCaptureAllowed;
            this.flyingKingsAllowed = flyingKinsAllowed;
            return this;
        }

        @Override
        public GameRulesStep firstMove(Color firstMove) {
            this.firstMove = firstMove;
            return this;
        }

        @Override
        public BuildStep gameRules(CaptureConstrain captureConstrain, PromotionConstrain promotionConstrain) {
            this.captureConstrain = captureConstrain;
            this.promotionConstrain = promotionConstrain;
            return this;
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public Game build() {
            Board board = initialPiecePositions == null
                    ? new Board(size, nearRightSquareColor, piecesPerSide, placement, backCaptureAllowed, flyingKingsAllowed)
                    : new Board(size, nearRightSquareColor, initialPiecePositions, backCaptureAllowed, flyingKingsAllowed);
            return new Game(name, board, firstMove, captureConstrain, promotionConstrain);
        }
    }

}
