package org.example.generator;

import org.example.model.Color;
import org.example.model.PiecePlacement;
import org.example.model.Position;
import org.example.model.Type;

import java.util.ArrayList;
import java.util.List;

public  class PiecePlacementGenerator {

    private List<Position> alliedMenPositions = new ArrayList<>();
    private List<Position> alliedKingsPositions = new ArrayList<>();
    private List<Position> enemyMenPositions = new ArrayList<>();
    private List<Position> enemyKingsPositions = new ArrayList<>();
    private Color color;

    public void reset() {
        alliedMenPositions.clear();
        alliedKingsPositions.clear();
        enemyMenPositions.clear();
        enemyKingsPositions.clear();
        color = null;
    }

    public PiecePlacementGenerator set(List<Position> positions, Color color, Type type) {
        return type == Type.MAN ? setMen(positions, color) : setKings(positions, color);
    }

    public PiecePlacementGenerator setMen(List<Position> positions, Color color) {
        return this.color == color ? setAllyMen(positions) : setEnemyMen(positions);
    }

    public PiecePlacementGenerator setKings(List<Position> positions, Color color) {
        return this.color == color ? setAllyKings(positions) : setEnemyKings(positions);
    }

    public PiecePlacementGenerator setAllyMen(List<Position> positions) {
        alliedMenPositions = positions;
        return this;
    }

    public PiecePlacementGenerator setAllyKings(List<Position> positions) {
        alliedKingsPositions = positions;
        return this;
    }

    public PiecePlacementGenerator setEnemyMen(List<Position> positions) {
        enemyMenPositions = positions;
        return this;
    }

    public PiecePlacementGenerator setEnemyKings(List<Position> positions) {
        enemyKingsPositions = positions;
        return this;
    }

    public PiecePlacementGenerator forColor(Color color) {
        this.color = color;
        return this;
    }

    public ArrayList<PiecePlacement> generate() {
        ArrayList<PiecePlacement> customPiecePlacements = new ArrayList<>(List.of(
                new PiecePlacement(color, Type.KING, 3, 3)
        ));
        customPiecePlacements.addAll(alliedMenPositions
                .stream()
                .map(x -> new PiecePlacement(color, Type.MAN, x.row(), x.col()))
                .toList()
        );
        customPiecePlacements.addAll(alliedKingsPositions
                .stream()
                .map(x -> new PiecePlacement(color, Type.KING, x.row(), x.col()))
                .toList()
        );
        customPiecePlacements.addAll(enemyMenPositions
                .stream()
                .map(x -> new PiecePlacement(color.opposite(), Type.MAN, x.row(), x.col()))
                .toList()
        );
        customPiecePlacements.addAll(enemyKingsPositions
                .stream()
                .map(x -> new PiecePlacement(color.opposite(), Type.KING, x.row(), x.col()))
                .toList()
        );
        return customPiecePlacements;
    }
}
