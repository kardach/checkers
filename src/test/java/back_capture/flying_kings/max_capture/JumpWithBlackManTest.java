package back_capture.flying_kings.max_capture;

import org.example.model.*;
import org.example.model.Color;
import org.example.variants.GameBuilder;
import org.example.ui.GameplayPanel;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void jumpToTopLeft() {
        Position from = new Position(1, 1);
        Position to = new Position(0, 0);
        Move move = new Move(from ,to);

        gameplayPanel.getSquareButton(move.from().row(), move.from().col()).doClick();
        gameplayPanel.getSquareButton(move.to().row(), move.to().col()).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertTrue(game.getBoard().at(to).hasPiece());
        assertEquals(Color.BLACK, game.getBoard().at(to).getPiece().getColor());
    }

    @Test
    void jumpToTopRight() {
        Position from = new Position(1, 1);
        Position to = new Position(0, 2);
        Move move = new Move(from, to);

        gameplayPanel.getSquareButton(move.from().row(), move.from().col()).doClick();
        gameplayPanel.getSquareButton(move.to().row(), move.to().col()).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertTrue(game.getBoard().at(to).hasPiece());
        assertEquals(Color.BLACK, game.getBoard().at(to).getPiece().getColor());
    }

    @Test
    void jumpToBottomLeft() {
        Position from = new Position(1, 1);
        Position to = new Position(2, 0);
        Move move = new Move(from ,to);

        gameplayPanel.getSquareButton(move.from().row(), move.from().col()).doClick();
        gameplayPanel.getSquareButton(move.to().row(), move.to().col()).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertTrue(game.getBoard().at(from).hasPiece());
        assertEquals(Color.BLACK, game.getBoard().at(from).getPiece().getColor());
        assertFalse(game.getBoard().at(to).hasPiece());
    }

    @Test
    void jumpToBottonRight() {
        Position from = new Position(1, 1);
        Position to = new Position(2, 2);
        Move move = new Move(from ,to);

        gameplayPanel.getSquareButton(move.from().row(), move.from().col()).doClick();
        gameplayPanel.getSquareButton(move.to().row(), move.to().col()).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertTrue(game.getBoard().at(from).hasPiece());
        assertEquals(Color.BLACK, game.getBoard().at(from).getPiece().getColor());
        assertFalse(game.getBoard().at(to).hasPiece());
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
