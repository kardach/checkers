package org.example.options;

public record Variant(String name, int boardSize, int piecesPerSide, boolean lightSquareOnNearRight, PiecesPlacement piecesPlacement,
                      FirstMove firstMove) {
    public enum PiecesPlacement {
        ON_WHITE,
        ON_BLACK,
    }

    public enum FirstMove {
        BLACK,
        WHITE,
    }

}
