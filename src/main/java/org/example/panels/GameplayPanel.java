package org.example.panels;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import org.example.Triangle;
import org.example.variants.GameVariantSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameplayPanel {
    private final GLJPanel gljPanel;
    private static MainPanel parent;
    private static CardLayout cardLayout;
    private static VariantLabel variantLabel;
    private static boolean becameVisible;

    public GameplayPanel(GLCapabilities glCapabilities, GameVariantSetup gameVariantSetup) {
        becameVisible = false;
        gljPanel = new GLJPanel(glCapabilities);
        gljPanel.setName("TRIANGLE");
        gljPanel.setLayout(null);
        gljPanel.addGLEventListener(new GLEventListener() {
            private GameVariantSetup gameVariantSetup;

            private GLEventListener init(GameVariantSetup gameVariantSetup) {
                this.gameVariantSetup = gameVariantSetup;
                return this;
            }

            @Override
            public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
                Triangle.setup(glautodrawable.getGL().getGL2(), width, height);
            }

            @Override
            public void init(GLAutoDrawable glautodrawable) {}

            @Override
            public void dispose(GLAutoDrawable glautodrawable) {}

            @Override
            public void display(GLAutoDrawable glautodrawable) {
                if(!becameVisible) {
                    becameVisible = true;
                    variantLabel.setText(gameVariantSetup.getSelected());
                }

                Triangle.render(glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(),
                        glautodrawable.getSurfaceHeight());
            }
        }.init(gameVariantSetup));
        gljPanel.add(new QuitButton());
        variantLabel = new VariantLabel();
        gljPanel.add(variantLabel);
    }

    public GLJPanel getGLJPanel() {
        return gljPanel;
    }

    void setParent(MainPanel mainPanel) {
        parent = mainPanel;
        cardLayout = parent.getLayout();
    }

    static class VariantLabel extends JLabel {
        public VariantLabel() {
            setBounds(200, 200, 200, 50);
            setFont(new Font("Dialog", Font.BOLD, 18));
            setBackground(Color.WHITE);
        }
    }

    static class QuitButton extends JButton {
        public QuitButton() {
            setText("Quit game");
            setBounds(0, 0, 200, 50);
            setFont(new Font("Dialog", Font.BOLD, 18));
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    cardLayout.previous(parent.getJPanel());
                    becameVisible = false;
                }

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            });
        }
    }
}
