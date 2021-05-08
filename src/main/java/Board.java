import java.util.Arrays;

public class Board {

    public Tile[][] tiles;
    public static final int BOARD_SIZE = 7;
    private Position startingPosition = new Position(1, 4);
    private Position goalPosition = new Position(5, 2);

    public Board() {
        this(new int[][]{{1, 2}, {4, 3}, {5, 0}, {5, 4}},
                new int[][]{{0, 0}, {2, 2}, {3, 4}, {5, 2}, {6, 3}},
                new int[][]{{0, 6}, {2, 1}, {3, 6}, {5, 2}},
                new int[][]{{0, 4}, {2, 6}, {3, 4}, {5, 2}, {6, 6}});
    }

    public Board(int[][] topBorders, int[][] rightBorders, int[][] bottomBorders, int[][] leftBorders) {
        checkBorders(topBorders, rightBorders, bottomBorders, leftBorders);
        tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        setUpBlankBoard();

        this.generateStartingBoard(topBorders, rightBorders, bottomBorders, leftBorders);
    }

    private void setUpBlankBoard(){
        for(int i=0;i<BOARD_SIZE;i++){
            for(int j=0;j<BOARD_SIZE;j++){
                tiles[i][j]=new Tile();
            }
        }
    }

    private void generateStartingBoard(int[][] topBorders, int[][] rightBorders, int[][] bottomBorders, int[][] leftBorders) {
        this.setTopBorders(topBorders);
        this.setRightBorders(rightBorders);
        this.setBottomBorders(bottomBorders);
        this.setLeftBorders(leftBorders);
    }

    private void checkBorders(int[][] topBorders, int[][] rightBorders, int[][] bottomBorders, int[][] leftBorders) {
        checkBoardIndexes(topBorders);
        checkBoardIndexes(rightBorders);
        checkBoardIndexes(bottomBorders);
        checkBoardIndexes(leftBorders);
    }

    private void checkBoardIndexes(int[][] borderArray) {
        for (var cordinates : borderArray) {
            if (!isOnBoard(cordinates[0], cordinates[1])) {
                throw new IllegalArgumentException();
            }
        }
    }

    public void setStartingPosition(Position position) {
        if (isOnBoard(position.getRow(), position.getCol()))
            startingPosition = position;
        throw new IllegalArgumentException();
    }

    public void setGoalPosition(Position position) {
        if (isOnBoard(position.getRow(), position.getCol()))
            goalPosition = position;
        throw new IllegalArgumentException();
    }

    private void setTopBorders(int[][] coordinates) {
        for (int[] coordinate : coordinates) {
            tiles[coordinate[0]][coordinate[1]].setTopBorder(true);
        }
    }

    private void setRightBorders(int[][] coordinates) {
        for (int[] coordinate : coordinates) {
            tiles[coordinate[0]][coordinate[1]].setRightBorder(true);
        }
    }

    private void setBottomBorders(int[][] coordinates) {
        for (int[] coordinate : coordinates) {
            tiles[coordinate[0]][coordinate[1]].setBottomBorder(true);
        }
    }

    private void setLeftBorders(int[][] coordinates) {
        for (int[] coordinate : coordinates) {
            tiles[coordinate[0]][coordinate[1]].setLeftBorder(true);
        }
    }

    public boolean isUpBlocked(Position position) {
        return tiles[position.getRow()][position.getCol()].isTopBorder() || tiles[position.getRow() - 1][position.getCol()].isBottomBorder();
    }

    public boolean isRightBlocked(Position position) {
        return tiles[position.getRow()][position.getCol()].isRightBorder() || tiles[position.getRow()][position.getCol() + 1].isLeftBorder();
    }

    public boolean isDownBlocked(Position position) {
        return tiles[position.getRow()][position.getCol()].isBottomBorder() || tiles[position.getRow() + 1][position.getCol()].isTopBorder();
    }

    public boolean isLeftBlocked(Position position) {
        return tiles[position.getRow()][position.getCol()].isLeftBorder() || tiles[position.getRow()][position.getCol() - 1].isRightBorder();
    }

    private boolean isOnBoard(int row, int col) {
        return row >= 0 && row < BOARD_SIZE &&
                col >= 0 && col < BOARD_SIZE;
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
}
