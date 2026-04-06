package org.example;

import org.example.options.GameVariantSetup;
import org.example.ui.GameplayPanel;
import org.example.ui.OptionsPanel;
import org.example.ui.WelcomePanel;

import javax.swing.*;
import java.awt.*;

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

        pane.add("WELCOME", welcomePanel);
        pane.add("OPTIONS", optionsPanel);
        pane.add("GAMEPLAY", gameplayPanel);

        welcomePanel.getStartButton().addActionListener(_ -> cardLayout.show(pane, "OPTIONS"));
        welcomePanel.getExitButton().addActionListener(_ -> jFrame.dispose());

        JComboBox<String> variantComboBox = optionsPanel.getVariantComboBox();
        variantComboBox.addActionListener(_ -> {
            String name = variantComboBox.getItemAt(variantComboBox.getSelectedIndex());
            gameVariantSetup.select(name);
        });
        optionsPanel.getPlayButton().addActionListener(_ -> {
            gameplayPanel.setGame(gameVariantSetup.getGame());
            cardLayout.show(pane, "GAMEPLAY");
        });
        optionsPanel.getCancelButton().addActionListener(_ -> cardLayout.show(pane, "WELCOME"));

        gameplayPanel.getQuitButton().addActionListener(_ -> {
            gameplayPanel.removeGame();
            cardLayout.show(pane, "OPTIONS");
        });
    }

    public Main() {
        jFrame = new JFrame("Checkers");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(640, 480);

        gameVariantSetup = new GameVariantSetup();
    }

    void run() {
        addComponentsToPane(jFrame.getContentPane());

        jFrame.setVisible(true);
    }

    void main() {
        SwingUtilities.invokeLater(Main.this::run);
    }
}
