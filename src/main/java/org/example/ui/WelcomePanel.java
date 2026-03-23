package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WelcomePanel extends PanelWrapper {
    private final StartButton startButton;
    private final ExitButton exitButton;

    public WelcomePanel(GameWindow gameWindow) {
        startButton = new StartButton();
        exitButton = new ExitButton(gameWindow);
        jPanel.setName("WELCOME");
        jPanel.setLayout(null);
        jPanel.add(startButton);
        jPanel.add(exitButton);
    }

    public StartButton getStartButton() {
        return startButton;
    }

    public ExitButton getExitButton() {
        return exitButton;
    }

    public class StartButton extends JButton {
        public StartButton() {
            setText("Start new game");
            setBounds(0, 0, 200, 50);
            setFont(new Font("Dialog", Font.BOLD, 18));
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    if(cardLayout != null) {
                        cardLayout.next(parent);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            });
        }
    }

    public class ExitButton extends JButton {
        public ExitButton(GameWindow gameWindow) {
            setText("Exit");
            setBounds(0, 100, 200, 50);
            setFont(new Font("Dialog", Font.BOLD, 18));
            addMouseListener(new MouseListener() {
                private GameWindow gameWindow;

                private MouseListener init(GameWindow gameWindow) {
                    this.gameWindow = gameWindow;
                    return this;
                }

                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    if(SwingUtilities.isLeftMouseButton(e)) {
                        gameWindow.dispose();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            }.init(gameWindow));
        }
    }
}
