package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class PanelWrapper {
    protected final JPanel jPanel;
    protected static JPanel parent;
    protected static CardLayout cardLayout;

    public PanelWrapper() {
        jPanel = new JPanel();
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    void setParent(GameWindow gameWindow) {
        parent = gameWindow.getJPanel();
        cardLayout = gameWindow.getLayout();
    }
}
