package org.example.panels;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import org.example.Triangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameplayPanel {
    private final GLJPanel gljPanel;
    private static MainPanel parent;
    private static CardLayout cardLayout;

    public GameplayPanel(GLCapabilities glCapabilities) {
        gljPanel = new GLJPanel(glCapabilities);
        gljPanel.setName("TRIANGLE");
        gljPanel.setLayout(null);
        gljPanel.addGLEventListener(new GLEventListener() {
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
                Triangle.render(glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(),
                        glautodrawable.getSurfaceHeight());
            }
        });
        gljPanel.add(getQuitButton());
        JLabel variant = new JLabel();
//        variant.setText(selected);
        variant.setBounds(200, 200, 200, 50);
        variant.setFont(new Font("Dialog", Font.BOLD, 18));
        gljPanel.add(variant);
    }


    public GLJPanel getGLJPanel() {
        return gljPanel;
    }

    void setParent(MainPanel mainPanel) {
        parent = mainPanel;
        cardLayout = parent.getLayout();
    }

    private JButton getQuitButton() {
        JButton quitButton = new JButton("Quit game");
        quitButton.setBounds(0, 0, 200, 50);
        quitButton.setFont(new Font("Dialog", Font.BOLD, 18));
        quitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                cardLayout.previous(parent.getJPanel());
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        return quitButton;
    }
}
