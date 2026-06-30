package unit;

import org.example.model.*;
import org.example.support.ReplaceCamelCase;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.example.support.CheckersAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DisplayNameGeneration(ReplaceCamelCase.class)
class BoardTest {

    private static Board board;

    @Nested
    class BoardWithPiecePlacementsFromListTest {

        @BeforeAll
        static void initAll() {
            board = new Board(4, true, new ArrayList<>(List.of(
                    new PiecePlacement(Color.BLACK, Type.MAN, new Position(0, 0))
            )));
        }

        static Stream<Position> positionWithoutPieceProvider() {
            List<Position> positions = new ArrayList<>();
            for(int i = 1; i < 4; i++) {
                positions.add(new Position(0, i));
                for(int j = 0; j < 4; j++) {
                    positions.add(new Position(i, j));
                }
            }
            return positions.stream();
        }

        @DisplayName("has a piece at Position[row=0, col=0]")
        @Test
        void hasAPieceAt() {
            assertThat(board.at(new Position(0, 0)))
                    .hasPiece()
                    .extractingPiece()
                    .hasColor(Color.BLACK)
                    .hasType(Type.MAN);
        }

        @ParameterizedTest(name = "does not have a piece at {0}")
        @MethodSource("positionWithoutPieceProvider")
        void doesNotHaveAPieceAt(Position position) {
            assertFalse(board.at(position).hasPiece());
        }

        @Test
        void resetWorksCorrectly() {
            Position removeFrom = new Position(0, 0);
            Position placeAt = new Position(0, 1);
            board.at(placeAt).placePiece(new Piece(Color.WHITE));
            board.at(removeFrom).removePiece();
            board.reset();
            assertFalse(board.at(placeAt).hasPiece());
            assertTrue(board.at(removeFrom).hasPiece());
        }
    }

    @Nested
    class BoarWithPiecePlacementsFromParametersTest {

        @BeforeAll
        static void initAll() {
            board = new Board(4, true, 4, Board.Placement.ON_WHITE);
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

        @ParameterizedTest(name = "does not have piece at {0}")
        @MethodSource("positionWithoutPieceProvider")
        void atShouldNotHavePieceTest(Position position) {
            assertFalse(board.at(position).hasPiece());
        }

        @ParameterizedTest(name = "has {1} piece at {0}")
        @MethodSource("positionWithPieceProvider")
        void atShouldHavePieceTest(Position position, Color color) {
            assertThat(board.at(position))
                    .hasPiece()
                    .extractingPiece()
                        .hasColor(color)
                        .hasType(Type.MAN);
        }
    }
}
