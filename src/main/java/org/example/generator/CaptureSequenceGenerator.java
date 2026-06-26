package org.example.generator;

import org.example.model.Direction;
import org.example.model.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CaptureSequenceGenerator {
    private List<Position> alliedMenPositions = new ArrayList<>();
    private List<Position> alliedKingsPositions = new ArrayList<>();
    private List<Position> enemyMenPositions = new ArrayList<>();
    private List<Position> enemyKingsPositions = new ArrayList<>();
    private List<List<Position>> positionSequences;

    public CaptureSequenceGenerator(List<List<Position>> positionSequences) {
        this.positionSequences = positionSequences;
    }

    public void reset() {
        alliedMenPositions.clear();
        alliedKingsPositions.clear();
        enemyMenPositions.clear();
        enemyKingsPositions.clear();
    }

    public void setPositionSequences(List<List<Position>> positionSequences) {
        this.positionSequences = positionSequences;
    }

    public CaptureSequenceGenerator setAllyMen(List<Position> positions) {
        alliedMenPositions = positions;
        return this;
    }

    public CaptureSequenceGenerator setAllyKings(List<Position> positions) {
        alliedKingsPositions = positions;
        return this;
    }

    public CaptureSequenceGenerator setEnemyMen(List<Position> positions) {
        enemyMenPositions = positions;
        return this;
    }

    public CaptureSequenceGenerator setEnemyKings(List<Position> positions) {
        enemyKingsPositions = positions;
        return this;
    }

    private List<Position> generate(List<Position> positionSequence) {
        List<Position> jumpedOver = new ArrayList<>();
        Iterator<Position> iterator = positionSequence.iterator();
        if(iterator.hasNext()) {
            Position previous = iterator.next();
            while(iterator.hasNext()) {
                Position current = iterator.next();
                Direction direction = previous.getDirection(current);
                previous = previous.translate(direction, 1);
                while(previous.equals(current)) {
                    if(alliedMenPositions.contains(previous) || alliedKingsPositions.contains(previous)
                            || enemyMenPositions.contains(previous) || enemyKingsPositions.contains(previous)) {

                        jumpedOver.add(previous);
                    }
                    previous = previous.translate(direction, 1);
                }
                previous = current;
            }
        }
        return jumpedOver;
    }

    public List<List<Position>> generate() {
        List<List<Position>> capturedSequences = new ArrayList<>();
        for(List<Position> positionSequence : positionSequences) {
            capturedSequences.add(generate(positionSequence));
        }
        return capturedSequences;
    }

}