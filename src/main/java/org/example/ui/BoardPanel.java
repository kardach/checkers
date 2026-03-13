package org.example.ui;

import org.example.model.Board;
import org.example.model.Piece;
import org.example.model.Square;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class BoardPanel extends JPanel {
    public BoardPanel(Board board, int size) {
        int boardSize = board.getSize();

        setBounds(200, 0, size, size);
        setLayout(new GridLayout(boardSize, boardSize));

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
                    private int row;
                    private int col;

                    private MouseListener init(int row, int col) {
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
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                }.init(row, col));
                add(jButton);
            }
        }
    }

    static class SquareButtonUI extends BasicButtonUI {
        private Square square;

        public void setSquare(Square square) {
            this.square = square;
        }

        public Square getSquare() {
            return square;
        }

        private void paint(Graphics g, Dimension size) {
            Color squareColor = getSquare().getColor() == Square.Color.BLACK ? new Color(139, 69, 19)
                    : new Color(255, 228, 196);

            g.setColor(squareColor);
            g.fillRect(0, 0, size.width, size.height);

            if(square.hasPiece()) {
                Piece piece = square.getPiece();
                Color pieceColor = piece.getColor() == Piece.Color.BLACK ? Color.BLACK : Color.WHITE;

                g.setColor(pieceColor);
                g.fillOval(0, 0, size.width, size.height);
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
