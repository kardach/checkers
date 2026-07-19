package checkers.support;

import org.assertj.core.api.AbstractAssert;
import checkers.model.Color;
import checkers.model.Board;
import checkers.model.Type;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class PieceAssert extends AbstractAssert<PieceAssert, Board.Piece> {

    public PieceAssert(Board.Piece actual) {
        super(actual, PieceAssert.class);
    }

    public static PieceAssert assertThat(Board.Piece actual) {
        return new PieceAssert(actual);
    }

    public PieceAssert hasColor(Color color) {
        isNotNull();
        if (!actual.getColor().equals(color)) {
            failWithMessage("Expected color to be <%s> but was <%s>", color, actual.getColor());
        }
        return this;
    }

    public PieceAssert hasType(Type type) {
        isNotNull();
        if (!actual.getType().equals(type)) {
            failWithMessage("Expected type to be <%s> but was <%s>", type, actual.getType());
        }
        return this;
    }
}
