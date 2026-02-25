package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindow {
    private final JFrame jFrame;
    private final JPanel jPanel;
    private final CardLayout cardLayout;

    public GameWindow() {
        cardLayout = new CardLayout();
        jPanel = new JPanel(cardLayout);
        jFrame = new JFrame();
        jFrame.setTitle("Checkers");
        jFrame.setSize(640, 480);
        jFrame.addWindowListener(new WindowAdapter() {
            private JFrame jFrame;

            private WindowAdapter init(JFrame jFrame) {
                this.jFrame = jFrame;
                return this;
            }

            public void windowClosing(WindowEvent windowevent) {
                jFrame.dispose();
                System.exit(0);
            }
        }.init(jFrame));
        jFrame.add(jPanel);
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    public CardLayout getLayout() {
        return cardLayout;
    }

    public void add(PanelWrapper panelWrapper) {
        panelWrapper.setParent(this);
        jPanel.add(panelWrapper.getJPanel());
    }

    public void display() {
        jFrame.setMinimumSize(jFrame.getSize());
        jFrame.setVisible(true);
    }

    public void dispose() {
        jFrame.dispose();
    }
}
