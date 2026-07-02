package checkers.model;

public class Square {

    private final Color color;
    private Piece piece;
    private final Board board;

    Square(Color color, Board board) {
        this(color, null, board);
    }

    Square(Color color, Piece piece, Board board) {
        this.color = color;
        this.piece = piece;
        this.board = board;
    }

    public Color getColor() {
        return color;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece removePiece() {
        if (board != null && piece != null) {
            board.getPieceCount().decrement(piece.getColor());
        }
        Piece temp = piece;
        piece = null;
        return temp;
    }

    public void placePiece(Piece piece) {
        this.piece = piece;
        if (board != null && piece != null) {
            board.getPieceCount().increment(piece.getColor());
        }
    }
}
