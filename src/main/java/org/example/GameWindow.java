package org.example;

import com.jogamp.opengl.GLCapabilities;
import org.example.panels.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindow extends JFrame {
    public GameWindow(GLCapabilities glcapabilities) {
        setTitle("Checkers");
        addWindowListener(new WindowAdapter() {
            private GameWindow gameWindow;

            private WindowAdapter init(GameWindow gameWindow) {
                this.gameWindow = gameWindow;
                return this;
            }

            public void windowClosing(WindowEvent windowevent) {
                gameWindow.dispose();
                System.exit(0);
            }
        }.init(this) );
        add(new MainPanel(glcapabilities, this));
        setSize(640, 480);
        setVisible(true);
    }
}
