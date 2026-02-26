package org.example.ui;

import org.example.model.Board;
import org.example.model.Square;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
                squareViews[row][col].setPosition(row * squareSize, col * squareSize);
                squareViews[row][col].setSize(squareSize);
                add(squareViews[row][col].jButton);
            }
        }
    }

    public void setSize(int size) {
        this.size = size;
        int boardSize = board.getSize();
        int squareSize = size / boardSize;
        for(int row = 0; row < boardSize; row++) {
            for(int col = 0; col < boardSize; col++) {
                squareViews[row][col].setSize(squareSize);
            }
        }
        System.out.println(size);
        revalidate();
        repaint();
    }

    static class SquareButtonUI extends BasicButtonUI {
        private Color selectColor;

        public void setSelectColor(Color selectColor) {
            this.selectColor = selectColor;
        }

        public Color getSelectColor() {
            return selectColor;
        }

        @Override
        protected void paintButtonPressed(Graphics g, AbstractButton b) {
            super.paintButtonPressed(g, b);
            if(b.isContentAreaFilled()) {
                Dimension size = b.getSize();
                g.setColor(getSelectColor());
                g.fillRect(0, 0, size.width, size.height);
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
            Color color;
            if(square.getColor() == Square.Color.BLACK) {
                color = Color.BLACK;
            } else {
                color = Color.WHITE;
            }
            jButton = new JButton();
            SquareButtonUI ui = new SquareButtonUI();
            ui.setSelectColor(color);
            jButton.setUI(ui);
            jButton.setBackground(color);
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

        public void setSize(int size) {
            this.size = size;
            jButton.revalidate();
            jButton.repaint();
        }

        public void setPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
