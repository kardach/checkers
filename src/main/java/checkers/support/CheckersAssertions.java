package checkers.support;

import checkers.model.Board;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class CheckersAssertions {

    public static SquareAssert assertThat(Board.Square actual) {
        return new SquareAssert(actual);
    }

    public static PieceAssert assertThat(Board.Piece actual) {
        return new PieceAssert(actual);
    }
}
