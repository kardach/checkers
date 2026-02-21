package org.example.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NewGamePanel {
    private final JPanel jPanel;
    private static MainPanel parent;
    private static CardLayout cardLayout;

    public NewGamePanel() {
        String[] variants = {"International/Polish", "Ghanaian/damii"};
        JComboBox<String> variant = new JComboBox<>(variants);
        variant.setBounds(0, 0, 200, 50);

        jPanel = new JPanel();
        jPanel.setName("NEW_GAME");
        jPanel.setLayout(null);
        jPanel.add(variant);
        jPanel.add(getPlayButton(variant));
        jPanel.add(getCancelButton());
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    void setParent(MainPanel mainPanel) {
        parent = mainPanel;
        cardLayout = parent.getLayout();
    }

    private JButton getPlayButton(JComboBox<String> variant) {
        JButton quitButton = new JButton("Play");
        quitButton.setBounds(0, 100, 200, 50);
        quitButton.setFont(new Font("Dialog", Font.BOLD, 18));
        quitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
//                selected = Objects.requireNonNull(variant.getSelectedItem()).toString();
                cardLayout.next(parent.getJPanel());
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        return quitButton;
    }

    private JButton getCancelButton() {
        JButton quitButton = new JButton("Cancel");
        quitButton.setBounds(0, 200, 200, 50);
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
