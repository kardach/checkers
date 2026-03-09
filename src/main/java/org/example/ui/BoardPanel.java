package org.example.ui;

import org.example.model.Board;
import org.example.model.Piece;
import org.example.model.Square;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Optional;

class BoardPanel extends JPanel {
    private Board board;
    private int size;
    private SquareView[][] squareViews;

    public BoardPanel(Board board, int size) {
        this.board = board;
        int boardSize = board.getSize();
        int squareSize = size / boardSize;

        setBounds(200, 0, size, size);
        setLayout(new GridLayout(0, 10));

        squareViews = new SquareView[boardSize][boardSize];
        for(int row = 0; row < boardSize; row++) {
            for(int col = 0; col < boardSize; col++) {
                squareViews[row][col] = new SquareView(board.at(row, col));
                add(squareViews[row][col].jButton);
            }
        }
    }

    static class SquareButtonUI extends BasicButtonUI {
        private Square square;
        private Optional<Piece> piece;

        public void setSquare(Square square) {
            this.square = square;
        }

        public Square getSquare() {
            return square;
        }

        public void setPiece(Optional<Piece> piece) {
            this.piece = piece;
        }

        public Optional<Piece> getPiece() {
            return piece;
        }

        private void paint(Graphics g, Dimension size) {
            Color squareColor;

            if(getSquare().getColor() == Square.Color.BLACK) {
                squareColor = Color.BLACK;
            } else {
                squareColor = Color.WHITE;
            }

            g.setColor(squareColor);
            g.fillRect(0, 0, size.width, size.height);

            if(getPiece().isPresent()) {
                Piece piece = getPiece().get();
                Color pieceColor;

                if(piece.getColor() == Piece.Color.BLACK) {
                    pieceColor = Color.BLACK;
                } else {
                    pieceColor = Color.WHITE;
                }

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

    static class SquareView {
        private int size;
        private int x;
        private int y;
        private final JButton jButton;
        private Square square;

        public SquareView(Square square) {
            this.square = square;
            jButton = new JButton();
            SquareButtonUI ui = new SquareButtonUI();
            ui.setSquare(square);
            ui.setPiece(square.getPiece());
            jButton.setUI(ui);
            jButton.setBorderPainted(false);
            jButton.setMargin(new Insets(0, 0, 0, 0));
            jButton.setRolloverEnabled(false);
            jButton.setBounds(x, y, size, size);
            jButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

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
}
