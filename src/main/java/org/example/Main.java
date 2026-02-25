package org.example;

import org.example.ui.GameWindow;
import org.example.ui.GameplayPanel;
import org.example.ui.NewGamePanel;
import org.example.ui.WelcomePanel;
import org.example.options.GameVariantSetup;

public class Main {
    static void main() {
        GameVariantSetup gameVariantSetup = new GameVariantSetup();
        GameWindow gameWindow = new GameWindow();

        WelcomePanel welcomePanel = new WelcomePanel(gameWindow);
        NewGamePanel newGamePanel = new NewGamePanel(gameVariantSetup);
        GameplayPanel gameplayPanel = new GameplayPanel(gameVariantSetup);
//;

        gameWindow.add(welcomePanel);
        gameWindow.add(newGamePanel);
        gameWindow.add(gameplayPanel);
        gameWindow.display();
    }
}
