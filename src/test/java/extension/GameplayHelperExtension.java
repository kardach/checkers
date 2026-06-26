package extension;

import org.example.model.CustomPiecePlacement;
import org.example.model.Game;
import org.example.model.Position;
import org.example.model.Square;
import org.example.ui.GameplayPanel;
import org.example.variants.GameBuilder;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.extension.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameplayHelperExtension
        implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    private JFrame jFrame;
    private GameplayPanel gameplayPanel;
    private ArrayList<CustomPiecePlacement> customPiecePlacements;
    private GameBuilder gameBuilder;
    private Game game;

    public GameplayHelperExtension(GameBuilder gameBuilder) {
        this.gameBuilder = gameBuilder;
    }

    public GameplayHelperExtension() {}

    public void clickSquareButton(Position position) {
        gameplayPanel.getSquareButton(position).doClick();
    }

    public void clickConfirmButton() {
        gameplayPanel.getConfirmButton().doClick();
    }

    public Square getSquareAt(Position position) {
        return game.getBoard().at(position);
    }

    public void setGameBuilder(GameBuilder gameBuilder) {
        this.gameBuilder = gameBuilder;
    }

    public Game getGame() {
        return game;
    }

    public GameplayPanel getGameplayPanel() {
        return gameplayPanel;
    }

    @Override
    public void beforeAll(@NonNull ExtensionContext context) {
        jFrame = new JFrame("Checkers");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(640, 480);

        CardLayout cardLayout = new CardLayout();
        jFrame.getContentPane().setLayout(cardLayout);

        gameplayPanel = new GameplayPanel();
        jFrame.getContentPane().add("GAMEPLAY", gameplayPanel);
        jFrame.setVisible(true);
    }

    @Override
    public void beforeEach(@NonNull ExtensionContext context) {
        game = gameBuilder.build();
        gameplayPanel.setGame(game);
    }

    @Override
    public void afterEach(@NonNull ExtensionContext context) {
        gameplayPanel.removeGame();
    }

    @Override
    public void afterAll(@NonNull ExtensionContext context) {
        jFrame.dispose();
    }
}
