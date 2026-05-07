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

public class MulticaptureWithBlackManTest {
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
                new CustomPiecePlacement(Color.BLACK, Type.MAN, 4, 4)
        ));
        for (int row = 1; row < 8; row += 2) {
            for (int col = 1; col < 8; col += 2) {
                customPiecePlacements.add(new CustomPiecePlacement(Color.WHITE, Type.MAN, row, col));
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
                firstMove = BLACK<br>
                backwardCapture = true<br>
                flyingKings = true<br>
                capture = MAX<br>
                crowning = FINISH<br>
                </body>
                </html>
                """);
        gameBuilder.setBoard(new Board(9, true, customPiecePlacements));
        gameBuilder.setFirstMove(Color.BLACK);
        gameBuilder.setMenCaptureBackwards(true);
        gameBuilder.setFlyingKings(true);
        gameBuilder.setCapture(Capture.MAX);
        gameBuilder.setCrowning(Crowning.ON_FINISH);
        return gameBuilder;
    }

    private static List<Arguments> providePositionsForMulticapture() {
        List<Arguments> arguments = new ArrayList<>();
        String[] strings = {"TopLeft", "TopRight", "BottomLeft", "BottomRight"};
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int first = 0; first < 4; first++) {
            for (int second = 0; second < 4; second++) {
                if(directions[first][0] + directions[first][1] + directions[second][0]
                        + directions[second][1] != 0) {
                    List<Position> positionList = new ArrayList<>();
                    List<Position> capturedList = new ArrayList<>();
                    positionList.add(new Position(4, 4));
                    capturedList.add(new Position(4 + directions[first][0],
                            4 + directions[first][1]));
                    positionList.add(new Position(4 + 2 * directions[first][0],
                            4 + 2 * directions[first][1]));
                    capturedList.add(new Position(4 + 2 * directions[first][0] + directions[second][0],
                            4 + 2 * directions[first][1] + directions[second][1]));
                    positionList.add(new Position(4 + 2 * directions[first][0] + 2 * directions[second][0],
                            4 + 2 * directions[first][1] + 2 * directions[second][1]));
                    arguments.add(argumentSet(strings[first] + "Than" + strings[second], positionList,
                            capturedList));
                }
            }
        }
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("providePositionsForMulticapture")
    void multicapture(List<Position> positionList, List<Position> capturedList) {
        for (Position jump : positionList) {
            gameplayPanel.getSquareButton(jump).doClick();
        }
        gameplayPanel.getConfirmButton().doClick();

        for (Position captured : capturedList) {
            assertFalse(game.getBoard().at(captured).hasPiece());
        }
        for (int i = 0; i < positionList.size() - 1; i++) {
            assertFalse(game.getBoard().at(positionList.get(i)).hasPiece());
        }
        assertEquals(Color.BLACK, game.getBoard().at(positionList.getLast()).getPiece().getColor());
    }

    @Test
    void TopClockwiseCircularCapture() {
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
        assertEquals(Color.BLACK, game.getBoard().at(jumpList[0]).getPiece().getColor());
    }

    @Test
    void CaptureAlreadyCapturedPiece() {
        Position[] jumpList = {new Position(4, 4), new Position(2, 2),
                new Position(0, 4), new Position(2, 6)};
        Position[] capturedList = {new Position(3, 3), new Position(1 , 3),
                new Position(1, 5), new Position(3, 5)};

        for (int i = 0; i < 5; i++) {
            gameplayPanel.getSquareButton(jumpList[i % 4]).doClick();
        }
        gameplayPanel.getConfirmButton().doClick();

        for (Position captured : capturedList) {
            assertFalse(game.getBoard().at(captured).hasPiece());
        }
        for (int i = 1; i < jumpList.length; i++) {
            assertFalse(game.getBoard().at(jumpList[i]).hasPiece());
        }
        assertEquals(Color.BLACK, game.getBoard().at(jumpList[0]).getPiece().getColor());
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
