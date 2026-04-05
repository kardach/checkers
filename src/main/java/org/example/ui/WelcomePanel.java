package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    private final JButton startButton;
    private final JButton exitButton;

    public WelcomePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        startButton = new JButton("Start new game");
        startButton.setFont(new Font("Dialog", Font.BOLD, 18));

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Dialog", Font.BOLD, 18));

        add(startButton);
        add(exitButton);
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}
