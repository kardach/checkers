package org.example.ui;

import org.example.model.Board;
import org.example.model.Move;
import org.example.model.Piece;
import org.example.model.Square;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class BoardPanel extends JPanel {
    private JButton selectedSquare;

    public BoardPanel(Board board, Move move, int size) {
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

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                }.init(jButton, move, row, col));
                add(jButton);
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

        private void paint(Graphics g, Dimension size) {
            Color squareColor = getSquare().getColor() == Square.Color.BLACK ? new Color(139, 69, 19)
                    : new Color(255, 228, 196);
            if(selected) {
                squareColor = Color.GREEN;
            }
            g.setColor(squareColor);
            g.fillRect(0, 0, size.width, size.height);

            if(square.hasPiece()) {
                Piece piece = square.getPiece();
                Color pieceColor = piece.getColor() == Piece.Color.BLACK ? Color.BLACK : Color.WHITE;

                g.setColor(pieceColor);
                g.fillOval(0, 0, size.width, size.height);
            }

//            if(selected) {
//                Graphics2D g2 = (Graphics2D) g;
//                g2.rotate(Math.toRadians(45));
//                g2.setColor(new Color(255, 0, 0, 128));
//                g2.fillRect(0, 0, 100, 10);
//            }
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
