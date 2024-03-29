package model;

import java.util.Objects;

/**
 * Represents a 2D position.
 */
public class Position implements Cloneable {

    private int row;
    private int col;

    /**
     * Creates a {@code model.Position} object.
     *
     * @param row the row coordinate of the position
     * @param col the column coordinate of the position
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * {@return the row coordinate of the position}
     */
    public int getRow() {
        return row;
    }

    /**
     * {@return the column coordinate of the position}
     */
    public int getCol() {
        return col;
    }

    /**
     * {@return the position whose vertical and horizontal distances from this
     * position are equal to the coordinate changes of the direction given}
     *
     * @param direction a direction that specifies the change in the coordinates
     */
    public Position getTarget(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }

    public Position getUp() {
        return getTarget(Direction.UP);
    }

    public Position getRight() {
        return getTarget(Direction.RIGHT);
    }

    public Position getDown() {
        return getTarget(Direction.DOWN);
    }

    public Position getLeft() {
        return getTarget(Direction.LEFT);
    }

    /**
     * Changes the position by the coordinate changes of the direction given.
     *
     * @param direction a direction that specifies the change in the coordinates
     */
    public void setTarget(Direction direction) {
        row += direction.getRowChange();
        col += direction.getColChange();
    }

    public void setUp() {
        setTarget(Direction.UP);
    }

    public void setRight() {
        setTarget(Direction.RIGHT);
    }

    public void setDown() {
        setTarget(Direction.DOWN);
    }

    public void setLeft() {
        setTarget(Direction.LEFT);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (! (o instanceof Position)) {
            return false;
        }
        return  ((Position)o).row == row && ((Position)o).col == col;
    }

    @Override
    public Position clone() {
        Position copy;
        try {
            copy = (Position) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        return copy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }


    @Override
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

}
