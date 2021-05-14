package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    Board board1=new Board(); //A board which corresponds to the original puzzle's board

    Board board2=new Board(new int[][]{{4,4},{5,4}},
            new int[][]{{4,4},{4,3}});// A board with the (4,4) cell having all the borders


    Position position1=new Position(1,4);
    Position position2=new Position(4,4);


    @Test
    void testConstructor_invalid() {
        assertThrows(IllegalArgumentException.class, () -> new Board(new int[][]{{7,0},{3,4}},
                new int[][]{{3,1},{8,2}}));

    }

    @Test
    void testSetStartingPosition_invalid(){
        assertThrows(IllegalArgumentException.class,()->board1.setStartingPosition(new Position(8,4)));
    }

    @Test
    void testSetGoalPosition_invalid(){
        assertThrows(IllegalArgumentException.class,()->board1.setGoalPosition(new Position(3,9)));
    }

    @Test
    void testIsUpBlocked(){
        assertFalse(board1.isUpBlocked(position1));
        assertTrue(board2.isUpBlocked(position2));
    }

    @Test
    void testIsRightBlocked(){
        assertFalse(board1.isRightBlocked(position1));
        assertTrue(board2.isRightBlocked(position2));
    }

    @Test
    void testIsDownBlocked(){
        assertFalse(board1.isDownBlocked(position1));
        assertTrue(board2.isDownBlocked(position2));
    }

    @Test
    void testIsLeftBlocked(){
        assertFalse(board1.isLeftBlocked(position1));
        assertTrue(board2.isLeftBlocked(position2));
    }

    @Test
    void testIsOnBoard(){
        assertTrue(board1.isOnBoard(position1));
        assertFalse(board1.isOnBoard(new Position(7,8)));
        assertTrue(board2.isOnBoard(position2));
        assertFalse(board2.isOnBoard(new Position(9,10)));
    }

    @Test
    void testEquals() {
        assertEquals(board1, board1);

        var clone1 = board1.clone();
        clone1.setGoalPosition(new Position(3,5));
        assertNotEquals(board1, clone1);

        var clone2 = board1.clone();
        clone2.setStartingPosition(new Position(4,2));
        assertNotEquals(board1, clone2);

        assertNotEquals(board1, null);
        assertNotEquals(board1, "Test string!");
        assertNotEquals(board2, board1);
    }

    @Test
    void testHashCode() {
        assertEquals(board1.hashCode(), board1.hashCode());
        assertEquals(board1.clone().hashCode(), board1.hashCode());
    }

    @Test
    void testClone() {
        var clone = board1.clone();
        assertEquals(board1, clone);
        assertNotSame(clone, board1);
    }
}
