package back_capture.flying_kings.max_capture;

import extension.GameplaySetupExtension;
import org.example.model.*;
import org.example.model.Color;
import org.example.variants.GameBuilder;
import org.example.ui.GameplayPanel;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.argumentSet;

public class JumpWithBlackManTest {

    @RegisterExtension
    static GameplaySetupExtension checkersResource = new GameplaySetupExtension(getGameBuilder());

    private static @NonNull ArrayList<CustomPiecePlacement> getCustomPiecePlacements() {
        return new ArrayList<>(List.of(
                new CustomPiecePlacement(org.example.model.Color.BLACK, Type.MAN, 1, 1)
        ));
    }

    private static @NonNull GameBuilder getGameBuilder() {
        var gameBuilder = new GameBuilder();
        gameBuilder.setName("""
                <html>
                <body>
                size = 3<br>
                lightSquareOnNearRight = true<br>
                firstMove = BLACK<br>
                backwardCapture = true<br>
                flyingKings = true<br>
                capture = MAX<br>
                crowning = FINISH<br>
                </body>
                </html>
                """);
        gameBuilder.setBoard(new Board(3, true, getCustomPiecePlacements()));
        gameBuilder.setFirstMove(Color.BLACK);
        gameBuilder.setMenCaptureBackwards(true);
        gameBuilder.setFlyingKings(true);
        gameBuilder.setCapture(Capture.MAX);
        gameBuilder.setCrowning(Crowning.ON_FINISH);
        return gameBuilder;
    }

    private static List<Arguments> providePositionForForwardJump() {
        Position from = new Position(1, 1);
        return List.of(
                argumentSet("TopLeft", from, new Position(0, 0)),
                argumentSet("TopRight", from, new Position(0, 2))
        );
    }

    private static List<Arguments> providePositionForBackwardJump() {
        Position from = new Position(1, 1);
        return List.of(
                argumentSet("BottomLeft", from, new Position(2, 0)),
                argumentSet("BottomRight", from, new Position(2, 2))
        );
    }

    @ParameterizedTest(name = "{argumentSetName} from={0} to={1}")
    @MethodSource("providePositionForForwardJump")
    void forwardJump(Position from, Position to) {
        checkersResource.getGameplayPanel().getSquareButton(from).doClick();
        checkersResource.getGameplayPanel().getSquareButton(to).doClick();
        checkersResource.getGameplayPanel().getConfirmButton().doClick();

        assertFalse(checkersResource.getGame().getBoard().at(from).hasPiece());
        assertTrue(checkersResource.getGame().getBoard().at(to).hasPiece());
        assertEquals(Color.BLACK, checkersResource.getGame().getBoard().at(to).getPiece().getColor());
    }

    @ParameterizedTest(name = "{argumentSetName} from={0} to={1}")
    @MethodSource("providePositionForBackwardJump")
    void backwardJump(Position from, Position to) {
        checkersResource.getGameplayPanel().getSquareButton(from).doClick();
        checkersResource.getGameplayPanel().getSquareButton(to).doClick();
        checkersResource.getGameplayPanel().getConfirmButton().doClick();

        assertTrue(checkersResource.getGame().getBoard().at(from).hasPiece());
        assertFalse(checkersResource.getGame().getBoard().at(to).hasPiece());
        assertEquals(Color.BLACK, checkersResource.getGame().getBoard().at(from).getPiece().getColor());
    }
}
