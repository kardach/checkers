package checkers.model;

import java.util.*;
import java.util.function.Function;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class Board {

    private final int size;
    private final Square[][] squares;
    private final List<PiecePlacement> initialPiecePositions;
    private final Map<Piece, Position> currentPiecePositions;
    private final PieceCount pieceCount;
    private final boolean backCaptureAllowed;
    private final boolean flyingKingsAllowed;

    public Board(int size, Color nearRightSquareColor, List<PiecePlacement> initialPiecePositions, boolean backCaptureAllowed, boolean flyingKingsAllowed) {
        this.size = size;
        this.initialPiecePositions = initialPiecePositions;
        this.pieceCount = new PieceCount();
        this.currentPiecePositions = new HashMap<>();
        this.backCaptureAllowed = backCaptureAllowed;
        this.flyingKingsAllowed = flyingKingsAllowed;
        squares = new Square[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                squares[row][col] = new Square(new Position(row, col), (row + col) % 2 == 0
                        ? nearRightSquareColor
                        : nearRightSquareColor.opposite()
                );
            }
        }
        placePieces();
    }

    public Board(int size, Color nearRightSquareColor, int piecesPerSide, Placement placement, boolean backCaptureAllowed, boolean flyingKingsAllowed) {
        this(size, nearRightSquareColor, List.copyOf(generatePiecePlacements(size, nearRightSquareColor, piecesPerSide, placement)), backCaptureAllowed, flyingKingsAllowed);
        placePieces();
    }

    private static List<PiecePlacement> generatePiecePlacements(int size, Color nearRightSquareColor, int piecesPerSide, Placement placement) {
        List<PiecePlacement> generatedPiecePlacements = new ArrayList<>();
        int row = 0;
        int col = placement == Placement.ON_BLACK && nearRightSquareColor == Color.WHITE
                || placement == Placement.ON_WHITE && nearRightSquareColor == Color.BLACK ? 1 : 0;
        for (int i = 0; i < piecesPerSide; i++) {
            generatedPiecePlacements.add(
                    new PiecePlacement(Color.WHITE, Type.MAN, new Position(row, col))
            );
            generatedPiecePlacements.add(
                    new PiecePlacement(Color.BLACK, Type.MAN, new Position(size - row - 1, size - col - 1))
            );

            col += 2;
            if (col >= size) {
                row += 1;
                col = (col + 1) % 2;
            }
        }
        return generatedPiecePlacements;
    }

    public int getSize() {
        return size;
    }

    public Map<Piece, Position> getCurrentPiecePositions() {
        return Map.copyOf(currentPiecePositions);
    }

    public PieceCount getPieceCount() {
        return pieceCount;
    }

    private void placePieces() {
        initialPiecePositions.forEach(piecePlacement -> at(piecePlacement.position()).placePiece(new Piece(piecePlacement.color(), piecePlacement.type(), backCaptureAllowed, flyingKingsAllowed)));
    }

    Square at(Position position) {
        if (!isInBounds(position)) {
            throw new IllegalArgumentException("Position out of bounds of board");
        }
        return squares[position.row()][position.col()];
    }

    public Position getPiecePosition(Piece piece) {
        return currentPiecePositions.get(piece);
    }

    public boolean isInBounds(Position position) {
        return position.row() >= 0 && position.row() < getSize() && position.col() >= 0 && position.col() < getSize();
    }

    private List<Position> search(Piece piece, Integer range, List<Direction> directions) {
        List<Position> positions = new ArrayList<>();
        Position position = getPiecePosition(piece);
        Position temp;
        for (Direction direction : directions) {
            int enemyCounter = 0;
            boolean encounteredAlly = false;
            for (int i = 1; i <= range - 1; i++) {
                temp = position.translate(direction, i);
                if (!isInBounds(temp)) {
                    break;
                }
                if (at(temp).hasPiece()) {
                    boolean areSameColor = at(temp).getPiece().getColor() == piece.getColor();
                    encounteredAlly |= areSameColor;
                    if (areSameColor) {
                        break;
                    } else {
                        enemyCounter++;
                    }
                    if (enemyCounter > 1) {
                        break;
                    }
                    continue;
                }
                positions.add(temp);
            }
            temp = position.translate(direction, range);
            if (isInBounds(temp) && !at(temp).hasPiece() && !encounteredAlly && enemyCounter == 1) {
                positions.add(temp);
            }
        }
        return positions;
    }

    private Function<Piece, List<Position>> search(int range, List<Direction> directions) {
        return piece -> search(piece, range, directions);
    }

    private void clear() {
        currentPiecePositions
                .values()
                .forEach(position -> at(position).removePiece());
    }

    public void reset() {
        clear();
        placePieces();
    }

    public class Square {

        private final Position position;
        private final Color color;
        @Nullable
        private Piece piece;

        private Square(Position position, Color color) {
            this.position = position;
            this.color = color;
            this.piece = null;
        }

        public Position getPosition() {
            return position;
        }

        public Color getColor() {
            return color;
        }

        public Piece getPiece() {
            if (piece == null) {
                throw new NoSuchElementException("There is no piece placed on this square");
            }
            return piece;
        }

        public boolean hasPiece() {
            return piece != null;
        }

        public Piece removePiece() {
            if (piece == null) {
                throw new NoSuchElementException("There is no piece placed on this square");
            }
            getPieceCount().decrement(piece.getColor(), piece.getType());
            currentPiecePositions.remove(piece);
            Piece temp = piece;
            piece = null;
            return temp;
        }

        public void placePiece(Piece piece) {
            if (this.piece != null) {
                throw new IllegalStateException("Piece is already placed on this square");
            }
            this.piece = piece;
            getPieceCount().increment(piece.getColor(), piece.getType());
            currentPiecePositions.put(piece, getPosition());
        }
    }

    public class Piece {

        private final Color color;
        private Type type;
        private final Function<Piece, List<Position>> manSearch;
        private final Function<Piece, List<Position>> kingSearch;
        private final static List<Direction> ALL_DIAGONAL = Direction.getDiagonal();
        private final static List<Direction> TOP_DIAGONAL = List.of(Direction.TOP_LEFT, Direction.TOP_RIGHT);
        private final static List<Direction> BOTTOM_DIAGONAL = List.of(Direction.BOTTOM_LEFT, Direction.BOTTOM_RIGHT);

        private Piece(Color color, Type type, boolean backCaptureAllowed, boolean flyingKingsAllowed) {
            this.color = color;
            this.type = type;
            if (backCaptureAllowed) {
                manSearch = search(2, ALL_DIAGONAL);
            } else {
                manSearch = color == Color.BLACK ? search(2, TOP_DIAGONAL) : search(2, BOTTOM_DIAGONAL);
            }
            kingSearch = flyingKingsAllowed ? search(getSize() + 1, ALL_DIAGONAL) : search(2, ALL_DIAGONAL);
        }

        public Type getType() {
            return type;
        }

        public Color getColor() {
            return color;
        }

        public void promote() {
            if (type == Type.KING) {
                throw new IllegalStateException("Cannot promote a king");
            }
            getPieceCount().decrement(getColor(), getType());
            type = Type.KING;
            getPieceCount().increment(getColor(), getType());
        }

        public void demote() {
            if (type == Type.MAN) {
                throw new IllegalStateException("Cannot demote a man");
            }
            getPieceCount().decrement(getColor(), getType());
            type = Type.MAN;
            getPieceCount().increment(getColor(), getType());
        }

        public List<Position> getValidPositions() {
            return getType() == Type.MAN
                    ? manSearch.apply(this)
                    : kingSearch.apply(this);
        }
    }

    public class PieceCount {

        private final int initialBlackMen;
        private final int initialBlackKings;
        private final int initialWhiteMen;
        private final int initialWhiteKings;
        private int blackMen;
        private int blackKings;
        private int whiteMen;
        private int whiteKings;

        private PieceCount() {
            int initialBlackMen = 0;
            int initialBlackKings = 0;
            int initialWhiteMen = 0;
            int initialWhiteKings = 0;
            for (PiecePlacement piecePlacement : Board.this.initialPiecePositions) {
                if (piecePlacement.color() == Color.BLACK) {
                    if (piecePlacement.type() == Type.MAN) {
                        initialBlackMen++;
                    } else {
                        initialBlackKings++;
                    }
                } else {
                    if (piecePlacement.type() == Type.MAN) {
                        initialWhiteMen++;
                    } else {
                        initialWhiteKings++;
                    }
                }
            }
            this.initialBlackMen = initialBlackMen;
            this.initialBlackKings = initialBlackKings;
            this.initialWhiteMen = initialWhiteMen;
            this.initialWhiteKings = initialWhiteKings;
        }

        public int numberOfPieces(Color color) {
            return color == Color.BLACK ? blackMen + blackKings : whiteMen + whiteKings;
        }

        public int numberOfPieces(Type type) {
            return type == Type.MAN ? blackMen + whiteMen : blackKings + whiteKings;
        }

        public int numberOfPieces(Color color, Type type) {
            if (color == Color.BLACK) {
                return type == Type.MAN ? blackMen : blackKings;
            } else {
                return type == Type.MAN ? whiteMen : whiteKings;
            }
        }

        private void updateCounter(Color color, Type type, int amount) {
            if (color == Color.BLACK) {
                if (type == Type.MAN) {
                    blackMen += amount;
                } else {
                    blackKings += amount;
                }
            } else {
                if (type == Type.MAN) {
                    whiteMen += amount;
                } else {
                    whiteKings += amount;
                }
            }
            if (blackMen < 0 || blackKings < 0 || whiteMen < 0 || whiteKings < 0) {
                throw new IllegalStateException("Piece count can't be less than 0");
            } else if (blackMen + blackKings > initialBlackMen + initialBlackKings || whiteMen + whiteKings > initialWhiteMen + initialWhiteKings) {
                throw new IllegalStateException("Piece count can't be more than initial");
            }
        }

        private void increment(Color color, Type type) {
            updateCounter(color, type, +1);
        }

        private void decrement(Color color, Type type) {
            updateCounter(color, type, -1);
        }
    }
}
