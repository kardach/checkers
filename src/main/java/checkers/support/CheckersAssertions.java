package checkers.support;

import checkers.model.Piece;
import checkers.model.Square;

public class CheckersAssertions {

    public static SquareAssert assertThat(Square actual) {
        return new SquareAssert(actual);
    }

    public static PieceAssert assertThat(Piece actual) {
        return new PieceAssert(actual);
    }
}
