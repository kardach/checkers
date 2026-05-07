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

public class MultiCaptureWithWhiteManTest {
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
                new CustomPiecePlacement(Color.WHITE, Type.MAN, 4, 4)
        ));
        for (int row = 1; row < 8; row += 2) {
            for (int col = 1; col < 8; col += 2) {
                customPiecePlacements.add(new CustomPiecePlacement(Color.BLACK, Type.MAN, row, col));
            }
        }

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
        gameBuilder.setBoard(new Board(9, true, customPiecePlacements));
        gameBuilder.setFirstMove(Color.WHITE);
        gameBuilder.setMenCaptureBackwards(true);
        gameBuilder.setFlyingKings(true);
        gameBuilder.setCapture(Capture.MAX);
        gameBuilder.setCrowning(Crowning.ON_FINISH);
        return gameBuilder;
    }

    @Test
    void CaptureToTopLeftThanTopLeft() {
        Position from = new Position(4, 4);
        Position firstCaptured = new Position(3, 3);
        Position firstTo =  new Position(2, 2);
        Position secondCaptured = new Position(1, 1);
        Position secondTo = new Position(0, 0);

        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(firstTo).doClick();
        gameplayPanel.getSquareButton(secondTo).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(firstCaptured).hasPiece());
        assertFalse(game.getBoard().at(firstTo).hasPiece());
        assertFalse(game.getBoard().at(secondCaptured).hasPiece());
        assertTrue(game.getBoard().at(secondTo).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(secondTo).getPiece().getColor());
    }

    @Test
    void CaptureToTopLeftThanTopRight() {
        Position from = new Position(4, 4);
        Position firstCaptured = new Position(3, 3);
        Position firstTo =  new Position(2, 2);
        Position secondCaptured = new Position(3, 1);
        Position secondTo = new Position(4, 0);

        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(firstTo).doClick();
        gameplayPanel.getSquareButton(secondTo).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(firstCaptured).hasPiece());
        assertFalse(game.getBoard().at(firstTo).hasPiece());
        assertFalse(game.getBoard().at(secondCaptured).hasPiece());
        assertTrue(game.getBoard().at(secondTo).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(secondTo).getPiece().getColor());
    }

    @Test
    void CaptureToTopLeftThanBottomLeft() {
        Position from = new Position(4, 4);
        Position firstCaptured = new Position(3, 3);
        Position firstTo =  new Position(2, 2);
        Position secondCaptured = new Position(1, 3);
        Position secondTo = new Position(0, 4);

        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(firstTo).doClick();
        gameplayPanel.getSquareButton(secondTo).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(firstCaptured).hasPiece());
        assertFalse(game.getBoard().at(firstTo).hasPiece());
        assertFalse(game.getBoard().at(secondCaptured).hasPiece());
        assertTrue(game.getBoard().at(secondTo).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(secondTo).getPiece().getColor());
    }

    @Test
    void CaptureToTopRightThanTopLeft() {
        Position from = new Position(4, 4);
        Position firstCaptured = new Position(5, 3);
        Position firstTo =  new Position(6, 2);
        Position secondCaptured = new Position(5, 1);
        Position secondTo = new Position(4, 0);

        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(firstTo).doClick();
        gameplayPanel.getSquareButton(secondTo).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(firstCaptured).hasPiece());
        assertFalse(game.getBoard().at(firstTo).hasPiece());
        assertFalse(game.getBoard().at(secondCaptured).hasPiece());
        assertTrue(game.getBoard().at(secondTo).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(secondTo).getPiece().getColor());
    }

    @Test
    void CaptureToTopRightThanTopRight() {
        Position from = new Position(4, 4);
        Position firstCaptured = new Position(5, 3);
        Position firstTo =  new Position(6, 2);
        Position secondCaptured = new Position(7, 1);
        Position secondTo = new Position(8, 0);

        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(firstTo).doClick();
        gameplayPanel.getSquareButton(secondTo).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(firstCaptured).hasPiece());
        assertFalse(game.getBoard().at(firstTo).hasPiece());
        assertFalse(game.getBoard().at(secondCaptured).hasPiece());
        assertTrue(game.getBoard().at(secondTo).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(secondTo).getPiece().getColor());
    }

    @Test
    void CaptureToTopRightThanBottomRight() {
        Position from = new Position(4, 4);
        Position firstCaptured = new Position(5, 3);
        Position firstTo =  new Position(6, 2);
        Position secondCaptured = new Position(7, 3);
        Position secondTo = new Position(8, 4);

        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(firstTo).doClick();
        gameplayPanel.getSquareButton(secondTo).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(firstCaptured).hasPiece());
        assertFalse(game.getBoard().at(firstTo).hasPiece());
        assertFalse(game.getBoard().at(secondCaptured).hasPiece());
        assertTrue(game.getBoard().at(secondTo).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(secondTo).getPiece().getColor());
    }

    @Test
    void CaptureToBottomLeftThanTopLeft() {
        Position from = new Position(4, 4);
        Position firstCaptured = new Position(3, 5);
        Position firstTo =  new Position(2, 6);
        Position secondCaptured = new Position(1, 5);
        Position secondTo = new Position(0, 4);

        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(firstTo).doClick();
        gameplayPanel.getSquareButton(secondTo).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(firstCaptured).hasPiece());
        assertFalse(game.getBoard().at(firstTo).hasPiece());
        assertFalse(game.getBoard().at(secondCaptured).hasPiece());
        assertTrue(game.getBoard().at(secondTo).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(secondTo).getPiece().getColor());
    }

    @Test
    void CaptureToBottomLeftThanBottomLeft() {
        Position from = new Position(4, 4);
        Position firstCaptured = new Position(3, 5);
        Position firstTo =  new Position(2, 6);
        Position secondCaptured = new Position(1, 7);
        Position secondTo = new Position(0, 8);

        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(firstTo).doClick();
        gameplayPanel.getSquareButton(secondTo).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(firstCaptured).hasPiece());
        assertFalse(game.getBoard().at(firstTo).hasPiece());
        assertFalse(game.getBoard().at(secondCaptured).hasPiece());
        assertTrue(game.getBoard().at(secondTo).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(secondTo).getPiece().getColor());
    }

    @Test
    void CaptureToBottomLeftThanBottomRight() {
        Position from = new Position(4, 4);
        Position firstCaptured = new Position(3, 5);
        Position firstTo =  new Position(2, 6);
        Position secondCaptured = new Position(3, 7);
        Position secondTo = new Position(4, 8);

        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(firstTo).doClick();
        gameplayPanel.getSquareButton(secondTo).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(firstCaptured).hasPiece());
        assertFalse(game.getBoard().at(firstTo).hasPiece());
        assertFalse(game.getBoard().at(secondCaptured).hasPiece());
        assertTrue(game.getBoard().at(secondTo).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(secondTo).getPiece().getColor());
    }

    @Test
    void CaptureToBottomRightThanTopRight() {
        Position from = new Position(4, 4);
        Position firstCaptured = new Position(5, 5);
        Position firstTo =  new Position(6, 6);
        Position secondCaptured = new Position(7, 5);
        Position secondTo = new Position(8, 4);

        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(firstTo).doClick();
        gameplayPanel.getSquareButton(secondTo).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(firstCaptured).hasPiece());
        assertFalse(game.getBoard().at(firstTo).hasPiece());
        assertFalse(game.getBoard().at(secondCaptured).hasPiece());
        assertTrue(game.getBoard().at(secondTo).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(secondTo).getPiece().getColor());
    }

    @Test
    void CaptureToBottomRightThanBottomLeft() {
        Position from = new Position(4, 4);
        Position firstCaptured = new Position(5, 5);
        Position firstTo =  new Position(6, 6);
        Position secondCaptured = new Position(5, 7);
        Position secondTo = new Position(4, 8);

        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(firstTo).doClick();
        gameplayPanel.getSquareButton(secondTo).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(firstCaptured).hasPiece());
        assertFalse(game.getBoard().at(firstTo).hasPiece());
        assertFalse(game.getBoard().at(secondCaptured).hasPiece());
        assertTrue(game.getBoard().at(secondTo).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(secondTo).getPiece().getColor());
    }

    @Test
    void CaptureToBottomRightThanBottomRight() {
        Position from = new Position(4, 4);
        Position firstCaptured = new Position(5, 5);
        Position firstTo =  new Position(6, 6);
        Position secondCaptured = new Position(7, 7);
        Position secondTo = new Position(8, 8);

        gameplayPanel.getSquareButton(from).doClick();
        gameplayPanel.getSquareButton(firstTo).doClick();
        gameplayPanel.getSquareButton(secondTo).doClick();
        gameplayPanel.getConfirmButton().doClick();

        assertFalse(game.getBoard().at(from).hasPiece());
        assertFalse(game.getBoard().at(firstCaptured).hasPiece());
        assertFalse(game.getBoard().at(firstTo).hasPiece());
        assertFalse(game.getBoard().at(secondCaptured).hasPiece());
        assertTrue(game.getBoard().at(secondTo).hasPiece());
        assertEquals(Color.WHITE, game.getBoard().at(secondTo).getPiece().getColor());
    }

    @Test
    void CaptureTopClockwiseCircular() {
        Position[] jumpList = {new Position(4, 4), new Position(2, 2),
                new Position(0, 4), new Position(2, 6)};
        Position[] capturedList = {new Position(3, 3), new Position(1 , 3),
                new Position(1, 5), new Position(3, 5)};

        for (Position jump : jumpList) {
            gameplayPanel.getSquareButton(jump).doClick();
        }
        gameplayPanel.getSquareButton(jumpList[0]).doClick();
        gameplayPanel.getConfirmButton().doClick();

        for (Position captured : capturedList) {
            assertFalse(game.getBoard().at(captured).hasPiece());
        }
        for (int i = 1; i < jumpList.length; i++) {
            assertFalse(game.getBoard().at(jumpList[i]).hasPiece());
        }
        assertEquals(Color.WHITE, game.getBoard().at(jumpList[0]).getPiece().getColor());
    }

    @Test
    void CaptureAlreadyCapturedPiece() {
        Position[] jumpList = {new Position(4, 4), new Position(2, 2),
                new Position(0, 4), new Position(2, 6)};
        Position[] capturedList = {new Position(3, 3), new Position(1 , 3),
                new Position(1, 5), new Position(3, 5)};

        for (int i = 0; i < 5; i++) {
            IO.println(i);
            gameplayPanel.getSquareButton(jumpList[i % 4]).doClick();
        }
        gameplayPanel.getConfirmButton().doClick();

        for (Position captured : capturedList) {
            assertFalse(game.getBoard().at(captured).hasPiece());
        }
        for (int i = 1; i < jumpList.length; i++) {
            assertFalse(game.getBoard().at(jumpList[i]).hasPiece());
        }
        assertEquals(Color.WHITE, game.getBoard().at(jumpList[0]).getPiece().getColor());
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
