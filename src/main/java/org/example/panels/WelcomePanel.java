package org.example.panels;

import org.example.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WelcomePanel extends JPanel{
    public WelcomePanel(GameWindow gameWindow, CardLayout cardLayout, Font font) {
        setName("WELCOME");
        setLayout(null);
        add(getStartButton(cardLayout, font));
        add(getExitButton(gameWindow, font));
        getParent();
    }

    private JButton getExitButton(GameWindow gameWindow, Font font) {
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(0, 100, 200, 50);
        exitButton.setFont(font);
        exitButton.addMouseListener(new MouseListener() {
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
        return exitButton;
    }

    private JButton getStartButton(CardLayout cardLayout, Font font) {
        JButton startButton = new JButton("Start new game");
        startButton.setBounds(0, 0, 200, 50);
        startButton.setFont(font);
        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                cardLayout.next(getParent());
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        return startButton;
    }
}
