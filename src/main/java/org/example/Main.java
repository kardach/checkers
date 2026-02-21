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
import java.util.Objects;

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
    static String selected;

    static void main() {
        jframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowevent) {
                jframe.dispose();
                System.exit(0);
            }
        });
        mainPanel.setName("MAIN");



        JPanel welcomePanel = getWelcomePanel();
        JPanel newGamePanel = getNewGamePanel();
        GLJPanel trianglePanel = getGLJPanel();

        jframe.add(mainPanel);
        mainPanel.add(welcomePanel);
        mainPanel.add(newGamePanel);
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
        JLabel variant = new JLabel();
        variant.setText(selected);
        variant.setBounds(200, 200, 200, 50);
        variant.setFont(font);
        trianglePanel.add(variant);
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

    private static JPanel getNewGamePanel() {
        JPanel newGamePanel = new JPanel();
        newGamePanel.setName("NEW_GAME");
        newGamePanel.setLayout(null);
        String[] variants = {"International/Polish", "Ghanaian/damii"};
        JComboBox<String> variant = new JComboBox<>(variants);
        variant.setBounds(0, 0, 200, 50);
        newGamePanel.add(variant);


        JButton playButton = getPlayButton(variant);
        newGamePanel.add(playButton);
        JButton cancelButton = getCancelButton();
        newGamePanel.add(cancelButton);
        return newGamePanel;
    }

    private static JButton getPlayButton(JComboBox<String> variant) {
        JButton quitButton = new JButton("Play");
        quitButton.setBounds(0, 100, 200, 50);
        quitButton.setFont(font);
        quitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                selected = Objects.requireNonNull(variant.getSelectedItem()).toString();
                cardLayout.next(mainPanel);
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        return quitButton;
    }

    private static JButton getCancelButton() {
        JButton quitButton = new JButton("Cancel");
        quitButton.setBounds(0, 200, 200, 50);
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
