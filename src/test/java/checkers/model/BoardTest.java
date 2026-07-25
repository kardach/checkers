package checkers.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import checkers.support.CustomNameGenerator;

import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.junit.jupiter.params.provider.MethodSource;

import static checkers.support.CheckersAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@NullMarked
@DisplayNameGeneration(CustomNameGenerator.class)
class BoardTest {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    class BoardWithPiecePlacementsFromListTest {

        final Position positionWithPiece = new Position(0, 0);
        static final List<Position> POSITIONS_WITHOUT_PIECE = List.of(
                new Position(0, 1),
                new Position(1, 0),
                new Position(1, 1)
        );
        final List<PiecePlacement> piecePlacements = List.of(
                new PiecePlacement(Color.BLACK, Type.MAN, positionWithPiece)
        );

        Board board;

        @BeforeEach
        void init() {
            board = new Board(2, Color.BLACK, piecePlacements, false, false);
        }

        @DisplayName("board, Should: have piece at Position[row=0, col=0]")
        @Test
        void boardShouldHavePieceAt() {
            assertThat(board.at(positionWithPiece))
                    .hasPiece()
                    .extractPiece()
                    .hasColor(Color.BLACK)
                    .hasType(Type.MAN);
        }

        @ParameterizedTest(name = "{0}")
        @FieldSource("POSITIONS_WITHOUT_PIECE")
        void boardShouldNotHavePieceAt(Position position) {
            assertFalse(board.at(position).hasPiece());
        }

        @Test
        void isInBoundsShouldReturnCorrectly() {
            assertTrue(board.isInBounds(positionWithPiece));
            assertFalse(board.isInBounds(new Position(-1, -1)));
            assertFalse(board.isInBounds(new Position(-1, 0)));
            assertFalse(board.isInBounds(new Position(-1, 2)));
            assertFalse(board.isInBounds(new Position(2, -1)));
            assertFalse(board.isInBounds(new Position(2, 0)));
            assertFalse(board.isInBounds(new Position(2, 2)));
        }

        @Test
        void atShouldThrowWhenPositionsIsOutOfBounds() {
            assertThrows(IllegalArgumentException.class, () -> board.at(new Position(2, 2)));
        }

        @Test
        void getCurrentPiecePositionsShouldReturnCorrectMap() {
            Board.Piece piece = board.at(positionWithPiece).getPiece();
            assertEquals(Map.ofEntries(Map.entry(piece, positionWithPiece)), board.getCurrentPiecePositions());
            piece = board.at(positionWithPiece).removePiece();
            assertEquals(Map.of(), board.getCurrentPiecePositions());
            board.at(POSITIONS_WITHOUT_PIECE.getFirst()).placePiece(piece);
            assertEquals(Map.ofEntries(Map.entry(piece, POSITIONS_WITHOUT_PIECE.getFirst())), board.getCurrentPiecePositions());
        }

        @Test
        void resetShouldRemoveAllPiecesAndPlaceNewOnInitialPositions() {
            Board.Piece piece = board.at(positionWithPiece).removePiece();
            board.at(POSITIONS_WITHOUT_PIECE.getFirst()).placePiece(piece);
            board.reset();
            assertTrue(board.at(positionWithPiece).hasPiece());
            POSITIONS_WITHOUT_PIECE.forEach(position -> assertFalse(board.at(position).hasPiece()));
        }
    }

    @Nested
    class BoarWithPiecePlacementsFromParametersTest {

        Board board;

        @BeforeEach
        void init() {
            board = new Board(4, Color.BLACK, 4, Placement.ON_BLACK, false, false);
        }

        static Stream<Position> positionWithoutPieceProvider() {
            return Stream.of(
                    new Position(0, 1),
                    new Position(0, 3),
                    new Position(1, 0),
                    new Position(1, 2),
                    new Position(2, 1),
                    new Position(2, 3),
                    new Position(3, 0),
                    new Position(3, 2)
            );
        }

        static Stream<Arguments> positionWithPieceProvider() {
            return Stream.of(
                    Arguments.arguments(new Position(0, 0), Color.WHITE),
                    Arguments.arguments(new Position(0, 2), Color.WHITE),
                    Arguments.arguments(new Position(1, 1), Color.WHITE),
                    Arguments.arguments(new Position(1, 3), Color.WHITE),
                    Arguments.arguments(new Position(2, 0), Color.BLACK),
                    Arguments.arguments(new Position(2, 2), Color.BLACK),
                    Arguments.arguments(new Position(3, 1), Color.BLACK),
                    Arguments.arguments(new Position(3, 3), Color.BLACK)
            );
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("positionWithoutPieceProvider")
        void boardShouldNotHavePieceAt(Position position) {
            assertFalse(board.at(position).hasPiece());
        }

        @ParameterizedTest(name = "has {1} piece at {0}")
        @MethodSource("positionWithPieceProvider")
        void boardShouldHavePieceAt(Position position, Color color) {
            assertThat(board.at(position))
                    .hasPiece()
                    .extractPiece()
                    .hasColor(color)
                    .hasType(Type.MAN);
        }
    }
}
