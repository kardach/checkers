package back_capture.flying_kings.max_capture;

import org.example.model.*;
import org.example.variants.GameBuilder;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.BeforeParameterizedClassInvocation;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import extension.GameplayHelperExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.argumentSet;

public class JumpWithKingTest {

    @RegisterExtension
    static GameplayHelperExtension gameplayHelperExtension = new GameplayHelperExtension();

    private static final Position from = new Position(3, 3);

    private static final List<Position> alliedMenPositions = new ArrayList<>(List.of(
            new Position(1, 1),
            new Position(1, 5),
            new Position(5, 1),
            new Position(5, 5)
    ));

    private static @NonNull GameBuilder getGameBuilder(Color color, ArrayList<CustomPiecePlacement> customPiecePlacements) {
        GameBuilder gameBuilder = new GameBuilder();
        gameBuilder.setName("""
                <html>
                <body>
                size = 7<br>
                lightSquareOnNearRight = true<br>
                firstMove = %s<br>
                backwardCapture = true<br>
                flyingKings = true<br>
                capture = MAX<br>
                crowning = FINISH<br>
                </body>
                </html>
                """.formatted(color));
        gameBuilder.setBoard(new Board(7, true, customPiecePlacements));
        gameBuilder.setFirstMove(color);
        gameBuilder.setMenCaptureBackwards(true);
        gameBuilder.setFlyingKings(true);
        gameBuilder.setCapture(Capture.MAX);
        gameBuilder.setCrowning(Crowning.ON_FINISH);
        return gameBuilder;
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @ParameterizedClass(name = "Jump with {0} KING")
    @EnumSource(Color.class)
    class JumpTest {

        @Parameter
        Color color;

        List<Arguments> argumentsForNearJump;
        List<Arguments> argumentsForFarJump;

        @BeforeParameterizedClassInvocation
        void init() {
            List<List<Position>> positionSequencesForNearJump = new PositionSequenceGenerator(from)
                    .nextDiagonalDirections(1)
                    .generate();
            List<List<Position>> positionSequencesForFarJump = new PositionSequenceGenerator(from)
                    .nextDiagonalDirections(3)
                    .generate();

            argumentsForNearJump = new ArrayList<>();
            argumentsForFarJump = new ArrayList<>();
            for(int i = 0; i < positionSequencesForNearJump.size(); i++) {
                argumentsForNearJump.add(Arguments.of(positionSequencesForNearJump.get(i)));
                argumentsForFarJump.add(Arguments.of(positionSequencesForFarJump.get(i)));
            }

            gameplayHelperExtension.setGameBuilder(getGameBuilder(color, new PiecePlacementGenerator()
                    .forColor(color)
                    .generate()
            ));
        }

        @ParameterizedTest
        @FieldSource({"argumentsForNearJump", "argumentsForFarJump"})
        void jump(List<Position> positions) {
            for(Position position : positions) {
                gameplayHelperExtension.clickSquareButton(position);
            }
            gameplayHelperExtension.clickConfirmButton();

            assertFalse(gameplayHelperExtension.getSquareAt(positions.getFirst()).hasPiece());
            assertTrue(gameplayHelperExtension.getSquareAt(positions.getLast()).hasPiece());
            assertEquals(color, gameplayHelperExtension.getSquareAt(positions.getLast()).getPiece().getColor());
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @ParameterizedClass(name = "Jump with {0} KING")
    @EnumSource(Color.class)
    class JumpOntoAlliedTest {

        @Parameter
        Color color;

        List<Arguments> arguments;

        @BeforeParameterizedClassInvocation
        void init() {
            List<List<Position>> positionSequences = new PositionSequenceGenerator(from)
                    .nextDiagonalDirections(2)
                    .generate();
            arguments = new ArrayList<>();
            for (List<Position> positionSequence : positionSequences) {
                arguments.add(Arguments.of(positionSequence));
            }

            gameplayHelperExtension.setGameBuilder(getGameBuilder(color, new PiecePlacementGenerator()
                    .setAllyMen(alliedMenPositions)
                    .forColor(color)
                    .generate())
            );
        }

        @ParameterizedTest
        @FieldSource("arguments")
        void jump(List<Position> positions) {
            for(Position position : positions) {
                gameplayHelperExtension.clickSquareButton(position);
            }
            gameplayHelperExtension.clickConfirmButton();

            assertTrue(gameplayHelperExtension.getSquareAt(positions.getFirst()).hasPiece());
            assertEquals(color, gameplayHelperExtension.getSquareAt(positions.getFirst()).getPiece().getColor());

            assertTrue(gameplayHelperExtension.getSquareAt(positions.getLast()).hasPiece());
            assertEquals(color, gameplayHelperExtension.getSquareAt(positions.getLast()).getPiece().getColor());
        }
    }

}
