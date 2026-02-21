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

public class GameplayPanel extends GLJPanel {
    public GameplayPanel(GLCapabilities glCapabilities, CardLayout cardLayout, Font font) {
        super(glCapabilities);
        setName("TRIANGLE");
        setLayout(null);
        addGLEventListener(new GLEventListener() {
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
        add(getQuitButton(cardLayout, font));
        JLabel variant = new JLabel();
//        variant.setText(selected);
        variant.setBounds(200, 200, 200, 50);
        variant.setFont(font);
        add(variant);
    }

    private JButton getQuitButton(CardLayout cardLayout, Font font) {
        JButton quitButton = new JButton("Quit game");
        quitButton.setBounds(0, 0, 200, 50);
        quitButton.setFont(font);
        quitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                cardLayout.previous(getParent());
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        return quitButton;
    }
}
