package org.example.options;

import org.example.model.*;

public class GameDirector {
    public void constructInternationalGameVariant(GameBuilder gameBuilder) {
        gameBuilder.setName("International/Polish");
        gameBuilder.setBoard(new Board(10, true, 20, PiecePlacement.ON_BLACK));
        gameBuilder.setFirstMove(Color.WHITE);
        gameBuilder.setMenCaptureBackwards(true);
        gameBuilder.setFlyingKings(true);
        gameBuilder.setCapture(Capture.MAX);
        gameBuilder.setCrowning(Crowning.ON_FINISH);
    }

    public void constructGhanaianGameVariant(GameBuilder gameBuilder) {
        gameBuilder.setName("Ghanaian/damii");
        gameBuilder.setBoard(new Board(10, false, 20, PiecePlacement.ON_WHITE));
        gameBuilder.setFirstMove(Color.WHITE);
        gameBuilder.setMenCaptureBackwards(true);
        gameBuilder.setFlyingKings(true);
        gameBuilder.setCapture(Capture.MAX);
        gameBuilder.setCrowning(Crowning.ON_FINISH);
    }
}
