package unit;

import org.example.model.Color;
import org.example.model.Piece;
import org.example.model.Square;
import org.example.support.ReplaceCamelCase;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(ReplaceCamelCase.class)
class SquareTest {

    private final Square square = new Square(Color.BLACK);
    private final Piece piece = new Piece(Color.WHITE);

    @Nested
    class SquareWithoutPieceTest {

        @Test
        void removePiece() {
            assertNull(square.removePiece());
        }

        @Test
        void hasAPiece() {
            assertFalse(square.hasPiece());
        }
    }

    @Nested
    class SquareWithPieceTest {

        @BeforeEach
        void init() {
            square.placePiece(piece);
        }

        @Test
        void removePiece() {
            assertEquals(piece, square.removePiece());
        }

        @Test
        void hasAPiece() {
            assertTrue(square.hasPiece());
        }
    }
}
