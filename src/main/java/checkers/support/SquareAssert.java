package checkers.support;

import checkers.model.Board;
import org.assertj.core.api.AbstractAssert;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class SquareAssert extends AbstractAssert<SquareAssert, Board.Square> {

    public SquareAssert(Board.Square square) {
        super(square, SquareAssert.class);
    }

    public static SquareAssert assertThat(Board.Square square) {
        return new SquareAssert(square);
    }

    public SquareAssert hasPiece() {
        isNotNull();
        if (!actual().hasPiece()) {
            failWithMessage("The square was expected to have a piece");
        }
        return this;
    }

    public PieceAssert extractPiece() {
        Board.Piece piece = actual.getPiece();
        if (piece == null) {
            throw new AssertionError("Piece was expected to not be null");
        }
        return PieceAssert.assertThat(piece);
    }
}
