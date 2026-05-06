package back_capture.flying_kings.max_capture;

import org.example.model.*;
import org.example.model.Color;
import org.example.ui.GameplayPanel;
import org.example.variants.GameBuilder;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CaptureWithWhiteManTest {
    private static JFrame jFrame;
    private static GameplayPanel gameplayPanel;
    private static ArrayList<CustomPiecePlacement> customPiecePlacements;
    private static GameBuilder gameBuilder;
    private Game game;

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
                new CustomPiecePlacement(Color.WHITE, Type.MAN, 2, 2),
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 1, 1),
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 1, 3),
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 3, 1),
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 3, 3)));

        gameBuilder = getGameBuilder();

        jFrame.setVisible(true);
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
        gameBuilder.setBoard(new Board(5, true, customPiecePlacements));
        gameBuilder.setFirstMove(Color.WHITE);
        gameBuilder.setMenCaptureBackwards(true);
        gameBuilder.setFlyingKings(true);
        gameBuilder.setCapture(Capture.MAX);
        gameBuilder.setCrowning(Crowning.ON_FINISH);
        return gameBuilder;
    }

    @BeforeEach
    void init() {
        game = gameBuilder.build();
        gameplayPanel.setGame(game);
    }

    @Test
    void CaptureToTopLeft() {
        Position from = new Position(2, 2);
        Position captured = new Position(1, 1);
        Position to = new Position(0, 0);
        Move move = new Move(from, to);

        gameplayPanel.getSquareButton(move.from().row(), move.from().col()).doClick();
        gameplayPanel.getSquareButton(move.to().row(), move.to().col()).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(captured).hasPiece());
        assertTrue(game.getBoard().at(to).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(to).getPiece().getColor());
    }

    @Test
    void CaptureToTopRight() {
        Position from = new Position(2, 2);
        Position captured = new Position(1, 3);
        Position to = new Position(0, 4);
        Move move = new Move(from, to);

        gameplayPanel.getSquareButton(move.from().row(), move.from().col()).doClick();
        gameplayPanel.getSquareButton(move.to().row(), move.to().col()).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(captured).hasPiece());
        assertTrue(game.getBoard().at(to).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(to).getPiece().getColor());
    }

    @Test
    void CaptureToBottomLeft() {
        Position from = new Position(2, 2);
        Position captured = new Position(3, 1);
        Position to = new Position(4, 0);
        Move move = new Move(from, to);

        gameplayPanel.getSquareButton(move.from().row(), move.from().col()).doClick();
        gameplayPanel.getSquareButton(move.to().row(), move.to().col()).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(captured).hasPiece());
        assertTrue(game.getBoard().at(to).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(to).getPiece().getColor());
    }

    @Test
    void CaptureToBottonRight() {
        Position from = new Position(2, 2);
        Position captured = new Position(3, 3);
        Position to = new Position(4, 4);
        Move move = new Move(from, to);

        gameplayPanel.getSquareButton(move.from().row(), move.from().col()).doClick();
        gameplayPanel.getSquareButton(move.to().row(), move.to().col()).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(captured).hasPiece());
        assertTrue(game.getBoard().at(to).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(to).getPiece().getColor());
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
