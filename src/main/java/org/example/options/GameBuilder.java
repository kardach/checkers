package org.example.options;

import org.example.model.*;

public class GameBuilder {
    private String name;
    private Board board;
    private Color firstMove;
    private boolean menCaptureBackwards;
    private boolean flyingKings;
    private Capture capture;
    private Crowning crowning;

    public void setName(String name) {
        this.name = name;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setFirstMove(Color firstMove) {
        this.firstMove = firstMove;
    }

    public void setMenCaptureBackwards(boolean menCaptureBackwards) {
        this.menCaptureBackwards = menCaptureBackwards;
    }

    public void setFlyingKings(boolean flyingKings) {
        this.flyingKings = flyingKings;
    }

    public void setCapture(Capture capture) {
        this.capture = capture;
    }

    public void setCrowning(Crowning crowning) {
        this.crowning = crowning;
    }

    public Game build() {
        return new Game(name, board, firstMove, menCaptureBackwards, flyingKings, capture, crowning);
    }
}
