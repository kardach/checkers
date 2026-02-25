package org.example.ui;

import org.example.model.Board;
import org.example.model.Square;

import javax.swing.*;
import java.awt.*;

class BoardPanel extends JPanel {
    private Board board;
    private int size;
    private SquareView[][]  squareViews;

    public BoardPanel(Board board, int size) {
        this.board = board;
        int boardSize = board.getSize();
        int squareSize = size / boardSize;

        setBounds(200, 0, size, size);
        setLayout(new GridLayout(0, 10));
        SquareView.setSize(squareSize);

        squareViews = new SquareView[boardSize][boardSize];
        for(int row = 0; row < boardSize; row++) {
            for(int col = 0; col < boardSize; col++) {
                squareViews[row][col] = new SquareView(board.at(row, col));
                squareViews[row][col].setPosition(row * squareSize, col * squareSize);
                add(squareViews[row][col].jComponent);
            }
        }
    }

    public void setSize(int size) {
        this.size = size;
    }

    static class SquareView {
        private static int size;
        private int x;
        private int y;
        private final Color color;
        private final JComponent jComponent;
        private final JButton jButton;
        private Square square;

        public SquareView(Square square) {
            this.square = square;
            if(square.getColor() == Square.Color.BLACK) {
                color = Color.BLACK;
            } else {
                color = Color.WHITE;
            }
            jComponent = new JComponent() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(color);
                    g.fillRect(0, 0, size, size);
                }
            };
            jComponent.setBounds(x, y, size, size);
            jButton = new JButton(x+""+y);
            jButton.setBounds(x, y, size, size);
        }

        public static void setSize(int size) {
            SquareView.size = size;
        }

        public void setPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
