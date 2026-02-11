package org.example;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLJPanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class OneTriangle {
    protected static void setup(GL2 gl2, int width, int height) {
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();

        GLU glu = new GLU();
        glu.gluOrtho2D(0.0f, width, 0.0f, height);

        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();

        gl2.glViewport(0, 0, width, height);
    }

    protected static void render(GL2 gl2, int width, int height) {
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl2.glLoadIdentity();
        gl2.glBegin(GL.GL_TRIANGLES);
        gl2.glColor3f(1, 0, 0);
        gl2.glVertex2f(0, 0);
        gl2.glColor3f(0, 1, 0);
        gl2.glVertex2f(width, 0);
        gl2.glColor3f(0, 0, 1);
        gl2.glVertex2f((float) width / 2, height);
        gl2.glEnd();
    }
}

public class Main {
    static final GLProfile glprofile = GLProfile.getDefault();
    static final GLCapabilities glcapabilities = new GLCapabilities(glprofile);
    static final CardLayout cardLayout = new CardLayout();
    static final JPanel mainPanel = new JPanel(cardLayout);
    static final Font font = new Font("Dialog", Font.BOLD, 18);
    static final JFrame jframe = new JFrame("One Triangle Swing GLJPanel");

    static void main() {
        jframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowevent) {
                jframe.dispose();
                System.exit(0);
            }
        });
        mainPanel.setName("MAIN");

        JPanel welcomePanel = getWelcomePanel();
        GLJPanel trianglePanel = getGLJPanel();

        jframe.add(mainPanel);
        mainPanel.add(welcomePanel);
        mainPanel.add(trianglePanel, BorderLayout.CENTER);

        jframe.setSize(640, 480);
        jframe.setVisible(true);
    }

    private static GLJPanel getGLJPanel() {
        GLJPanel trianglePanel = new GLJPanel(Main.glcapabilities);
        trianglePanel.setName("TRIANGLE");
        trianglePanel.setLayout(null);
        trianglePanel.addGLEventListener(new GLEventListener() {
            @Override
            public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
                OneTriangle.setup(glautodrawable.getGL().getGL2(), width, height);
            }

            @Override
            public void init(GLAutoDrawable glautodrawable) {}

            @Override
            public void dispose(GLAutoDrawable glautodrawable) {}

            @Override
            public void display(GLAutoDrawable glautodrawable) {
                OneTriangle.render(glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(),
                        glautodrawable.getSurfaceHeight());
            }
        });
        JButton quitButton = getQuitButton();
        trianglePanel.add(quitButton);
        return trianglePanel;
    }

    private static JPanel getWelcomePanel() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setName("WELCOME");
        welcomePanel.setLayout(null);
        JButton startButton = getStartButton();
        welcomePanel.add(startButton);
        JButton exitButton = getExitButton();
        welcomePanel.add(exitButton);
        return welcomePanel;
    }

    private static JButton getExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(0, 100, 200, 50);
        exitButton.setFont(font);
        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)) {
                    jframe.dispose();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        return exitButton;
    }

    private static JButton getStartButton() {
        JButton startButton = new JButton("Start new game");
        startButton.setBounds(0, 0, 200, 50);
        startButton.setFont(font);
        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                cardLayout.next(mainPanel);
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        return startButton;
    }

    private static JButton getQuitButton() {
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
                cardLayout.previous(mainPanel);
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        return quitButton;
    }

}
