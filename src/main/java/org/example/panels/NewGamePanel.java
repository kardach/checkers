package org.example.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NewGamePanel extends JPanel{
    public NewGamePanel(CardLayout cardLayout, Font font) {
        String[] variants = {"International/Polish", "Ghanaian/damii"};
        JComboBox<String> variant = new JComboBox<>(variants);
        variant.setBounds(0, 0, 200, 50);

        setName("NEW_GAME");
        setLayout(null);
        add(variant);
        add(getPlayButton(cardLayout, font, variant));
        add(getCancelButton(cardLayout, font));
    }

    private JButton getPlayButton(CardLayout cardLayout, Font font, JComboBox<String> variant) {
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
//                selected = Objects.requireNonNull(variant.getSelectedItem()).toString();
                cardLayout.next(getParent());
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        return quitButton;
    }

    private JButton getCancelButton(CardLayout cardLayout, Font font) {
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
