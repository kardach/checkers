package org.example.ui;

import org.example.model.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class GameplayPanel extends JPanel {
    private final JLayeredPane containerPane;
    private final JPanel buttonsPanel;
    private JPanel boardPanel;
    private JPanel arrowsPanel;
    private JRadioButton[][] squareButtons;
    private ArrayList<JRadioButton> clickableButtons;
    private ButtonGroup squareGroup;
    private JButton confirmButton;
    private JButton quitButton;
    private JLabel variantNameLabel;
    private JLabel turnLabel;
    private JLabel winnerLabel;
    private Game game;

    public GameplayPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.RED, 5));

        buttonsPanel = getButtonsPanel();
        add(buttonsPanel);

        containerPane = new JLayeredPane();
        containerPane.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
        containerPane.setPreferredSize(new Dimension(300, 300));
        containerPane.setLayout(new StackLayout());
        add(containerPane);
    }

    public void setGame(Game game) {
        this.game = game;
        boardPanel = getBoardPanel(game);
        arrowsPanel = getArrowsPanel();
        containerPane.add(boardPanel, JLayeredPane.DEFAULT_LAYER);
        containerPane.add(arrowsPanel, JLayeredPane.PALETTE_LAYER);
        variantNameLabel.setText(game.getName());
        turnLabel.setText(game.getTurn().name());
    }

    public void removeGame() {
        containerPane.remove(boardPanel);
        containerPane.remove(arrowsPanel);
        boardPanel = null;
        arrowsPanel = null;
        game = null;
        variantNameLabel.setText("");
        turnLabel.setText("");
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
        confirmButton.setFont(new Font("Dialog", Font.BOLD, 18));
        confirmButton.addActionListener(_ -> {
            if(!game.getSequence().isEmpty()) {
                squareGroup.clearSelection();
                game.performMove();
                arrowsPanel.repaint();
                if(game.isFinished()) {
                    winnerLabel.setText(game.getWinner() == org.example.model.Color.BLACK ? "Black won" : "White won");
                }
                setClickableSquares();
                turnLabel.setText(game.getTurn().name());
            }
        });

        quitButton = new JButton("Quit button");
        quitButton.setFont(new Font("Dialog", Font.BOLD, 18));

        variantNameLabel = new JLabel();
        variantNameLabel.setFont(new Font("Dialog", Font.BOLD, 18));

        turnLabel = new JLabel();
        turnLabel.setFont(new Font("Dialog", Font.BOLD, 18));


        winnerLabel = new JLabel();
        winnerLabel.setFont(new Font("Dialog", Font.BOLD, 18));

        buttonsPanel.add(confirmButton);
        buttonsPanel.add(quitButton);
        buttonsPanel.add(variantNameLabel);
        buttonsPanel.add(turnLabel);
        buttonsPanel.add(winnerLabel);
        buttonsPanel.add(Box.createVerticalGlue());

        return buttonsPanel;
    }

    private JPanel getArrowsPanel() {
        JPanel arrowsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(255, 127, 0));
                for(Move move: game.getSequence().getMoves()) {
                    paintArrow(g2, move);
                }
            }

            private void paintArrow(Graphics2D g, Move move) {
                Rectangle fromSquare = squareButtons[move.from().row()][move.from().col()].getBounds();
                Rectangle toSquare = squareButtons[move.to().row()][move.to().col()].getBounds();
                int fromX = fromSquare.x + fromSquare.width / 2;
                int fromY = fromSquare.y + fromSquare.height / 2;
                int toX = toSquare.x + toSquare.width / 2;
                int toY = toSquare.y + toSquare.height / 2;
                int deltaX = toX - fromX;
                int deltaY = toY - fromY;
                int adjustRectangle = fromSquare.width / 3;
                int adjustTriangle = fromSquare.width / 2;
                int dirX = 0;
                int dirY = 0;

                if(deltaX > 0 && deltaY > 0) {
                    dirX = 1;
                    dirY = 1;
                } else if(deltaX < 0 && deltaY > 0) {
                    dirX = -1;
                    dirY = 1;
                } else if(deltaX > 0 && deltaY < 0) {
                    dirX = 1;
                    dirY = -1;
                } else if(deltaX < 0 && deltaY < 0) {
                    dirX = -1;
                    dirY = -1;
                }
                {
                    int[] xPoints = {fromX + adjustRectangle * dirX, toX - adjustRectangle / 2 * dirX,
                            toX - adjustRectangle * dirX, fromX + adjustRectangle / 2 * dirX};
                    int[] yPoints = {fromY + adjustRectangle / 2 * dirY, toY - adjustRectangle * dirY,
                            toY - adjustRectangle / 2 * dirY, fromY + adjustRectangle * dirY};
                    g.fillPolygon(xPoints, yPoints, 4);
                }
                {
                    int[] xPoints = {toX - adjustTriangle / 4 * dirX, toX, toX - adjustTriangle * dirX};
                    int[] yPoints = {toY - adjustTriangle * dirY, toY, toY - adjustTriangle / 4 * dirY};
                    g.fillPolygon(xPoints, yPoints, 3);
                }
            }
        };
        arrowsPanel.setOpaque(false);

        return arrowsPanel;
    }

    private JPanel getBoardPanel(Game game) {
        int boardSize = game.getBoard().getSize();
        JPanel boardPanel = new JPanel(new BoardLayout(boardSize, boardSize));
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
        addSquareButtons(boardPanel, game);
        setClickableSquares();

        return boardPanel;
    }

    public JRadioButton getSquareButton(int row, int col) {
        return squareButtons[row][col];
    }

    public JRadioButton getSquareButton(Position position) {
        return squareButtons[position.row()][position.col()];
    }

    private void addSquareButtons(JPanel boardPanel, Game game) {
        int boardSize = game.getBoard().getSize();
        squareButtons = new JRadioButton[boardSize][boardSize];
        squareGroup = new ButtonGroup();
        clickableButtons = new ArrayList<>();
        for(int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                SquareButton squareButton = new SquareButton(row, col);
                SquareButtonUI ui = new SquareButtonUI();
                ui.setSquare(game.getBoard().at(row, col));
                squareButton.setUI(ui);
                squareButton.setBorderPainted(false);
                squareButton.setRolloverEnabled(false);
                squareButton.addItemListener(new SquareButtonItemListener(game, boardPanel, row, col));
                boardPanel.add(squareButton);
                squareGroup.add(squareButton);
                squareButtons[row][col] = squareButton;
            }
        }
    }

    private void setClickableSquares() {
        for(int row = 0; row < game.getBoard().getSize(); row++) {
            for(int col = 0; col < game.getBoard().getSize(); col++) {
                Square square = game.getBoard().at(row, col);
                if (!square.hasPiece() || square.getPiece().getColor() != game.getTurn()
                        || game.getLegalMoves(row, col).isEmpty()) {
                    squareButtons[row][col].setEnabled(false);
                } else {
                    squareButtons[row][col].setEnabled(true);
                    clickableButtons.add(squareButtons[row][col]);
                }
            }
        }
    }

    private void updateClickableSquares(int row, int col) {
        for(JRadioButton squareButton: clickableButtons) {
            squareButton.setEnabled(false);
        }
        clickableButtons.clear();
        if(game.isFinished()) {
            return;
        }
        clickableButtons.add(squareButtons[row][col]);
        for(Move move: game.getLegalMoves(row, col)) {
            Position position = move.to();
            clickableButtons.add(squareButtons[position.row()][position.col()]);
        }
        for(JRadioButton button : clickableButtons) {
            button.setEnabled(true);
        }
    }

    static class SquareButton extends JRadioButton {
        private final int row;
        private final int col;

        public SquareButton(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }

    static class SquareButtonUI extends BasicRadioButtonUI {
        private Square square;

        public void setSquare(Square square) {
            this.square = square;
        }

        public Square getSquare() {
            return square;
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

        private void paintKing(Graphics2D g2, Dimension size) {
            Piece piece = square.getPiece();
            Color pieceColor = piece.getColor() == org.example.model.Color.BLACK ? Color.BLACK : Color.WHITE;
            Color pieceBorderColor = piece.getColor() == org.example.model.Color.BLACK ? Color.WHITE : Color.BLACK;

            int borderThickness = (size.height + 5) / 20;

            g2.setColor(pieceBorderColor);
            g2.fillArc(0, size.height * 4 / 8, size.width, size.height / 2, 0, -180);
            g2.setColor(pieceColor);
            g2.fillArc(borderThickness, size.height * 4 / 8 + borderThickness,
                    size.width -  2 * borderThickness, size.height / 2 - 2 * borderThickness,
                    0, -180);

            g2.setColor(pieceBorderColor);
            g2.fillRect(0, size.height * 4 / 8, size.width, size.height / 4 + 1);
            g2.setColor(pieceColor);
            g2.fillRect(borderThickness, size.height * 4 / 8, size.width - 2 * borderThickness,
                    size.height / 4 + 2);

            g2.setColor(pieceBorderColor);
            g2.fillArc(0, size.height * 2 / 8, size.width, size.height / 2, 0, -180);
            g2.setColor(pieceColor);
            g2.fillArc(borderThickness, size.height * 2 / 8 + borderThickness,
                    size.width -  2 * borderThickness, size.height / 2 - 2 * borderThickness,
                    0, -180);

            g2.setColor(pieceBorderColor);
            g2.fillRect(0, size.height * 2 / 8, size.width, size.height / 4 + 1);
            g2.setColor(pieceColor);
            g2.fillRect(borderThickness, size.height * 2 / 8, size.width - 2 * borderThickness,
                    size.height / 4 + 2);

            g2.setColor(pieceBorderColor);
            g2.fillOval(0, 0, size.width, size.height / 2);
            g2.setColor(pieceColor);
            g2.fillOval(borderThickness, borderThickness,
                    size.width - 2 * borderThickness, size.height / 2 - 2 * borderThickness);
        }

        private void paintClickableIndicator(Graphics2D g2, Dimension size) {
            g2.setColor(Color.GREEN);
            int indicatorWidth = size.width / 6;
            int indicatorHeight = size.height / 6;
            g2.fillOval(size.width / 2 - indicatorWidth / 2, size.height / 2 - indicatorHeight / 2, indicatorWidth, indicatorHeight);
        }

        private void paint(Graphics g, Dimension size, ButtonModel model) {
            Color squareColor;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if(model.isSelected()) {
                squareColor = Color.GREEN;
            } else if(getSquare().getColor() == org.example.model.Color.BLACK) {
                squareColor = new Color(139, 69, 19);
            } else {
                squareColor = new Color(255, 228, 196);
            }
            g2.setColor(squareColor);
            g2.fillRect(0, 0, size.width, size.height);

            if(square.hasPiece()) {
                if(square.getPiece().getType() == Type.MAN) {
                    paintMan(g2, size);
                } else {
                    paintKing(g2, size);
                }
            }

            if(model.isEnabled()) {
                paintClickableIndicator(g2, size);
            }
        }

        @Override
        public synchronized void paint(Graphics g, JComponent c) {
            super.paint(g, c);
            AbstractButton b = (AbstractButton) c;
            ButtonModel model = b.getModel();
            Dimension size = c.getSize();
            paint(g, size, model);
        }
    }

    private class SquareButtonItemListener implements ItemListener {
        private final Game game;
        private final JPanel boardPanel;
        private final int row;
        private final int col;

        public SquareButtonItemListener(Game game, JPanel boardPanel, int row, int col) {
            this.game = game;
            this.boardPanel = boardPanel;
            this.row = row;
            this.col = col;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange() == ItemEvent.SELECTED) {
//                System.out.println(row + " " + col);
                game.getSequence().add(row, col);
                updateClickableSquares(row, col);
                boardPanel.repaint();
            }
        }
    }
}