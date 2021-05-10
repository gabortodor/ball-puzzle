
import java.util.Objects;


public class Position implements Cloneable {

    private int row;
    private int col;


    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

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
        return (o instanceof Position p) && p.row == row && p.col == col;
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
