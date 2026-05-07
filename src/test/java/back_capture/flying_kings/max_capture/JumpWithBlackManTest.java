package back_capture.flying_kings.max_capture;

import org.example.model.*;
import org.example.model.Color;
import org.example.variants.GameBuilder;
import org.example.ui.GameplayPanel;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.*;
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
    private static JFrame jFrame;
    private static GameplayPanel gameplayPanel;
    private static ArrayList<CustomPiecePlacement> customPiecePlacements;
    private static GameBuilder gameBuilder;
    private static Game game;

    @BeforeAll
    static void initAll() {
        jFrame = new JFrame("Checkers");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(640, 480);

        CardLayout cardLayout = new CardLayout();
        jFrame.getContentPane().setLayout(cardLayout);

        gameplayPanel = new GameplayPanel();
        jFrame.getContentPane().add("GAMEPLAY", gameplayPanel);

        customPiecePlacements = new ArrayList<>(List.of(
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 1, 1)
        ));

        gameBuilder = getGameBuilder();

        jFrame.setVisible(true);
    }

    @BeforeEach
    void init() {
        game = gameBuilder.build();
        gameplayPanel.setGame(game);
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
        gameBuilder.setBoard(new Board(3, true, customPiecePlacements));
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
        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(to).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertTrue(game.getBoard().at(to).hasPiece());
        assertEquals(Color.BLACK, game.getBoard().at(to).getPiece().getColor());
    }

    @ParameterizedTest(name = "{argumentSetName} from={0} to={1}")
    @MethodSource("providePositionForBackwardJump")
    void backwardJump(Position from, Position to) {
        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(to).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertTrue(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(to).hasPiece());
        assertEquals(Color.BLACK, game.getBoard().at(from).getPiece().getColor());
    }

    @AfterEach
    void tearDown() {
        gameplayPanel.removeGame();
    }

    @AfterAll
    static void tearDownAll() {
        jFrame.dispose();
    }
}
