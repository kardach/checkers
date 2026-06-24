package back_capture.flying_kings.max_capture;

import org.example.model.*;
import org.example.variants.GameBuilder;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import extension.GameplaySetupExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.argumentSet;

public class JumpWithBlackKingTest {

    @RegisterExtension
    static GameplaySetupExtension checkersResource = new GameplaySetupExtension(getGameBuilder());

    private static @NonNull ArrayList<CustomPiecePlacement> getCustomPiecePlacements() {
        return new ArrayList<>(List.of(
                new CustomPiecePlacement(org.example.model.Color.BLACK, Type.KING, 3, 3)
        ));
    }

    private static @NonNull GameBuilder getGameBuilder() {
        var gameBuilder = new GameBuilder();
        gameBuilder.setName("""
                <html>
                <body>
                size = 7<br>
                lightSquareOnNearRight = true<br>
                firstMove = BLACK<br>
                backwardCapture = true<br>
                flyingKings = true<br>
                capture = MAX<br>
                crowning = FINISH<br>
                </body>
                </html>
                """);
        gameBuilder.setBoard(new Board(7, true, getCustomPiecePlacements()));
        gameBuilder.setFirstMove(org.example.model.Color.BLACK);
        gameBuilder.setMenCaptureBackwards(true);
        gameBuilder.setFlyingKings(true);
        gameBuilder.setCapture(Capture.MAX);
        gameBuilder.setCrowning(Crowning.ON_FINISH);
        return gameBuilder;
    }

    private static java.util.List<Arguments> providePositionForJump() {
        Position from = new Position(3, 3);
        return List.of(
                argumentSet("NearTopLeft", from, new Position(2, 2)),
                argumentSet("NearTopRight", from, new Position(2, 4)),
                argumentSet("FarTopLeft", from, new Position(0, 0)),
                argumentSet("FarTopRight", from, new Position(0, 6)),
                argumentSet("NearBottomLeft", from, new Position(4, 2)),
                argumentSet("NearBottomRight", from, new Position(4, 4)),
                argumentSet("FarBottomLeft", from, new Position(6, 0)),
                argumentSet("FarBottomRight", from, new Position(6, 6))
        );
    }

    @ParameterizedTest(name = "{argumentSetName} from={0} to={1}")
    @MethodSource("providePositionForJump")
    void forwardJump(Position from, Position to) {
        checkersResource.getGameplayPanel().getSquareButton(from).doClick();
        checkersResource.getGameplayPanel().getSquareButton(to).doClick();
        checkersResource.getGameplayPanel().getConfirmButton().doClick();

        assertFalse(checkersResource.getGame().getBoard().at(from).hasPiece());
        assertTrue(checkersResource.getGame().getBoard().at(to).hasPiece());
        assertEquals(org.example.model.Color.BLACK, checkersResource.getGame().getBoard().at(to).getPiece().getColor());
    }
}
