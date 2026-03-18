package org.example.options;

public record Variant(String name, int boardSize, int piecesPerSide, boolean lightSquareOnNearRight,
                      PiecesPlacement piecesPlacement, FirstMove firstMove, boolean flyingKings,
                      boolean menCaptureBackwards, Capture capture) {
    public enum PiecesPlacement {
        ON_WHITE,
        ON_BLACK,
    }

    public enum FirstMove {
        BLACK,
        WHITE,
    }

    public enum Capture {
        MAXIMUM,
        ALL,
    }
}
