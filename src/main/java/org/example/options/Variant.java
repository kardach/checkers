package org.example.options;

public record Variant(String name, int boardSize, int piecesPerSide, boolean lightSquareOnNearRight,
                      PiecesPlacement piecesPlacement, FirstMove firstMove, boolean flyingKings,
                      boolean menCaptureBackwards, Capture capture, Crowning crowning) {
    public enum PiecesPlacement {
        ON_BLACK,
        ON_WHITE,
    }

    public enum FirstMove {
        BLACK,
        WHITE,
    }

    public enum Capture {
        MAXIMUM,
        ALL,
    }

    public enum Crowning {
        TERMINATE,
        ON_FLY,
        ON_FINISH,
    }
}
