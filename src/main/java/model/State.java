package model;

import javafx.beans.property.*;

import java.util.EnumSet;

/**
 * Represents the current state of the ball-puzzle.
 */
public class State implements Cloneable {

    private Board board;
    private ObjectProperty<Position> position = new SimpleObjectProperty<>();
    private final SimpleBooleanProperty goal=new SimpleBooleanProperty();
    private final SimpleIntegerProperty numberOfMoves=new SimpleIntegerProperty();

    /**
     * Creates a {@code model.State} object which corresponds
     * to the original initial state.
     */
    public State() {
        this.board=new Board();
        this.position.set(board.getStartingPosition());
        goal.set(false);
        numberOfMoves.set(0);
    }

    /**
     * Creates a {@code model.Position} object, using the given
     * board and position.
     *
     * @param board the board in which the puzzle will take place
     * @param position the position of the ball in the initial state
     */
    public State(Board board,Position position){
        if(!board.isOnBoard(position))
            throw new IllegalArgumentException();
        this.board=board;
        this.position.set(position);
        goal.set(false);
        numberOfMoves.set(0);
    }

    /**
     * {@return the current position of the ball}
     */
    public Position getPosition() {
        return position.get();
    }

    /**
     * {@return the positionProperty linked to the ball}
     */
    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    /**
     * {@return whether is the current position is a goal position or not}
     */
    public boolean getGoal(){return goal.get();}

    /**
     * {@return the goalProperty of the state}
     */
    public SimpleBooleanProperty goalProperty(){
        return goal;
    }

    /**
     * {@return the number of moves in the current state}
     */
    public int getNumberOfMoves() {
        return numberOfMoves.get();
    }

    /**
     * {@return the numberOfMovesProperty of the state}
     */
    public SimpleIntegerProperty numberOfMovesProperty(){
        return numberOfMoves;
    }

    /**
     * Checks whether the current position of the ball is a goal position or not and sets
     * the property corresponding to that.
     */
    public void isGoal() {
        goal.set(position.get().equals(board.getGoalPosition()));
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
        return position.get().getRow() > 0 && !board.isUpBlocked(position.get());
    }

    private boolean canMoveRight() {
        return position.get().getCol()<board.getBoardSize()-1 && !board.isRightBlocked(position.get());
    }

    private boolean canMoveDown() {
        return position.get().getRow()<board.getBoardSize()-1 && !board.isDownBlocked(position.get());
    }

    private boolean canMoveLeft() {
        return position.get().getCol() > 0 && !board.isLeftBlocked(position.get());
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
        isGoal();
        numberOfMoves.set(numberOfMoves.get()+1);
    }

    private void moveUp() {
        if(canMoveUp()){
            Position newPosition=position.get().getUp();
            position.set(newPosition);
            moveUp();
        }
    }

    private void moveRight() {
        if(canMoveRight()){
            Position newPosition=position.get().getRight();
            position.set(newPosition);
            moveRight();
        }
    }

    private void moveDown() {
        if(canMoveDown()){
            Position newPosition=position.get().getDown();
            position.set(newPosition);
            moveDown();
        }
    }

    private void moveLeft() {
        if(canMoveLeft()){
            Position newPosition=position.get().getLeft();
            position.set(newPosition);
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

    /**
     * {@return the {@code Board} used in the current state}
     */
    public Board getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (! (o instanceof State)) {
            return false;
        }
        return position.get().equals(((State) o).position.get()) && board.equals(((State)o).board);
    }

    @Override
    public int hashCode() {
        return board.getGoalPosition().hashCode()-position.get().hashCode();
    }

    @Override
    public State clone() {
        State copy;
        try {
            copy = (State) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        copy.position = new SimpleObjectProperty<>(position.get().clone());
        copy.board=board.clone();
        return copy;
    }

}
