package checkers.model;

import checkers.model.Board;
import checkers.model.Color;
import checkers.model.Piece;
import checkers.model.Square;
import checkers.support.ReplaceCamelCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(ReplaceCamelCase.class)
class SquareTest {

    private final Square square = new Square(Color.BLACK, null);
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
