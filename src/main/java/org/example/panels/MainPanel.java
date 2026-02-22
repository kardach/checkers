package org.example.panels;

import javax.swing.*;
import java.awt.*;

public class MainPanel {
    private final JPanel jPanel;
    private final CardLayout cardLayout;
    public MainPanel() {
        jPanel = new JPanel();
        cardLayout = new CardLayout();
        jPanel.setLayout(cardLayout);
        jPanel.setName("MAIN");
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    public CardLayout getLayout() {
        return cardLayout;
    }

    public void add(WelcomePanel welcomePanel) {
        welcomePanel.setParent(this);
        jPanel.add(welcomePanel.getJPanel());
    }

    public void add(NewGamePanel newGamePanel) {
        newGamePanel.setParent(this);
        jPanel.add(newGamePanel.getJPanel());
    }

    public void add(GameplayPanel gameplayPanel) {
        gameplayPanel.setParent(this);
        jPanel.add(gameplayPanel.getGLJPanel());
    }
}
