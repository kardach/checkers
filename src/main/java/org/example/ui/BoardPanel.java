package org.example.ui;

import org.example.model.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

class BoardPanel extends JPanel {
    private JButton selectedSquare;
    private final Move move;
    private final JButton[][] jButtons;

    public BoardPanel(Game game, int size) {
        this.move = game.getMove();
        Board board = game.getBoard();
        int boardSize = board.getSize();

        setBounds(200, 0, size, size);
        setLayout(new GridLayout(boardSize, boardSize));

        jButtons = new JButton[boardSize][boardSize];
        for(int row = 0; row < boardSize; row++) {
            for(int col = 0; col < boardSize; col++) {
                JButton jButton = new JButton();
                SquareButtonUI ui = new SquareButtonUI();
                ui.setSquare(board.at(row, col));
                jButton.setUI(ui);
                jButton.setBorderPainted(false);
                jButton.setMargin(new Insets(0, 0, 0, 0));
                jButton.setRolloverEnabled(false);
                jButton.addMouseListener(new MouseListener() {
                    private JButton jButton;
                    private Move move;
                    private int row;
                    private int col;

                    private MouseListener init(JButton jButton, Move move, int row, int col) {
                        this.jButton = jButton;
                        this.move = move;
                        this.row = row;
                        this.col = col;
                        return this;
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        System.out.println("row = " + row +", col = " + col);

                        if(game.validateSubMove(row, col)) {
                            move.add(row, col);

                            if(selectedSquare != null) {
                                SquareButtonUI ui = (SquareButtonUI) selectedSquare.getUI();
                                ui.setSelected(false);
                                selectedSquare.revalidate();
                                selectedSquare.repaint();
                            }

                            selectedSquare = jButton;
                            SquareButtonUI ui = (SquareButtonUI) selectedSquare.getUI();
                            ui.setSelected(true);
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                }.init(jButton, move, row, col));
                add(jButton);
                jButtons[row][col] = jButton;
            }
        }
    }

    public void clearSelection() {
        if(selectedSquare != null) {
            SquareButtonUI ui = (SquareButtonUI) selectedSquare.getUI();
            ui.setSelected(false);
            selectedSquare.revalidate();
            selectedSquare.repaint();
            selectedSquare = null;
        }
    }

    public void paintArrow(Graphics2D g, Move.SubMove subMove) {
        Rectangle fromSquare = jButtons[subMove.from().row()][subMove.from().col()].getBounds();
        Rectangle toSquare = jButtons[subMove.to().row()][subMove.to().col()].getBounds();
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(!move.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 127, 0));
            List<Move.SubMove> subMoves = move.getSubMoves();
            for(Move.SubMove subMove : subMoves) {
                paintArrow(g2, subMove);
            }
        }
    }

    class SquareButtonUI extends BasicButtonUI {
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

            BoardPanel.this.revalidate();
            BoardPanel.this.repaint();
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
