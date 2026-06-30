package org.example.support;

import org.assertj.core.api.AbstractAssert;
import org.example.model.Color;
import org.example.model.Piece;
import org.example.model.Type;

public class PieceAssert extends AbstractAssert<PieceAssert, Piece> {

    public  PieceAssert(Piece actual) {
        super(actual, PieceAssert.class);
    }

    public static  PieceAssert assertThat(Piece actual) {
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
