package back_capture.flying_kings.max_capture;

import org.example.model.Color;
import org.example.model.CustomPiecePlacement;
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

    public ArrayList<CustomPiecePlacement> generate() {
        ArrayList<CustomPiecePlacement> customPiecePlacements = new ArrayList<>(List.of(
                new CustomPiecePlacement(color, Type.KING, 3, 3)
        ));
        customPiecePlacements.addAll(alliedMenPositions
                .stream()
                .map(x -> new CustomPiecePlacement(color, Type.MAN, x.row(), x.col()))
                .toList()
        );
        customPiecePlacements.addAll(alliedKingsPositions
                .stream()
                .map(x -> new CustomPiecePlacement(color, Type.KING, x.row(), x.col()))
                .toList()
        );
        customPiecePlacements.addAll(enemyMenPositions
                .stream()
                .map(x -> new CustomPiecePlacement(color.opposite(), Type.MAN, x.row(), x.col()))
                .toList()
        );
        customPiecePlacements.addAll(enemyKingsPositions
                .stream()
                .map(x -> new CustomPiecePlacement(color.opposite(), Type.KING, x.row(), x.col()))
                .toList()
        );
        return customPiecePlacements;
    }
}
