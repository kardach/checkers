package unit;

import org.example.model.Color;
import org.example.model.Piece;
import org.example.model.Type;
import org.example.support.ReplaceCamelCase;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
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
