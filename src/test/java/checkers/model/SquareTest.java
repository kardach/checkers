package checkers.model;

import checkers.support.CustomNameGenerator;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@NullMarked
@DisplayNameGeneration(CustomNameGenerator.class)
class SquareTest {

    final Position topLeft = new Position(0, 0);
    final Position topRight = new Position(0, 1);
    final Position bottomLeft = new Position(1, 0);
    final Position bottomRight = new Position(1, 1);
    final List<PiecePlacement> piecePlacements = List.of(
            new PiecePlacement(Color.BLACK, Type.MAN, topLeft),
            new PiecePlacement(Color.BLACK, Type.MAN, bottomRight)
    );
    final Board board = new Board(2, Color.BLACK, piecePlacements, false, false);

    @Test
    void hasPieceShouldReturnCorrectly() {
        assertTrue(board.at(topLeft).hasPiece());
        assertTrue(board.at(bottomRight).hasPiece());
        assertFalse(board.at(topRight).hasPiece());
        assertFalse(board.at(bottomLeft).hasPiece());
    }

    @Test
    void getPieceShouldThrowWhenPieceIsNotPresent() {
        assertThrows(NoSuchElementException.class, () -> board.at(topRight).getPiece());
    }

    @Test
    void getPieceShouldNotThrowWhenPieceIsPresent() {
        assertDoesNotThrow(() -> board.at(topLeft).getPiece());
    }

    @Test
    void getPieceShouldReturnPieceAndNotRemoveIt() {
        board.at(topLeft).getPiece();
        assertTrue(board.at(topLeft).hasPiece());
    }

    @Test
    void removePieceShouldThrowWhenPieceIsNotPresent() {
        assertThrows(NoSuchElementException.class, () -> board.at(topRight).removePiece());
    }

    @Test
    void removePieceShouldNotThrowWhenPieceIsPresent() {
        assertDoesNotThrow(() -> board.at(topLeft).removePiece());
    }

    @Test
    void removePieceShouldRemovePiece() {
        board.at(topLeft).removePiece();
        assertFalse(board.at(topLeft).hasPiece());
    }

    @Test
    void placePieceShouldThrowWhenPieceIsPresent() {
        Board.Piece piece = board.at(topLeft).removePiece();
        assertThrows(IllegalStateException.class, () -> board.at(bottomRight).placePiece(piece));
    }

    @Test
    void placePieceShouldNotThrowWhenPieceIsNotPresent() {
        Board.Piece piece = board.at(topLeft).removePiece();
        assertDoesNotThrow(() -> board.at(topRight).placePiece(piece));
    }

    @Test
    void placePieceShouldPlacePiece() {
        Board.Piece piece = board.at(topLeft).removePiece();
        board.at(topRight).placePiece(piece);
        assertTrue(board.at(topRight).hasPiece());
    }
}
