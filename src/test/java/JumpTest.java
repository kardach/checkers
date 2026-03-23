import org.example.options.GameVariantSetup;
import org.example.ui.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

import static org.junit.jupiter.api.Assertions.*;

public class JumpTest {
    @Test
    void test() throws AWTException {
        GameVariantSetup gameVariantSetup = new GameVariantSetup();
        GameWindow gameWindow = new GameWindow();

        WelcomePanel welcomePanel = new WelcomePanel(gameWindow);
        NewGamePanel newGamePanel = new NewGamePanel(gameVariantSetup);
        GameplayPanel gameplayPanel = new GameplayPanel(gameVariantSetup);

        gameWindow.add(welcomePanel);
        gameWindow.add(newGamePanel);
        gameWindow.add(gameplayPanel);
        gameWindow.display();

        Robot robot = new Robot();

        assertTrue(welcomePanel.getJPanel().isVisible());

        Point point = welcomePanel.getStartButton().getLocationOnScreen();
        robot.mouseMove(point.x, point.y);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        assertTrue(newGamePanel.getJPanel().isVisible());
    }
}
