package org.example;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;
import org.example.panels.GameplayPanel;
import org.example.panels.MainPanel;
import org.example.panels.NewGamePanel;
import org.example.panels.WelcomePanel;

public class Main {
    static void main() {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GameWindow gameWindow = new GameWindow(glCapabilities);

        MainPanel mainPanel = new MainPanel();
        WelcomePanel welcomePanel = new WelcomePanel(gameWindow);
        NewGamePanel newGamePanel = new NewGamePanel();
        GameplayPanel gameplayPanel = new GameplayPanel(glCapabilities);

        mainPanel.add(welcomePanel);
        mainPanel.add(newGamePanel);
        mainPanel.add(gameplayPanel);

        gameWindow.add(mainPanel);
        gameWindow.setVisible(true);
    }
}
