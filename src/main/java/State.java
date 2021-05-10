import java.util.EnumSet;

/**
 * Represents the current state of the ball-puzzle.
 */
public class State implements Cloneable {

    private Board board;
    private Position position;

    /**
     * Creates a {@code State} object which corresponds
     * to the original initial state.
     */
    public State() {
        board=new Board();
        position=board.getStartingPosition();
    }

    /**
     * Creates a {@code Position} object, using the given
     * board an position.
     *
     * @param board the board in which the puzzle will take place
     * @param position the position of the ball in the initial state
     */
    public State(Board board,Position position){
        if(!board.isOnBoard(position))
            throw new IllegalArgumentException();
        this.board=board;
        this.position=position;
    }

    /**
     * {@return a clone of the current position of the ball}
     */
    public Position getPosition() {
        return position.clone();
    }

    /**
     * {@return whether the current position of the ball is a goal position or not}
     */
    public boolean isGoal() {
        return position.equals(board.getGoalPosition());
    }

    /**
     * {@return whether the ball can move in the direction specified or not}
     *
     * @param direction the direction which the ball should move in
     */
    public boolean canMove(Direction direction) {
        return switch (direction) {
            case UP -> canMoveUp();
            case RIGHT -> canMoveRight();
            case DOWN -> canMoveDown();
            case LEFT -> canMoveLeft();
        };
    }

    private boolean canMoveUp() {
        return position.getRow() > 0 && !board.isUpBlocked(position);
    }

    private boolean canMoveRight() {
        return position.getCol()<board.getBoardSize()-1 && !board.isRightBlocked(position);
    }

    private boolean canMoveDown() {
        return position.getRow()<board.getBoardSize()-1 && !board.isDownBlocked(position);
    }

    private boolean canMoveLeft() {
        return position.getCol() > 0 && !board.isLeftBlocked(position);
    }

    /**
     * Moves the ball in the direction specified, while
     * the way isn't blocked.
     *
     * @param direction the direction which the ball is moved in
     */
    public void move(Direction direction) {
        switch (direction) {
            case UP -> moveUp();
            case RIGHT -> moveRight();
            case DOWN -> moveDown();
            case LEFT -> moveLeft();
        }
    }

    private void moveUp() {
        if(canMoveUp()){
            position.setUp();
            moveUp();
        }
    }

    private void moveRight() {
        if(canMoveRight()){
            position.setRight();
            moveRight();
        }
    }

    private void moveDown() {
        if(canMoveDown()){
            position.setDown();
            moveDown();
        }
    }

    private void moveLeft() {
        if(canMoveLeft()){
            position.setLeft();
            moveLeft();
        }
    }

    /**
     * {@return the set of directions in which the ball can move}
     */
    public EnumSet<Direction> getLegalMoves() {
        var legalMoves = EnumSet.noneOf(Direction.class);
        for (var direction : Direction.values()) {
            if (canMove(direction)) {
                legalMoves.add(direction);
            }
        }
        return legalMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (! (o instanceof State)) {
            return false;
        }
        return position.equals(((State) o).position) && board.equals(((State)o).board);
    }

    @Override
    public int hashCode() {
        return board.getGoalPosition().hashCode()-position.hashCode();
    }

    @Override
    public State clone() {
        State copy;
        try {
            copy = (State) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        copy.position = position.clone();
        copy.board=board.clone();
        return copy;
    }

}
