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
    private static Game game;
    private static BoardPanel boardPanel;

    public GameplayPanel(GameVariantSetup gameVariantSetup) {
        variantLabel = new VariantLabel();
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
                int size = Math.min(jPanel.getSize().width - 200, jPanel.getSize().height);
//                if(boardPanel != null) {
//                    boardPanel.setSize(size);
//                };
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
                int size = Math.min(jPanel.getSize().width - 200, jPanel.getSize().height);
                game = new Game(gameVariantSetup.getSelectedVariant());
                boardPanel = new BoardPanel(game.getBoard(), size);
                jPanel.add(boardPanel);
                variantLabel.setText(gameVariantSetup.getSelected());
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                if(boardPanel != null) {
                    jPanel.remove(boardPanel);
                }
                game = null;
                boardPanel = null;
                variantLabel.setText("");
            }
        }.init(gameVariantSetup));
        jPanel.add(new QuitButton());
        jPanel.add(new ConfirmMoveButton());
        jPanel.add(variantLabel);
    }

    static class VariantLabel extends JLabel {
        public VariantLabel() {
            setBounds(0, 100, 200, 50);
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

    static class ConfirmMoveButton extends JButton {
        public ConfirmMoveButton() {
            setText("Confirm move");
            setBounds(0, 200, 200, 50);
            setFont(new Font("Dialog", Font.BOLD, 18));
        }
    }
}
