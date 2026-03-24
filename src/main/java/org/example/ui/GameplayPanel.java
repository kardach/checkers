package org.example.ui;

import org.example.model.Game;
import org.example.model.Piece;
import org.example.model.Square;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class GameplayPanel extends JPanel {
    private JButton[][] squareButtons;
    private final JPanel buttonsPanel;
    private JPanel boardPanel;
    private final JPanel boardContainerPanel;
    private JButton selectedSquare;
    private JButton confirmButton;
    private JButton quitButton;
    private Game game;

    public GameplayPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.RED, 5));

        buttonsPanel = getButtonsPanel();
        boardContainerPanel = getBoardContainerPanel();

        add(buttonsPanel, BorderLayout.WEST);

        selectedSquare = null;
    }

    public void setGame(Game game) {
        this.game = game;
        boardPanel = getBoardPanel(game);
        add(boardPanel);
    }

    public void removeGame() {
        this.game = null;
        boardContainerPanel.remove(boardPanel);
        boardPanel = null;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getQuitButton() {
        return quitButton;
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));

        confirmButton = new JButton("Confirm button");
        confirmButton.setFont(new Font("Dia log", Font.BOLD, 18));

        quitButton = new JButton("Quit button");
        quitButton.setFont(new Font("Dialog", Font.BOLD, 18));

        buttonsPanel.add(confirmButton);
        buttonsPanel.add(quitButton);
        buttonsPanel.add(Box.createVerticalGlue());

        return buttonsPanel;
    }

    private JPanel getBoardPanel(Game game) {
        JPanel boardPanel = new JPanel(new BoardLayout(10, 10));
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 0));
        addSquareButtons(boardPanel, game);

        return boardPanel;
    }

    private void addSquareButtons(JPanel boardPanel, Game game) {
        int boardSize = game.getBoard().getSize();
        squareButtons = new JButton[boardSize][boardSize];
        for(int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                JButton jButton = new JButton();
                SquareButtonUI ui = new SquareButtonUI();
                ui.setSquare(game.getBoard().at(row, col));
                jButton.setUI(ui);
                jButton.setBorderPainted(false);
//                jButton.setRolloverEnabled(false);
                boardPanel.add(jButton);
                squareButtons[row][col] = jButton;
            }
        }
    }

    private JPanel getBoardContainerPanel() {
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridBagLayout());
        containerPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
        return containerPanel;
    }

    static class SquareButtonUI extends BasicButtonUI {
        private Square square;
        private boolean selected;

        public void setSquare(Square square) {
            this.square = square;
        }

        public Square getSquare() {
            return square;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        private void paintMan(Graphics2D g2, Dimension size) {
            Piece piece = square.getPiece();
            Color pieceColor = piece.getColor() == org.example.model.Color.BLACK ? Color.BLACK : Color.WHITE;
            Color pieceBorderColor = piece.getColor() == org.example.model.Color.BLACK ? Color.WHITE : Color.BLACK;

            int borderThickness = (size.height + 5) / 20;

            g2.setColor(pieceBorderColor);
            g2.fillArc(0, size.height * 3 / 8, size.width, size.height / 2, 0, -180);
            g2.setColor(pieceColor);
            g2.fillArc(borderThickness, size.height * 3 / 8 + borderThickness,
                    size.width -  2 * borderThickness, size.height / 2 - 2 * borderThickness,
                    0, -180);

            g2.setColor(pieceBorderColor);
            g2.fillRect(0, size.height * 3 / 8, size.width, size.height / 4 + 1);
            g2.setColor(pieceColor);
            g2.fillRect(borderThickness, size.height * 3 / 8, size.width - 2 * borderThickness,
                    size.height / 4 + 2);

            g2.setColor(pieceBorderColor);
            g2.fillOval(0, size.height / 8, size.width, size.height / 2);
            g2.setColor(pieceColor);
            g2.fillOval(borderThickness, size.height / 8 + borderThickness,
                    size.width - 2 * borderThickness, size.height / 2 - 2 * borderThickness);
        }

        private void paintKing(Graphics2D g, Dimension size) {

        }

        private void paint(Graphics g, Dimension size) {
            Color squareColor;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if(selected) {
                squareColor = Color.GREEN;
            } else if(getSquare().getColor() == org.example.model.Color.BLACK) {
                squareColor = new Color(139, 69, 19);
            } else {
                squareColor = new Color(255, 228, 196);
            }
            g2.setColor(squareColor);
            g2.fillRect(0, 0, size.width, size.height);

            if(square.hasPiece()) {
                if(square.getPiece().getType() == Piece.Type.MAN) {
                    paintMan(g2, size);
                } else {
                    paintKing(g2, size);
                }
            }
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            super.paint(g, c);
            Dimension size = c.getSize();
            paint(g, size);
        }

        @Override
        protected void paintButtonPressed(Graphics g, AbstractButton b) {
            super.paintButtonPressed(g, b);
            if(b.isContentAreaFilled()) {
                Dimension size = b.getSize();
                paint(g, size);
            }
        }
    }
}