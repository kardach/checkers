package back_capture.flying_kings.max_capture;

import extension.GameplaySetupExtension;
import org.example.model.*;
import org.example.model.Color;
import org.example.ui.GameplayPanel;
import org.example.variants.GameBuilder;
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

public class CaptureWithWhiteManTest {

    @RegisterExtension
    static GameplaySetupExtension checkersResource = new GameplaySetupExtension(getGameBuilder());

    private static @NonNull ArrayList<CustomPiecePlacement> getCustomPiecePlacements() {
        return new ArrayList<>(List.of(
                new CustomPiecePlacement(Color.WHITE, Type.MAN, 2, 2),
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 1, 1),
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 1, 3),
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 3, 1),
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 3, 3)));
    }

    private static @NonNull GameBuilder getGameBuilder() {
        var gameBuilder = new GameBuilder();
        gameBuilder.setName("""
                <html>
                <body>
                size = 5<br>
                lightSquareOnNearRight = true<br>
                firstMove = WHITE<br>
                backwardCapture = true<br>
                flyingKings = true<br>
                capture = MAX<br>
                crowning = FINISH<br>
                </body>
                </html>
                """);
        gameBuilder.setBoard(new Board(5, true, getCustomPiecePlacements()));
        gameBuilder.setFirstMove(Color.WHITE);
        gameBuilder.setMenCaptureBackwards(true);
        gameBuilder.setFlyingKings(true);
        gameBuilder.setCapture(Capture.MAX);
        gameBuilder.setCrowning(Crowning.ON_FINISH);
        return gameBuilder;
    }

    private static List<Arguments> providePositionsForCapture() {
        Position from = new Position(2, 2);
        return List.of(
                argumentSet("TopLeft", from, new Position(1, 1), new Position(0, 0)),
                argumentSet("TopRight", from, new Position(1, 3), new Position(0, 4)),
                argumentSet("BottomLeft", from, new Position(3, 1), new Position(4, 0)),
                argumentSet("BottomRight", from, new Position(3, 3), new Position(4, 4))
        );
    }

    @ParameterizedTest(name = "{argumentSetName} from={0} captured={1} to={2}")
    @MethodSource("providePositionsForCapture")
    void capture(Position from, Position captured, Position to) {
        checkersResource.getGameplayPanel().getSquareButton(from).doClick();
        checkersResource.getGameplayPanel().getSquareButton(to).doClick();
        checkersResource.getGameplayPanel().getConfirmButton().doClick();

        assertFalse(checkersResource.getGame().getBoard().at(from).hasPiece());
        assertFalse(checkersResource.getGame().getBoard().at(captured).hasPiece());
        assertTrue(checkersResource.getGame().getBoard().at(to).hasPiece());
        assertEquals(Color.WHITE, checkersResource.getGame().getBoard().at(to).getPiece().getColor());
    }
}
