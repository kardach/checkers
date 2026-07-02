package checkers.model;

import checkers.support.ReplaceCamelCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(ReplaceCamelCase.class)
public class SequenceTest {

    public static final Position FIRST_POSITION = new Position(0, 0);
    public static final Position SECOND_POSITION = new Position(1, 1);
    private static final Position THIRD_POSITION = new Position(2, 2);
    private static final Move FIRST_MOVE = new Move(FIRST_POSITION, SECOND_POSITION);
    private static final Move SECOND_MOVE = new Move(SECOND_POSITION, THIRD_POSITION);
    private static final Move FIRST_MOVE_REVERSED = new Move(SECOND_POSITION, FIRST_POSITION);
    private static final Move SECOND_MOVE_REVERSED = new Move(THIRD_POSITION, SECOND_POSITION);
    private Sequence sequence;

    @BeforeEach
    void init() {
        sequence = new Sequence();
    }

    @Test
    void clearWorksCorrectly() {
        sequence.add(FIRST_POSITION);
        sequence.add(SECOND_POSITION);
        sequence.clear();
        assertFalse(sequence.isStarted());
        assertTrue(sequence.isEmpty());
    }

    @Test
    void noPositions() {
        assertFalse(sequence.isStarted());
        assertTrue(sequence.isEmpty());
    }

    @Test
    void addFirstPosition() {
        sequence.add(FIRST_POSITION);
        assertEquals(FIRST_POSITION, sequence.getStart());
        assertTrue(sequence.isStarted());
        assertTrue(sequence.isEmpty());
    }

    @Test
    void addFirstAndSecondPosition() {
        sequence.add(FIRST_POSITION);
        sequence.add(SECOND_POSITION);
        assertTrue(sequence.isStarted());
        assertFalse(sequence.isEmpty());
        assertTrue(sequence.contains(FIRST_MOVE));
    }

    @Test
    void addFirstSecondAndThirdPosition() {
        sequence.add(FIRST_POSITION);
        sequence.add(SECOND_POSITION);
        sequence.add(THIRD_POSITION);
        assertTrue(sequence.isStarted());
        assertFalse(sequence.isEmpty());
        assertTrue(sequence.contains(FIRST_MOVE));
        assertTrue(sequence.contains(SECOND_MOVE));
    }

    @Test
    void backtrackFirstPosition() {
        sequence.add(FIRST_POSITION);
        sequence.add(FIRST_POSITION);
        assertTrue(sequence.isStarted());
        assertTrue(sequence.isEmpty());
    }

    @Test
    void backtrackSecondPosition() {
        sequence.add(FIRST_POSITION);
        sequence.add(SECOND_POSITION);
        sequence.add(FIRST_POSITION);
        assertTrue(sequence.isStarted());
        assertTrue(sequence.isEmpty());
        assertFalse(sequence.contains(FIRST_MOVE));
        assertFalse(sequence.contains(FIRST_MOVE_REVERSED));
    }

    @Test
    void backtrackThirdPosition() {
        sequence.add(FIRST_POSITION);
        sequence.add(SECOND_POSITION);
        sequence.add(THIRD_POSITION);
        sequence.add(SECOND_POSITION);
        assertTrue(sequence.isStarted());
        assertFalse(sequence.isEmpty());
        assertTrue(sequence.contains(FIRST_MOVE));
        assertFalse(sequence.contains(SECOND_MOVE));
        assertFalse(sequence.contains(SECOND_MOVE_REVERSED));
    }
}
