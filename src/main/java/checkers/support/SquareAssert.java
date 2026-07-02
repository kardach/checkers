package checkers.support;

import org.assertj.core.api.AbstractAssert;
import checkers.model.Square;

public class SquareAssert extends AbstractAssert<SquareAssert, Square> {

    public SquareAssert(Square square) {
        super(square, SquareAssert.class);
    }

    public static SquareAssert assertThat(Square square) {
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
        return PieceAssert.assertThat(actual().getPiece());
    }
}
