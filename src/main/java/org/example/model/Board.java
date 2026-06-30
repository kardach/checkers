package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int size;
    private final Square[][] squares;
    private final boolean lightSquareOnNearRight;
    private List<PiecePlacement> piecePlacements;
    private int piecesPerSide;
    private Placement placement;

    public enum Placement {
        ON_BLACK,
        ON_WHITE,
    }

    private Board(int size, boolean lightSquareOnNearRight) {
        this.size = size;
        this.lightSquareOnNearRight = lightSquareOnNearRight;
        squares = new Square[size][size];
        Color nearRightSquareColor = lightSquareOnNearRight ? Color.WHITE : Color.BLACK;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                squares[row][col] = new Square((row + col) % 2 == 0 ? nearRightSquareColor : nearRightSquareColor.opposite());
            }
        }
    }

    public Board(int size, boolean lightSquareOnNearRight, List<PiecePlacement> piecePlacements) {
        this(size, lightSquareOnNearRight);
        placePieces(piecePlacements);
    }

    public Board(int size, boolean lightSquareOnNearRight, int piecesPerSide, Placement placement) {
        this(size, lightSquareOnNearRight);
        placePieces(generatePiecePlacements(piecesPerSide, placement));
    }

    protected final void placePieces(List<PiecePlacement> piecePlacements) {
        this.piecePlacements = piecePlacements;

        for(PiecePlacement piecePlacement : piecePlacements) {
            at(piecePlacement.position()).placePiece(new Piece(piecePlacement.color(), piecePlacement.type()));
        }
    }

    protected final List<PiecePlacement> generatePiecePlacements(int piecesPerSide, Placement placement) {
        List<PiecePlacement> generatedPiecePlacements = new ArrayList<>();
        int row = 0;
        int col = placement == Placement.ON_BLACK && lightSquareOnNearRight
                || placement == Placement.ON_WHITE && !lightSquareOnNearRight ? 1 : 0;
        for(int i = 0; i < piecesPerSide; i++) {
            generatedPiecePlacements.add(new PiecePlacement(Color.WHITE, Type.MAN, new Position(row, col)));
            generatedPiecePlacements.add(new PiecePlacement(Color.BLACK, Type.MAN, new Position(size - row - 1, size - col -1)));

            col += 2;
            if(col >= size) {
                row += 1;
                col = (col + 1) % 2;
            }
        }
        return generatedPiecePlacements;
    }

    public void reset() {
        clear();
        placePieces(piecePlacements);
    }

    protected final void clear() {
        for(int row = 0; row < size; row++) {
            for(int col = 0; col < size; col++) {
                squares[row][col].removePiece();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Square at(int row, int col) {
        return squares[row][col];
    }

    public Square at(Position position) {
        return squares[position.row()][position.col()];
    }
}
