package org.example.panels;

import org.example.variants.GameVariantSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NewGamePanel {
    private final JPanel jPanel;
    private static MainPanel parent;
    private static CardLayout cardLayout;
    private final GameVariantSetup gameVariantSetup;

    public NewGamePanel(GameVariantSetup gameVariantSetup) {
        this.gameVariantSetup = gameVariantSetup;
        gameVariantSetup.select(gameVariantSetup.getVariantNames()[0]);

        jPanel = new JPanel();
        jPanel.setName("NEW_GAME");
        jPanel.setLayout(null);
        jPanel.add(new VariantComboBox());
        jPanel.add(new PlayButton());
        jPanel.add(new CancelButton());
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    void setParent(MainPanel mainPanel) {
        parent = mainPanel;
        cardLayout = parent.getLayout();
    }

    class VariantComboBox extends JComboBox<String> {
        public VariantComboBox() {
            super(gameVariantSetup.getVariantNames());
            setBounds(0, 0, 200, 50);
            addItemListener(e -> {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    gameVariantSetup.select(e.getItem().toString());
                }
            });
        }
    }

    static class PlayButton extends JButton {
        public PlayButton() {
            setText("Play");
            setBounds(0, 100, 200, 50);
            setFont(new Font("Dialog", Font.BOLD, 18));
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    cardLayout.next(parent.getJPanel());
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }
    }

    static class CancelButton extends JButton {
        public CancelButton() {
            setText("Cancel");
            setBounds(0, 200, 200, 50);
            setFont(new Font("Dialog", Font.BOLD, 18));
            addMouseListener(new MouseListener() {
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
        }
    }
}
