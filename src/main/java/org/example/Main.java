package org.example;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLJPanel;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

    static void main() {
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        GLJPanel gljpanel = getGLJPanel(glcapabilities);

        final JFrame jframe = new JFrame("One Triangle Swing GLJPanel");
        jframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowevent) {
                jframe.dispose();
                System.exit(0);
            }
        });

        jframe.getContentPane().add(gljpanel, BorderLayout.CENTER);
        jframe.setSize(640, 480);
        jframe.setVisible(true);
    }

    private static GLJPanel getGLJPanel(GLCapabilities glcapabilities) {
        GLJPanel gljpanel = new GLJPanel(glcapabilities);

        gljpanel.addGLEventListener(new GLEventListener() {
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
        return gljpanel;
    }
}
