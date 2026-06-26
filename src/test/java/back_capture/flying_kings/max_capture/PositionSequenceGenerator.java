package back_capture.flying_kings.max_capture;

import org.example.model.Direction;
import org.example.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PositionSequenceGenerator {

    static class Node {
        Position position;
        List<Node> children;

        Node(Position position) {
            this.position = position;
            this.children = new ArrayList<>();
        }
    }

    private final Node tree;
    private List<Node> leaves = new ArrayList<>();

    public PositionSequenceGenerator(Position position) {
        tree = new Node(position);
        leaves.add(tree);
    }

    public PositionSequenceGenerator next(Position position) {
        leaves = leaves.stream()
                .flatMap(leave -> {
                    leave.children.add(new Node(position));
                    return leave.children.stream();
                })
                .toList();
        return this;
    }

    public PositionSequenceGenerator nextInDirection(Direction direction, int amount) {
        return nextInDirections(List.of(direction), amount);
    }

    public PositionSequenceGenerator nextInDirections(List<Direction> directions, int amount) {
        leaves = leaves.stream()
                .flatMap(leave -> {
                    for(Direction direction : directions) {
                        leave.children.add(new Node(leave.position.translate(direction, amount)));
                    }
                    return leave.children.stream();
                })
                .toList();
        return this;
    }

    public PositionSequenceGenerator nextDiagonalDirections(int amount) {
        return nextInDirections(Direction.getDiagonal(), amount);
    }

    public PositionSequenceGenerator nextOrthogonalDirections(int amount) {
        return  nextInDirections(Direction.getOrthogonal(), amount);
    }

    private final Stack<Position> path = new Stack<>();

    private final List<List<Position>> paths = new ArrayList<>();

    private void generate(Node node) {
        path.push(node.position);
        for(Node child : node.children) {
            generate(child);
        }
        if(node.children.isEmpty()) {
            paths.add(new ArrayList<>(path));
        }
        path.pop();
    }

    public List<List<Position>> generate() {
        generate(tree);
        return paths;
    }
}
