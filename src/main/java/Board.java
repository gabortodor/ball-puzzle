import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board implements Cloneable{


    public static final int BOARD_SIZE = 7;
    private Position startingPosition = new Position(1, 4);
    private Position goalPosition = new Position(5, 2);
    Position[] topBorders;
    Position[] rightBorders;

    public Board(){
        this(new int[][]{{1,2},{1,6},{3,1},{4,3},{4,6},{5,0},{5,4},{6,2}},
        new int[][]{{0,0},{0,3},{2,2},{2,5},{3,3},{3,4},{5,1},{5,2},{6,3},{6,5}});
    }

    public Board(int[][] topBorders,int[][] rightBorders) {
        this(convertArrayToPosition(topBorders),convertArrayToPosition(rightBorders));
    }

    public Board(Position[] topBorders,Position[] rightBorders) {
        checkBorders(topBorders, rightBorders);
        this.topBorders=topBorders;
        this.rightBorders=rightBorders;

    }

    private static Position[] convertArrayToPosition(int[][] intArray){
        List<Position> temp=new ArrayList<>();
        for(var coordinate:intArray){
            temp.add(new Position(coordinate[0],coordinate[1]));
        }
        Position[] positions=new Position[temp.size()];
        return temp.toArray(positions);
    }

    private void checkBorders(Position[] topBorders,Position[] rightBorders){
        if(checkBoardIndexes(topBorders) || checkBoardIndexes(rightBorders))
            throw new IllegalArgumentException();
    }


    private boolean checkBoardIndexes(Position[] borderArray) {
      return Arrays.stream(borderArray).anyMatch(position -> position.getRow()>=BOARD_SIZE || position.getCol()>=BOARD_SIZE);
    }

    public void setStartingPosition(Position position) {
        if (!isOnBoard(position))
            throw new IllegalArgumentException();
        startingPosition = position;

    }

    public void setGoalPosition(Position position) {
        if (!isOnBoard(position))
            throw new IllegalArgumentException();
        goalPosition = position;

    }


    public boolean isUpBlocked(Position position) {
       return Arrays.stream(topBorders).anyMatch(position::equals);
    }

    public boolean isRightBlocked(Position position) {
        return Arrays.stream(rightBorders).anyMatch(position::equals);
    }

    public boolean isDownBlocked(Position position) {
        Position positionDown=position.getDown();
        return Arrays.stream(topBorders).anyMatch(positionDown::equals);
    }

    public boolean isLeftBlocked(Position position) {
        Position positionLeft=position.getLeft();
        return Arrays.stream(rightBorders).anyMatch(positionLeft::equals);
    }

    public boolean isOnBoard(Position position) {
        return position.getRow() >= 0 && position.getRow() < BOARD_SIZE &&
                position.getCol() >= 0 && position.getCol() < BOARD_SIZE;
    }

    public Position getStartingPosition() {
        return startingPosition;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (! (o instanceof Board)) {
            return false;
        }
        return  startingPosition.equals(((Board)o).startingPosition) && goalPosition.equals(((Board)o).goalPosition)
                && Arrays.equals(topBorders,((Board)o).topBorders)
                && Arrays.equals(rightBorders,((Board)o).rightBorders);
    }

    @Override
    public int hashCode() {
        return (Arrays.hashCode(topBorders)+Arrays.hashCode(rightBorders)+startingPosition.hashCode()+ goalPosition.hashCode())/BOARD_SIZE;
    }

    @Override
    public Board clone() {
        Board copy;
        try {
            copy = (Board) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        copy.startingPosition = startingPosition.clone();
        copy.goalPosition = goalPosition.clone();
        copy.topBorders=deepClone(topBorders);
        copy.rightBorders=deepClone(rightBorders);
        return copy;
    }

    private static Position[] deepClone(Position[] a) {
        Position[] copy = a.clone();
        for (var i = 0; i < a.length; i++) {
            copy[i] = a[i].clone();
        }
        return copy;
    }

}
