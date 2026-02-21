package org.example;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;

public class Main {
    static String selected;

    static void main() {
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        GameWindow gameWindow = new GameWindow(glcapabilities);

    }
}
