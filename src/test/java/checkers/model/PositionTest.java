package checkers.model;

import checkers.support.CustomNameGenerator;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@NullMarked
@DisplayNameGeneration(CustomNameGenerator.class)
public class PositionTest {

    final Position position = new Position(0, 0);

    @Test
    void getDirectionsShouldReturnCorrectly() {
        assertEquals(Direction.TOP, position.getDirection(new Position(-1, 0)));
        assertEquals(Direction.TOP_RIGHT, position.getDirection(new Position(-1, 1)));
        assertEquals(Direction.RIGHT, position.getDirection(new Position(0, 1)));
        assertEquals(Direction.BOTTOM_RIGHT, position.getDirection(new Position(1, 1)));
        assertEquals(Direction.BOTTOM, position.getDirection(new Position(1, 0)));
        assertEquals(Direction.BOTTOM_LEFT, position.getDirection(new Position(1, -1)));
        assertEquals(Direction.LEFT, position.getDirection(new Position(0, -1)));
        assertEquals(Direction.TOP_LEFT, position.getDirection(new Position(-1, -1)));
        assertEquals(Direction.NONE, position.getDirection(new Position(0, 0)));
    }

    @Test
    void translateShouldReturnCorrectly() {
        assertEquals(new Position(-1, 0), position.translate(Direction.TOP, 1));
        assertEquals(new Position(-1, 1), position.translate(Direction.TOP_RIGHT, 1));
        assertEquals(new Position(0, 1), position.translate(Direction.RIGHT, 1));
        assertEquals(new Position(1, 1), position.translate(Direction.BOTTOM_RIGHT, 1));
        assertEquals(new Position(1, 0), position.translate(Direction.BOTTOM, 1));
        assertEquals(new Position(1, -1), position.translate(Direction.BOTTOM_LEFT, 1));
        assertEquals(new Position(0, -1), position.translate(Direction.LEFT, 1));
        assertEquals(new Position(-1, -1), position.translate(Direction.TOP_LEFT, 1));
        assertEquals(position, position.translate(Direction.NONE, 1));
    }
}
