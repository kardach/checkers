package back_capture.flying_kings.max_capture;

import org.example.generator.PiecePlacementGenerator;
import org.example.generator.PositionSequenceGenerator;
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

public class JumpWithKingTest {

    @RegisterExtension
    static GameplayHelperExtension helper = new GameplayHelperExtension();

    private static final Position FROM = new Position(3, 3);

    private static @NonNull GameBuilder getGameBuilder(Color color, ArrayList<PiecePlacement> piecePlacements) {
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
        gameBuilder.setBoard(new Board(7, true, piecePlacements));
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
            PositionSequenceGenerator generator = new PositionSequenceGenerator(FROM);

            List<List<Position>> positionSequencesForNearJump = generator
                    .nextDiagonalDirections(1)
                    .generate();

            generator.reset();

            List<List<Position>> positionSequencesForFarJump = generator
                    .nextDiagonalDirections(3)
                    .generate();

            argumentsForNearJump = new ArrayList<>();
            argumentsForFarJump = new ArrayList<>();
            for(int i = 0; i < positionSequencesForNearJump.size(); i++) {
                argumentsForNearJump.add(Arguments.of(positionSequencesForNearJump.get(i)));
                argumentsForFarJump.add(Arguments.of(positionSequencesForFarJump.get(i)));
            }

            helper.setGameBuilder(getGameBuilder(color, new PiecePlacementGenerator()
                    .forColor(color)
                    .generate()
            ));
        }

        @ParameterizedTest
        @FieldSource({"argumentsForNearJump", "argumentsForFarJump"})
        void jump(List<Position> positions) {
            for(Position position : positions) {
                helper.clickSquareButton(position);
            }
            helper.clickConfirmButton();

            assertFalse(helper.getSquareAt(positions.getFirst()).hasPiece());
            assertTrue(helper.getSquareAt(positions.getLast()).hasPiece());
            assertEquals(color, helper.getSquareAt(positions.getLast()).getPiece().getColor());
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @ParameterizedClass(name = "Jump with {0} KING onto {1}")
    @FieldSource("cartesianOfColors")
    class JumpOntoTest {

        Color[][] cartesianOfColors = {
                {Color.BLACK, Color.BLACK},
                {Color.BLACK, Color.WHITE},
                {Color.WHITE, Color.BLACK},
                {Color.WHITE, Color.WHITE}
        };

        @Parameter(0)
        Color color;

        @Parameter(1)
        Color otherColor;

        List<Arguments> arguments;

        @BeforeParameterizedClassInvocation
        void init() {
            List<List<Position>> positionSequences = new PositionSequenceGenerator(FROM)
                    .nextDiagonalDirections(2)
                    .generate();

            arguments = new ArrayList<>();
            for (List<Position> positionSequence : positionSequences) {
                arguments.add(Arguments.of(positionSequence));
            }

            helper.setGameBuilder(getGameBuilder(color, new PiecePlacementGenerator()
                    .forColor(color)
                    .setMen(Direction.getDiagonal()
                            .stream()
                            .map(direction -> FROM.translate(direction, 2))
                            .toList(), otherColor)
                    .generate())
            );
        }

        @ParameterizedTest
        @FieldSource("arguments")
        void jump(List<Position> positions) {
            for(Position position : positions) {
                helper.clickSquareButton(position);
            }
            helper.clickConfirmButton();

            assertTrue(helper.getSquareAt(positions.getFirst()).hasPiece());
            assertEquals(color, helper.getSquareAt(positions.getFirst()).getPiece().getColor());

            assertTrue(helper.getSquareAt(positions.getLast()).hasPiece());
            assertEquals(otherColor, helper.getSquareAt(positions.getLast()).getPiece().getColor());
        }
    }
}
