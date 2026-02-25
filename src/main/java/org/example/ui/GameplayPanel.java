package org.example.ui;

import org.example.model.Game;
import org.example.options.GameVariantSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameplayPanel extends PanelWrapper {
    private static VariantLabel variantLabel;
    private static boolean becameVisible;
    private Game game;

    public GameplayPanel(GameVariantSetup gameVariantSetup) {
        variantLabel = new VariantLabel();
        becameVisible = false;
        jPanel.setName("GAMEPLAY");
        jPanel.setLayout(null);
        jPanel.addComponentListener(new ComponentListener() {
            private GameVariantSetup gameVariantSetup;

            private ComponentListener init(GameVariantSetup gameVariantSetup) {
                this.gameVariantSetup = gameVariantSetup;
                return this;
            }

            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
                if(!becameVisible) {
                    becameVisible = true;
                    variantLabel.setText(gameVariantSetup.getSelected());
                    game = new Game(gameVariantSetup.getSelectedVariant());
                }
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                becameVisible = false;
            }
        }.init(gameVariantSetup));
        jPanel.add(new QuitButton());
        jPanel.add(variantLabel);
        jPanel.add(new JComponent() {
            @Override
            public Dimension getMinimumSize() {
                return new Dimension(100, 100);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(400, 300);
            }

            @Override
            public Dimension getMaximumSize() {
                return new Dimension(800, 600);
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED);
                g.fillRect(300, 0, 200, 200);
            }
        });
    }

    static class VariantLabel extends JLabel {
        public VariantLabel() {
            setBounds(0, 200, 200, 50);
            setFont(new Font("Dialog", Font.BOLD, 18));
            setBackground(Color.WHITE);
        }
    }

    static class QuitButton extends JButton {
        public QuitButton() {
            setText("Quit game");
            setBounds(0, 0, 200, 50);
            setFont(new Font("Dialog", Font.BOLD, 18));
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    cardLayout.previous(parent);
                }

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            });
        }
    }
}
