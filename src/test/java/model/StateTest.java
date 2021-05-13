package model;

import model.Board;
import model.Direction;
import model.Position;
import model.State;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class StateTest {
    State state1=new State();       //Original

    State state2=new State(new Board(),new Position(5,2));  //goal

    State state3=new State(new Board(),new Position(6,6)); //non-goal

    State state4=new State(new Board(new int[][]{{4,4},{5,4}},
            new int[][]{{4,4},{4,3}}),new Position(4,4));//dead-end

    @Test
    void testConstructor_invalid() {
        assertThrows(IllegalArgumentException.class, () -> new State(new Board(),new Position(10, 7)));
    }

    @Test
    void isGoal() {
        state1.isGoal();
        state2.isGoal();
        state3.isGoal();
        state4.isGoal();
        assertFalse(state1.getGoal());
        assertTrue(state2.getGoal());
        assertFalse(state3.getGoal());
        assertFalse(state4.getGoal());
    }

    @Test
    void canMove_state1() {
        assertTrue(state1.canMove(Direction.UP));
        assertTrue(state1.canMove(Direction.RIGHT));
        assertTrue(state1.canMove(Direction.DOWN));
        assertTrue(state1.canMove(Direction.LEFT));
    }
    @Test
    void canMove_state2() {
        assertTrue(state2.canMove(Direction.UP));
        assertFalse(state2.canMove(Direction.RIGHT));
        assertFalse(state2.canMove(Direction.DOWN));
        assertFalse(state2.canMove(Direction.LEFT));
    }

    @Test
    void canMove_state3() {
        assertTrue(state3.canMove(Direction.UP));
        assertFalse(state3.canMove(Direction.RIGHT));
        assertFalse(state3.canMove(Direction.DOWN));
        assertFalse(state3.canMove(Direction.LEFT));
    }

    @Test
    void canMove_state4() {
        assertFalse(state4.canMove(Direction.UP));
        assertFalse(state4.canMove(Direction.RIGHT));
        assertFalse(state4.canMove(Direction.DOWN));
        assertFalse(state4.canMove(Direction.LEFT));
    }


    @Test
    void move_state1_up() {
        var copy = state1.clone();
        state1.move(Direction.UP);
        assertEquals(copy.getPosition().getUp(), state1.getPosition());
    }

    @Test
    void move_state1_right() {
        var copy = state1.clone();
        state1.move(Direction.RIGHT);
        assertEquals(copy.getPosition().getRight().getRight(), state1.getPosition());
    }

    @Test
    void move_state1_down() {
        var copy = state1.clone();
        state1.move(Direction.DOWN);
        assertEquals(copy.getPosition().getDown().getDown().getDown(), state1.getPosition());
    }

    @Test
    void move_state1_left() {
        var copy = state1.clone();
        state1.move(Direction.LEFT);
        assertEquals(copy.getPosition().getLeft().getLeft().getLeft().getLeft(), state1.getPosition());
    }

    @Test
    void move_state2_up() {
        var copy = state2.clone();
        state2.move(Direction.UP);
        assertEquals(copy.getPosition().getUp().getUp().getUp().getUp(), state2.getPosition());
    }

    @Test
    void move_state2_right() {
        var copy = state2.clone();
        state2.move(Direction.RIGHT);
        assertEquals(copy.getPosition(), state2.getPosition());
    }

    @Test
    void move_state2_down() {
        var copy = state2.clone();
        state2.move(Direction.DOWN);
        assertEquals(copy.getPosition(), state2.getPosition());
    }

    @Test
    void move_state2_left() {
        var copy = state2.clone();
        state2.move(Direction.LEFT);
        assertEquals(copy.getPosition(), state2.getPosition());
    }

    @Test
    void move_state3_up() {
        var copy = state3.clone();
        state3.move(Direction.UP);
        assertEquals(copy.getPosition().getUp().getUp(), state3.getPosition());
    }

    @Test
    void move_state3_right() {
        var copy = state3.clone();
        state3.move(Direction.RIGHT);
        assertEquals(copy.getPosition(), state3.getPosition());
    }

    @Test
    void move_state3_down() {
        var copy = state3.clone();
        state3.move(Direction.DOWN);
        assertEquals(copy.getPosition(), state3.getPosition());
    }

    @Test
    void move_state3_left() {
        var copy = state3.clone();
        state3.move(Direction.LEFT);
        assertEquals(copy.getPosition(), state3.getPosition());
    }

    @Test
    void move_state4_up() {
        var copy = state4.clone();
        state4.move(Direction.UP);
        assertEquals(copy.getPosition(), state4.getPosition());
    }

    @Test
    void move_state4_right() {
        var copy = state4.clone();
        state4.move(Direction.RIGHT);
        assertEquals(copy.getPosition(), state4.getPosition());
    }

    @Test
    void move_state4_down() {
        var copy = state4.clone();
        state4.move(Direction.DOWN);
        assertEquals(copy.getPosition(), state4.getPosition());
    }

    @Test
    void move_state4_left() {
        var copy = state4.clone();
        state4.move(Direction.LEFT);
        assertEquals(copy.getPosition(), state4.getPosition());
    }

    @Test
    void getLegalMoves() {
        assertEquals(EnumSet.allOf(Direction.class), state1.getLegalMoves());
        assertEquals(EnumSet.of(Direction.UP), state2.getLegalMoves());
        assertEquals(EnumSet.of(Direction.UP), state3.getLegalMoves());
        assertEquals(EnumSet.noneOf(Direction.class), state4.getLegalMoves());
    }

    @Test
    void testEquals() {
        assertEquals(state1, state1);

        var clone = state1.clone();
        clone.move(Direction.RIGHT);
        assertNotEquals(state1, clone);

        assertNotEquals(state1, null);
        assertNotEquals(state1, "Test string!");
        assertNotEquals(state2, state1);
    }

    @Test
    void testHashCode() {
        assertEquals(state1.hashCode(), state1.hashCode());
        assertEquals(state1.clone().hashCode(), state1.hashCode());
    }

    @Test
    void testClone() {
        var clone = state1.clone();
        assertEquals(state1, clone);
        assertNotSame(clone, state1);
    }

}
