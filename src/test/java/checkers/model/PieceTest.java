package checkers.model;

import checkers.support.ReplaceCamelCase;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(ReplaceCamelCase.class)
class PieceTest {

    private final Piece piece = new Piece(Color.BLACK);

    @Test
    void promote() {
        piece.promote();
        assertEquals(Type.KING, piece.getType());
    }
}
