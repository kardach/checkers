package org.example;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;
import org.example.panels.GameplayPanel;
import org.example.panels.MainPanel;
import org.example.panels.NewGamePanel;
import org.example.panels.WelcomePanel;
import org.example.variants.GameVariantSetup;

public class Main {
    static void main() {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GameVariantSetup gameVariantSetup = new GameVariantSetup();
        GameWindow gameWindow = new GameWindow(glCapabilities);

        MainPanel mainPanel = new MainPanel();
        WelcomePanel welcomePanel = new WelcomePanel(gameWindow);
        NewGamePanel newGamePanel = new NewGamePanel(gameVariantSetup);
        GameplayPanel gameplayPanel = new GameplayPanel(glCapabilities, gameVariantSetup);

        mainPanel.add(welcomePanel);
        mainPanel.add(newGamePanel);
        mainPanel.add(gameplayPanel);

        gameWindow.add(mainPanel);
        gameWindow.setVisible(true);
    }
}
