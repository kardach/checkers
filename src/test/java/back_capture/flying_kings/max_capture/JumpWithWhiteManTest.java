package back_capture.flying_kings.max_capture;

import org.example.model.*;
import org.example.model.Color;
import org.example.options.GameBuilder;
import org.example.ui.GameplayPanel;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JumpWithWhiteManTest {
    JFrame jFrame;
    GameplayPanel gameplayPanel;
    ArrayList<CustomPiecePlacement> customPiecePlacements;
    Game game;
    Robot robot;

    @BeforeEach
    void init() throws AWTException {
        jFrame = new JFrame("Checkers");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(640, 480);

        CardLayout cardLayout = new CardLayout();
        jFrame.getContentPane().setLayout(cardLayout);

        customPiecePlacements = new ArrayList<>(List.of(
                new CustomPiecePlacement(Color.WHITE, Type.MAN, 1, 1)
        ));

        var gameBuilder = getGameBuilder();
        game = gameBuilder.build();

        gameplayPanel = new GameplayPanel();
        gameplayPanel.setGame(game);

        jFrame.getContentPane().add("GAMEPLAY", gameplayPanel);
        jFrame.setVisible(true);

        robot = new Robot();
    }

    private @NonNull GameBuilder getGameBuilder() {
        var gameBuilder = new GameBuilder();
        gameBuilder.setName("""
                <html>
                <body>
                size = 3<br>
                lightSquareOnNearRight = true<br>
                firstMove = WHITE<br>
                backwardCapture = true<br>
                flyingKings = true<br>
                capture = MAX<br>
                crowning = FINISH<br>
                </body>
                </html>
                """);
        gameBuilder.setBoard(new Board(3, true, customPiecePlacements));
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
    void jumpToTopLeftWith() throws InterruptedException {
        Position from = new Position(1, 1);
        Position to = new Position(0, 0);
        ArrayList<Move> moves = new ArrayList<>(List.of(new Move(from, to)));
        for(Move move : moves) {
            clickSquareButton(move.from().row(), move.from().col());
            clickSquareButton(move.to().row(), move.to().col());
            clickConfirmButton();
        }

        Thread.sleep(5000);

        assertTrue(game.getBoard().at(from).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(from).getPiece().getColor());
        assertFalse(game.getBoard().at(to).hasPiece());
    }

    @Test
    void jumpToTopRightWith() throws InterruptedException {
        Position from = new Position(1, 1);
        Position to = new Position(0, 2);
        ArrayList<Move> moves = new ArrayList<>(List.of(new Move(from, to)));
        for(Move move : moves) {
            clickSquareButton(move.from().row(), move.from().col());
            clickSquareButton(move.to().row(), move.to().col());
            clickConfirmButton();
        }

        Thread.sleep(5000);

        assertTrue(game.getBoard().at(from).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(from).getPiece().getColor());
        assertFalse(game.getBoard().at(to).hasPiece());
    }

    @Test
    void jumpToBottomLeftWith() throws InterruptedException {
        Position from = new Position(1, 1);
        Position to = new Position(2, 0);
        ArrayList<Move> moves = new ArrayList<>(List.of(new Move(from, to)));
        for(Move move : moves) {
            clickSquareButton(move.from().row(), move.from().col());
            clickSquareButton(move.to().row(), move.to().col());
            clickConfirmButton();
        }

        Thread.sleep(5000);

        assertFalse(game.getBoard().at(from).hasPiece());
        assertTrue(game.getBoard().at(to).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(to).getPiece().getColor());
    }

    @Test
    void jumpToBottonRightWith() throws InterruptedException {
        Position from = new Position(1, 1);
        Position to = new Position(2, 2);
        ArrayList<Move> moves = new ArrayList<>(List.of(new Move(from, to)));
        for(Move move : moves) {
            clickSquareButton(move.from().row(), move.from().col());
            clickSquareButton(move.to().row(), move.to().col());
            clickConfirmButton();
        }

        Thread.sleep(5000);

        assertFalse(game.getBoard().at(from).hasPiece());
        assertTrue(game.getBoard().at(to).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(to).getPiece().getColor());
    }
}
