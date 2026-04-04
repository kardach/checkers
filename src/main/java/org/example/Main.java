package org.example;

import org.example.model.Game;
import org.example.options.GameVariantSetup;
import org.example.ui.GameplayPanel;
import org.example.ui.OptionsPanel;
import org.example.ui.WelcomePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

//
//interface ViewController {
//    void nextView();
//    void previousView();
//}
//
//class DefaultViewController implements ViewController {
//    private Container parent;
//    private Component current;
//    private CardLayout cardLayout;
//    private LinkedHashMap<Component, String> views;
//
//
//    public DefaultViewController(Container parent, CardLayout cardLayout) {
//        this.parent = parent;
//        this.cardLayout = cardLayout;
//        views = new LinkedHashMap<>();
//    }
//
//    public Container getParent() {
//        return parent;
//    }
//
//    public CardLayout getCardLayout() {
//        return cardLayout;
//    }
//
//    public void addView(Component component, String name) {
//        if(views.isEmpty()) {
//            current = component;
//        }
//        views.put(component, name);
//        getParent().add(component);
//    }
//
//    public void removeView(Component component, String name) {
//        views.remove(component, name);
//        if(views.isEmpty()) {
//            current = null;
//        }
//        getParent().remove(component);
//    }
//
//    @Override
//    public void nextView() {
//        if(!views.isEmpty()) {
//            views
//            getCardLayout().show(getParent(), views.get(current).ne);
//        }
//    }
//
//    @Override
//    public void previousView() {
//
//    }
//}

public class Main {
    private final JFrame jFrame;
    private final GameVariantSetup gameVariantSetup;
    private WelcomePanel welcomePanel;
    private OptionsPanel optionsPanel;
    private GameplayPanel gameplayPanel;

    public void addComponentsToPane(Container pane) {
        CardLayout cardLayout = new CardLayout();
        pane.setLayout(cardLayout);

        welcomePanel = new WelcomePanel();
        optionsPanel = new OptionsPanel(gameVariantSetup);
        gameplayPanel = new GameplayPanel();

//        pane.add("WELCOME", welcomePanel);
//        pane.add("OPTIONS", optionsPanel);
        pane.add("GAMEPLAY", gameplayPanel);
        gameplayPanel.setGame(new Game(gameVariantSetup.getSelectedVariant()));

//        welcomePanel.getStartButton().addActionListener(_ -> cardLayout.show(pane, "OPTIONS"));
//        welcomePanel.getExitButton().addActionListener(_ -> jFrame.dispose());

//        JComboBox<String> variantComboBox = optionsPanel.getVariantComboBox();
//        variantComboBox.addActionListener(_ -> {
//            String name = variantComboBox.getItemAt(variantComboBox.getSelectedIndex());
//            gameVariantSetup.select(name);
//        });
//        optionsPanel.getPlayButton().addActionListener(_ -> {
//            gameplayPanel.setGame(new Game(gameVariantSetup.getSelectedVariant()));
//            cardLayout.show(pane, "GAMEPLAY");
//        });
//        optionsPanel.getCancelButton().addActionListener(_ -> cardLayout.show(pane, "WELCOME"));
//
//        gameplayPanel.getQuitButton().addActionListener(_ -> {
//            gameplayPanel.removeGame();
//            cardLayout.show(pane, "OPTIONS");
//        });
    }

    public GameplayPanel getGameplayPanel() {
        return gameplayPanel;
    }

    public Main() {
        jFrame = new JFrame("Checkers");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(640, 480);

        gameVariantSetup = new GameVariantSetup();

        addComponentsToPane(jFrame.getContentPane());

        jFrame.setVisible(true);
    }

    void main() {
//        GameVariantSetup gameVariantSetup = new GameVariantSetup();
//        GameWindow gameWindow = new GameWindow();
//
//        WelcomePanel welcomePanel = new WelcomePanel(gameWindow);
//        NewGamePanel newGamePanel = new NewGamePanel(gameVariantSetup);
//        GameplayPanel gameplayPanel = new GameplayPanel(gameVariantSetup);
//
//        gameWindow.add(welcomePanel);
//        gameWindow.add(newGamePanel);
//        gameWindow.add(gameplayPanel);
//        gameWindow.display();

        SwingUtilities.invokeLater(() -> {
//            new Main();
            Robot robot;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }
            int[] rows = {9, 8, 0, 1, 8, 7, 1, 2, 7, 6, 2, 3, 6, 5, 3, 4, 5, 4, 4, 5, 4, 3, 5, 6, 3, 2, 6, 7, 2, 1, 7, 8, 1, 0, 8, 9};
            int[] cols = {8, 9, 1, 0, 9, 8, 0, 1, 8, 9, 1, 0, 9, 8, 0, 1, 8, 9, 1, 0, 9, 8, 0, 1, 8, 9, 1, 0, 9, 8, 0, 1, 8, 9, 1, 0, 9, 8, 0, 1};
            for(int i = 0; i < 36; i++) {
                Point point = getGameplayPanel().getSquareButton(rows[i], cols[i]).getLocationOnScreen();
                point.x += getGameplayPanel().getSquareButton(rows[i], cols[i]).getWidth() / 2;
                point.y += getGameplayPanel().getSquareButton(rows[i], cols[i]).getHeight() / 2;
                robot.mouseMove(point.x, point.y);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                if(i % 2 == 1) {
                    point = getGameplayPanel().getConfirmButton().getLocationOnScreen();
                    point.x += getGameplayPanel().getConfirmButton().getWidth() / 2;
                    point.y += getGameplayPanel().getConfirmButton().getHeight() / 2;
                    robot.mouseMove(point.x, point.y);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }
            }

        });
    }
}
