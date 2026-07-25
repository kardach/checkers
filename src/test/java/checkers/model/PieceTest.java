package checkers.model;

import checkers.support.CustomNameGenerator;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.argumentSet;

@NullMarked
@DisplayNameGeneration(CustomNameGenerator.class)
class PieceTest {

    final static Position POSITION = new Position(4, 4);

    @Test
    void promoteAndDemoteShouldChangeType() {
        Board board = new Board(9, Color.BLACK, singlePiece(Color.BLACK, Type.MAN), false, false);
        Board.Piece piece = board.at(POSITION).getPiece();
        piece.promote();
        assertEquals(Type.KING, piece.getType());
        piece.demote();
        assertEquals(Type.MAN, piece.getType());
    }

    @Test
    void promoteShouldThrowWhenPieceIsKing() {
        Board board = new Board(9, Color.BLACK, singlePiece(Color.BLACK, Type.MAN), false, false);
        Board.Piece piece = board.at(POSITION).getPiece();
        piece.promote();
        assertThrows(IllegalStateException.class, piece::promote);
    }

    @Test
    void demoteShouldThrowWhenPieceIsMan() {
        Board board = new Board(9, Color.BLACK, singlePiece(Color.BLACK, Type.MAN), false, false);
        Board.Piece piece = board.at(POSITION).getPiece();
        assertThrows(IllegalStateException.class, piece::demote);
    }

    final static List<Direction> TOP_LEFT_RIGHT = List.of(Direction.TOP_LEFT, Direction.TOP_RIGHT);

    final static List<Direction> BOTTOM_LEFT_RIGHT = List.of(Direction.BOTTOM_LEFT, Direction.BOTTOM_RIGHT);

    static List<PiecePlacement> singlePiece(Color color, Type type) {
        return List.of(new PiecePlacement(color, type, POSITION));
    }

    static List<PiecePlacement> piecesInDirections(Color color, List<Direction> directions, int distance) {
        return directions
                .stream()
                .map(direction -> new PiecePlacement(color, Type.MAN, POSITION.translate(direction, distance)))
                .toList();
    }

    static List<Position> positionsInDirections(List<Direction> directions, int distance) {
        return directions
                .stream()
                .map(direction -> POSITION.translate(direction, distance))
                .toList();
    }

    static <T> List<T> union(List<List<T>> lists) {
        return lists
                .stream()
                .flatMap(List::stream)
                .toList();
    }

    @Nested
    class NotBackwardCapturingManTest {

        @ParameterizedTest(name = "{argumentSetName}")
        @MethodSource("argumentsProviderForBlackMan")
        void getValidPositionsShouldReturnCorrectPositionsWhenManIsBlack(List<PiecePlacement> piecePlacements, List<Position> validPositions) {
            getValidPositionsShouldReturnCorrectPositions(piecePlacements, validPositions);
        }

        @ParameterizedTest(name = "{argumentSetName}")
        @MethodSource("argumentsProviderForWhiteMan")
        void getValidPositionsShouldReturnCorrectPositionsWhenManIsWhite(List<PiecePlacement> piecePlacements, List<Position> validPositions) {
            getValidPositionsShouldReturnCorrectPositions(piecePlacements, validPositions);
        }
        
        void getValidPositionsShouldReturnCorrectPositions(List<PiecePlacement> piecePlacements, List<Position> validPositions) {
            Board board = new Board(9, Color.BLACK, piecePlacements, false, false);
            Board.Piece piece = board.at(POSITION).getPiece();
            assertEquals(validPositions, piece.getValidPositions());
        }

        static Stream<Arguments.ArgumentSet> argumentsProviderForBlackMan() {
            return argumentsProvider(Color.BLACK);
        }

        static Stream<Arguments.ArgumentSet> argumentsProviderForWhiteMan() {
            return argumentsProvider(Color.WHITE);
        }

        static Stream<Arguments.ArgumentSet> argumentsProvider(Color color) {
            List<Direction> directions;
            List<Direction> oppositeDirections;
            String directionString;
            String oppositeDirectionString;
            if (color == Color.BLACK) {
                directions = TOP_LEFT_RIGHT;
                oppositeDirections = BOTTOM_LEFT_RIGHT;
                directionString = "above";
                oppositeDirectionString = "below";
            } else {
                directions = BOTTOM_LEFT_RIGHT;
                oppositeDirections = TOP_LEFT_RIGHT;
                directionString = "below";
                oppositeDirectionString = "above";
            }
            return Stream.of(
                    argumentSet(
                            "no pieces",
                            singlePiece(color, Type.MAN),
                            positionsInDirections(directions, 1)
                    ),
                    argumentSet(
                            "enemies %s, 1 square away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.MAN),
                                    piecesInDirections(color.opposite(), directions, 1)
                            )),
                            positionsInDirections(directions, 2)
                    ),
                    argumentSet(
                            "enemies %s, 2 squares away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.MAN),
                                    piecesInDirections(color.opposite(), directions, 2)
                            )),
                            positionsInDirections(directions, 1)
                    ),
                    argumentSet(
                            "enemies %s, 1 and 2 squares away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.MAN),
                                    piecesInDirections(color.opposite(), directions, 1),
                                    piecesInDirections(color.opposite(), directions, 2)
                            )),
                            List.of()
                    ),
                    argumentSet(
                            "enemies %s, 1 square away".formatted(oppositeDirectionString),
                            union(List.of(
                                    singlePiece(color, Type.MAN),
                                    piecesInDirections(color.opposite(), oppositeDirections, 1)
                            )),
                            positionsInDirections(directions, 1)
                    ),
                    argumentSet(
                            "allies %s, 1 square away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.MAN),
                                    piecesInDirections(color, directions, 1)
                            )),
                            List.of()
                    ),
                    argumentSet(
                            "allies %s, 2 squares away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.MAN),
                                    piecesInDirections(color, directions, 2)
                            )),
                            positionsInDirections(directions, 1)
                    )
            );
        }
    }

    @Nested
    class BackwardCapturingManTest {

        @ParameterizedTest(name = "{argumentSetName}")
        @MethodSource("argumentsProviderForBlackMan")
        void getValidPositionsShouldReturnCorrectPositionsWhenManIsBlack(List<PiecePlacement> piecePlacements, List<Position> validPositions) {
            getValidPositionsShouldReturnCorrectPositions(piecePlacements, validPositions);
        }

        @ParameterizedTest(name = "{argumentSetName}")
        @MethodSource("argumentsProviderForWhiteMan")
        void getValidPositionShouldReturnCorrectPositionsWhenManIsWhite(List<PiecePlacement> piecePlacements, List<Position> validPositions) {
            getValidPositionsShouldReturnCorrectPositions(piecePlacements, validPositions);
        }

        void getValidPositionsShouldReturnCorrectPositions(List<PiecePlacement> piecePlacements, List<Position> validPositions) {
            Board board = new Board(9, Color.BLACK, piecePlacements, true, false);
            Board.Piece piece = board.at(POSITION).getPiece();
            assertEquals(validPositions, piece.getValidPositions());
        }

        static Stream<Arguments.ArgumentSet> argumentsProviderForBlackMan() {
            return argumentsProvider(Color.BLACK);
        }

        static Stream<Arguments.ArgumentSet> argumentsProviderForWhiteMan() {
            return argumentsProvider(Color.WHITE);
        }

        static Stream<Arguments.ArgumentSet> argumentsProvider(Color color) {
            List<Direction> directions = Direction.getDiagonal();
            String directionString = "diagonal";
            return Stream.of(
                    argumentSet(
                            "no pieces",
                            singlePiece(color, Type.MAN),
                            positionsInDirections(directions, 1)
                    ),
                    argumentSet(
                            "enemies %s, 1 square away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.MAN),
                                    piecesInDirections(color.opposite(), directions, 1)
                            )),
                            positionsInDirections(directions, 2)
                    ),
                    argumentSet(
                            "enemies %s, 2 squares away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.MAN),
                                    piecesInDirections(color.opposite(), directions, 2)
                            )),
                            positionsInDirections(directions, 1)
                    ),
                    argumentSet(
                            "enemies %s, 1 and 2 squares away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.MAN),
                                    piecesInDirections(color.opposite(), directions, 1),
                                    piecesInDirections(color.opposite(), directions, 2)
                            )),
                            List.of()
                    ),
                    argumentSet(
                            "allies %s, 1 square away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.MAN),
                                    piecesInDirections(color, directions, 1)
                            )),
                            List.of()
                    ),
                    argumentSet(
                            "allies %s, 2 squares away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.MAN),
                                    piecesInDirections(color, directions, 2)
                            )),
                            positionsInDirections(directions, 1)
                    )
            );
        }
    }

    @Nested
    class NotFlyingKingTest {

        @ParameterizedTest(name = "{argumentSetName}")
        @MethodSource("argumentsProviderForBlackKing")
        void getValidPositionShouldReturnCorrectPositionWhenKingIsBlack(List<PiecePlacement> piecePlacements, List<Position> validPositions) {
            getValidPositionsShouldReturnCorrectPositions(piecePlacements, validPositions);
        }

        @ParameterizedTest(name = "{argumentSetName}")
        @MethodSource("argumentsProviderForWhiteKing")
        void getValidPositionShouldReturnCorrectPositionWhenKingIsWhite(List<PiecePlacement> piecePlacements, List<Position> validPositions) {
            getValidPositionsShouldReturnCorrectPositions(piecePlacements, validPositions);
        }

        void getValidPositionsShouldReturnCorrectPositions(List<PiecePlacement> piecePlacements, List<Position> validPositions) {
            Board board = new Board(9, Color.BLACK, piecePlacements, true, false);
            Board.Piece piece = board.at(POSITION).getPiece();
            assertEquals(validPositions, piece.getValidPositions());
        }

        static Stream<Arguments.ArgumentSet> argumentsProviderForBlackKing() {
            return argumentsProvider(Color.BLACK);
        }

        static Stream<Arguments.ArgumentSet> argumentsProviderForWhiteKing() {
            return argumentsProvider(Color.WHITE);
        }

        static Stream<Arguments.ArgumentSet> argumentsProvider(Color color) {
            List<Direction> directions = Direction.getDiagonal();
            String directionString = "diagonal";
            return Stream.of(
                    argumentSet(
                            "no pieces",
                            singlePiece(color, Type.KING),
                            positionsInDirections(directions, 1)
                    ),
                    argumentSet(
                            "enemies %s, 1 square away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.KING),
                                    piecesInDirections(color.opposite(), directions, 1)
                            )),
                            positionsInDirections(directions, 2)
                    ),
                    argumentSet(
                            "enemies %s, 2 squares away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.KING),
                                    piecesInDirections(color.opposite(), directions, 2)
                            )),
                            positionsInDirections(directions, 1)
                    ),
                    argumentSet(
                            "enemies %s, 1 and 2 squares away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.KING),
                                    piecesInDirections(color.opposite(), directions, 1),
                                    piecesInDirections(color.opposite(), directions, 2)
                            )),
                            List.of()
                    ),
                    argumentSet(
                            "allies %s, 1 square away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.KING),
                                    piecesInDirections(color, directions, 1)
                            )),
                            List.of()
                    ),
                    argumentSet(
                            "allies %s, 2 squares away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.KING),
                                    piecesInDirections(color, directions, 2)
                            )),
                            positionsInDirections(directions, 1)
                    )
            );
        }
    }

    @Nested
    class FlyingKingTest {

        @ParameterizedTest(name = "{argumentSetName}")
        @MethodSource("argumentsProviderForBlackKing")
        void getValidPositionShouldReturnCorrectPositionWhenKingIsWhite(List<PiecePlacement> piecePlacements, List<Position> validPositions) {
            getValidPositionsShouldReturnCorrectPositions(piecePlacements, validPositions);
        }

        @ParameterizedTest(name = "{argumentSetName}")
        @MethodSource("argumentsProviderForWhiteKing")
        void getValidPositionShouldReturnCorrectPositionWhenKingIsBlack(List<PiecePlacement> piecePlacements, List<Position> validPositions) {
            getValidPositionsShouldReturnCorrectPositions(piecePlacements, validPositions);
        }

        void getValidPositionsShouldReturnCorrectPositions(List<PiecePlacement> piecePlacements, List<Position> validPositions) {
            Board board = new Board(9, Color.BLACK, piecePlacements, true, true);
            Board.Piece piece = board.at(POSITION).getPiece();
            assertEquals(Set.copyOf(validPositions), Set.copyOf(piece.getValidPositions()));
        }

        static Stream<Arguments.ArgumentSet> argumentsProviderForBlackKing() {
            return argumentsProvider(Color.BLACK);
        }

        static Stream<Arguments.ArgumentSet> argumentsProviderForWhiteKing() {
            return argumentsProvider(Color.WHITE);
        }

        static Stream<Arguments.ArgumentSet> argumentsProvider(Color color) {
            List<Direction> directions = Direction.getDiagonal();
            String directionString = "diagonal";
            return Stream.of(
                    argumentSet(
                            "no pieces",
                            singlePiece(color, Type.KING),
                            union(List.of(
                                    positionsInDirections(directions, 1),
                                    positionsInDirections(directions, 2),
                                    positionsInDirections(directions, 3),
                                    positionsInDirections(directions, 4)
                            ))
                    ),
                    argumentSet(
                            "enemies %s, 1 square away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.KING),
                                    piecesInDirections(color.opposite(), directions, 1)
                            )),
                            union(List.of(
                                positionsInDirections(directions, 2),
                                positionsInDirections(directions, 3),
                                positionsInDirections(directions, 4)
                            ))
                    ),
                    argumentSet(
                            "enemies %s, 2 squares away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.KING),
                                    piecesInDirections(color.opposite(), directions, 2)
                            )),
                            union(List.of(
                                    positionsInDirections(directions, 1),
                                    positionsInDirections(directions, 3),
                                    positionsInDirections(directions, 4)
                            ))
                    ),
                    argumentSet(
                            "enemies %s, 1 and 2 squares away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.KING),
                                    piecesInDirections(color.opposite(), directions, 1),
                                    piecesInDirections(color.opposite(), directions, 2)
                            )),
                            List.of()
                    ),
                    argumentSet(
                            "enemies %s, 1 and 3 squares away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.KING),
                                    piecesInDirections(color.opposite(), directions, 1),
                                    piecesInDirections(color.opposite(), directions, 3)
                            )),
                            positionsInDirections(directions, 2)
                    ),
                    argumentSet(
                            "allies %s, 1 square away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.KING),
                                    piecesInDirections(color, directions, 1)
                            )),
                            List.of()
                    ),
                    argumentSet(
                            "allies %s, 2 squares away".formatted(directionString),
                            union(List.of(
                                    singlePiece(color, Type.KING),
                                    piecesInDirections(color, directions, 2)
                            )),
                            positionsInDirections(directions, 1)
                    ),
                    argumentSet(
                            "allies %s, 1 square away, enemies %s, 3 squares away".formatted(directionString, directionString),
                            union(List.of(
                                    singlePiece(color, Type.KING),
                                    piecesInDirections(color, directions, 1),
                                    piecesInDirections(color.opposite(), directions, 3)
                            )),
                            List.of()
                    ),
                    argumentSet(
                            "enemies %s, 1 square away, allies %s, 3 squares away".formatted(directionString, directionString),
                            union(List.of(
                                    singlePiece(color, Type.KING),
                                    piecesInDirections(color.opposite(), directions, 1),
                                    piecesInDirections(color, directions, 3)
                            )),
                            positionsInDirections(directions, 2)
                    )
            );
        }
    }
}
