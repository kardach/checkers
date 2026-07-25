package checkers.model;

import checkers.support.CustomNameGenerator;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@NullMarked
@DisplayNameGeneration(CustomNameGenerator.class)
public class PieceCountTest {

    final Position positionWithPiece = new Position(0, 0);
    final List<Position> positionsWithoutPiece = List.of(
            new Position(0, 1),
            new Position(1, 0),
            new Position(1, 1)
    );
    final List<PiecePlacement> piecePlacements = List.of(
            new PiecePlacement(Color.BLACK, Type.MAN, positionWithPiece)
    );
    final Board board = new Board(2, Color.BLACK, piecePlacements, false, false);
    final Board.PieceCount pieceCount = board.getPieceCount();

    @Test
    void isConstructedWithCorrectCounts() {
        assertEquals(1, pieceCount.numberOfPieces(Color.BLACK, Type.MAN));
        assertEquals(0, pieceCount.numberOfPieces(Color.WHITE, Type.MAN));
        assertEquals(0, pieceCount.numberOfPieces(Color.BLACK, Type.KING));
        assertEquals(0, pieceCount.numberOfPieces(Color.WHITE, Type.KING));
        assertEquals(1, pieceCount.numberOfPieces(Color.BLACK));
        assertEquals(0, pieceCount.numberOfPieces(Color.WHITE));
        assertEquals(1, pieceCount.numberOfPieces(Type.MAN));
        assertEquals(0, pieceCount.numberOfPieces(Type.KING));
    }

    @Test
    void promoteAndDemoteShouldIncrementAndDecrementCorrespondingCounts() {
        Board.Piece piece = board.at(positionWithPiece).getPiece();
        piece.promote();
        assertEquals(0, pieceCount.numberOfPieces(Color.BLACK, Type.MAN));
        assertEquals(1, pieceCount.numberOfPieces(Color.BLACK, Type.KING));
        piece.demote();
        assertEquals(1, pieceCount.numberOfPieces(Color.BLACK, Type.MAN));
        assertEquals(0, pieceCount.numberOfPieces(Color.BLACK, Type.KING));
    }

    @Test
    void removePieceAndPlacePieceShouldIncrementAndDecrementCorrespondingCounts() {
        Board.Piece piece = board.at(positionWithPiece).removePiece();
        assertEquals(0, pieceCount.numberOfPieces(Color.BLACK, Type.MAN));
        board.at(positionsWithoutPiece.getFirst()).placePiece(piece);
        assertEquals(1, pieceCount.numberOfPieces(Color.BLACK, Type.MAN));
    }
}
