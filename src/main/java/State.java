import java.util.EnumSet;


public class State implements Cloneable {

    public Board board;
    private Position position;

    public State() {
        board=new Board();
        position=board.getStartingPosition();
    }

    public State(Board board,Position position){
        if(!board.isOnBoard(position))
            throw new IllegalArgumentException();
        this.board=board;
        this.position=position;
    }

    public Position getPosition() {
        return position.clone();
    }


    public boolean isGoal() {
        return position.equals(board.getGoalPosition());
    }


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
