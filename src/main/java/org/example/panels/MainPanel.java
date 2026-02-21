package org.example.panels;

import com.jogamp.opengl.GLCapabilities;
import org.example.GameWindow;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel{
    public MainPanel(GLCapabilities glCapabilities, GameWindow gameWindow) {
        CardLayout cardLayout = new CardLayout();
        Font font = new Font("Dialog", Font.BOLD, 18);

        setLayout(cardLayout);
        setName("MAIN");
        add(new WelcomePanel(gameWindow, cardLayout, font));
        add(new NewGamePanel(cardLayout, font));
        add(new GameplayPanel(glCapabilities, cardLayout, font), BorderLayout.CENTER);
    }
}
