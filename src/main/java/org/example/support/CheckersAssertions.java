package org.example.support;

import org.example.model.Piece;
import org.example.model.Square;

public class CheckersAssertions {

    public static SquareAssert assertThat(Square actual) {
        return new SquareAssert(actual);
    }

    public static PieceAssert assertThat(Piece actual) {
        return new PieceAssert(actual);
    }
}
