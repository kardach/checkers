package org.example;

import com.jogamp.opengl.GLCapabilities;
import org.example.panels.MainPanel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindow {
    private final GLCapabilities glCapabilities;
    private final JFrame jFrame;

    public GameWindow(GLCapabilities glCapabilities) {
        this.glCapabilities = glCapabilities;

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
    }

    public GLCapabilities getGlCapabilities() {
        return glCapabilities;
    }

    public void add(MainPanel mainPanel) {
        jFrame.add(mainPanel.getJPanel());
    }

    public void setVisible(boolean visible) {
        jFrame.setVisible(visible);
    }

    public void dispose() {
        jFrame.dispose();
    }
}
