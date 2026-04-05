package back_capture.flying_kings.max_capture;

import org.example.model.*;
import org.example.model.Color;
import org.example.options.GameBuilder;
import org.example.ui.GameplayPanel;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CaptureWithWhiteManTest {
    private static JFrame jFrame;
    private GameplayPanel gameplayPanel;
    private static ArrayList<CustomPiecePlacement> customPiecePlacements;
    private Game game;
    private static Robot robot;

    @BeforeAll
    static void initAll() throws AWTException {
        jFrame = new JFrame("Checkers");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(640, 480);
        jFrame.setVisible(true);

        CardLayout cardLayout = new CardLayout();
        jFrame.getContentPane().setLayout(cardLayout);

        customPiecePlacements = new ArrayList<>(List.of(
                new CustomPiecePlacement(Color.WHITE, Type.MAN, 2, 2),
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 1, 1),
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 1, 3),
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 3, 1),
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 3, 3)
        ));

        robot = new Robot();
    }

    @BeforeEach
    void init() {
        var gameBuilder = getGameBuilder();
        game = gameBuilder.build();

        gameplayPanel = new GameplayPanel();
        gameplayPanel.setGame(game);

        jFrame.getContentPane().add("GAMEPLAY", gameplayPanel);
    }

    private @NonNull GameBuilder getGameBuilder() {
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

    void clickSquareButton(int row, int col) {
        Point point = gameplayPanel.getSquareButton(row, col).getLocationOnScreen();
        point.x += gameplayPanel.getSquareButton(row, col).getWidth() / 2;
        point.y += gameplayPanel.getSquareButton(row, col).getHeight() / 2;
        robot.mouseMove(point.x, point.y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    void clickConfirmButton() {
        Point point = gameplayPanel.getConfirmButton().getLocationOnScreen();
        point.x += gameplayPanel.getConfirmButton().getWidth() / 2;
        point.y += gameplayPanel.getConfirmButton().getHeight() / 2;
        robot.mouseMove(point.x, point.y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    @Test
    void CaptureToTopLeftWith() throws InterruptedException {
        Position from = new Position(2, 2);
        Position captured = new Position(1, 1);
        Position to = new Position(0, 0);
        ArrayList<Move> moves = new ArrayList<>(List.of(new Move(from, to)));
        for(Move move : moves) {
            clickSquareButton(move.from().row(), move.from().col());
            clickSquareButton(move.to().row(), move.to().col());
            clickConfirmButton();
        }

        Thread.sleep(5000);

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(captured).hasPiece());
        assertTrue(game.getBoard().at(to).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(to).getPiece().getColor());
    }

    @Test
    void CaptureToTopRightWith() throws InterruptedException {
        Position from = new Position(2, 2);
        Position captured = new Position(1, 3);
        Position to = new Position(0, 4);
        ArrayList<Move> moves = new ArrayList<>(List.of(new Move(from, to)));
        for(Move move : moves) {
            clickSquareButton(move.from().row(), move.from().col());
            clickSquareButton(move.to().row(), move.to().col());
            clickConfirmButton();
        }

        Thread.sleep(5000);

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(captured).hasPiece());
        assertTrue(game.getBoard().at(to).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(to).getPiece().getColor());
    }

    @Test
    void CaptureToBottomLeftWith() throws InterruptedException {
        Position from = new Position(2, 2);
        Position captured = new Position(3, 1);
        Position to = new Position(4, 0);
        ArrayList<Move> moves = new ArrayList<>(List.of(new Move(from, to)));
        for(Move move : moves) {
            clickSquareButton(move.from().row(), move.from().col());
            clickSquareButton(move.to().row(), move.to().col());
            clickConfirmButton();
        }

        Thread.sleep(5000);

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(captured).hasPiece());
        assertTrue(game.getBoard().at(to).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(to).getPiece().getColor());
    }

    @Test
    void CaptureToBottonRightWith() throws InterruptedException {
        Position from = new Position(2, 2);
        Position captured = new Position(3, 3);
        Position to = new Position(4, 4);
        ArrayList<Move> moves = new ArrayList<>(List.of(new Move(from, to)));
        for(Move move : moves) {
            clickSquareButton(move.from().row(), move.from().col());
            clickSquareButton(move.to().row(), move.to().col());
            clickConfirmButton();
        }

        Thread.sleep(5000);

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(captured).hasPiece());
        assertTrue(game.getBoard().at(to).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(to).getPiece().getColor());
    }

    @AfterEach
    void tearDown() {
        gameplayPanel.removeGame();
        jFrame.getContentPane().remove(gameplayPanel);
    }
}
