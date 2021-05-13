package ui;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import model.Direction;
import model.Position;
import model.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SecondController {

    private List<Position> selectablePositions = new ArrayList<>();
    private State state = new State();
    private Stopwatch stopwatch=new Stopwatch();
    Color color=generateColor();

    String[] arrows=new String[]{"\u25B2","\u25B6","\u25BC","\u25C0"};

    @FXML
    private GridPane grid;

    @FXML
    private TextField numberOfMovesField;

    @FXML
    private TextField elapsedTime;

    @FXML
    private void initialize() {
        createGrid();
        createBall();
        setSelectablePositions();
        showSelectablePositions();
        stopwatch.start();
        createBindings();
    }

    private void createBindings(){
        numberOfMovesField.textProperty().bind(state.numberOfMovesProperty().asString());
        elapsedTime.textProperty().bind(stopwatch.hhmmssProperty());
        state.goalProperty().addListener(this::handleIsGoal);
    }


    private void createGrid() {
        grid.getStyleClass().add("grid");
        for (int i = 0; i < grid.getRowCount(); i++) {
            for (int j = 0; j < grid.getColumnCount(); j++) {
                var square = createTile();
                grid.add(square, j, i);
                setBorders(new Position(i, j));
            }
        }
    }

    private void setBorders(Position position) {
        List rightBorders = Arrays.asList(state.getBoard().getRightBorders());
        List topBorders = Arrays.asList(state.getBoard().getTopBorders());
        if (rightBorders.contains(position))
            getTile(position).getStyleClass().add("tile-right");
        if (topBorders.contains(position))
            getTile(position).getStyleClass().add("tile-top");

    }

    private StackPane createTile() {
        var tile = new StackPane();
        tile.getStyleClass().add("tile");
        tile.setOnMouseClicked(this::handleMouseClick);
        return tile;
    }

    private void createBall() {
        state.positionProperty().addListener(this::ballPositionChange);
        var ball = new Circle(30);
        ball.setFill(color);
        getTile(state.getPosition()).getChildren().add(ball);
    }


    private StackPane getTile(Position position) {
        for (var child : grid.getChildren()) {
            if (GridPane.getRowIndex(child) == position.getRow() && GridPane.getColumnIndex(child) == position.getCol()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var tile = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(tile);
        var col = GridPane.getColumnIndex(tile);
        var position = new Position(row, col);
        System.out.println(position);
        //Logger.debug("Click on square {}", position);
        handleClickOnSquare(position);
    }

    private void handleClickOnSquare(Position position) {
        if (selectablePositions.contains(position)) {
            hideSelectablePositions();
            Direction direction = Direction.of(position.getRow() - state.getPosition().getRow(), position.getCol() - state.getPosition().getCol());
            hideSelectablePositions();
            state.move(direction);
            setSelectablePositions();
            showSelectablePositions();
            //Logger
        }
    }


    private void setSelectablePositions() {
        selectablePositions.clear();
        for (var direction : state.getLegalMoves()) {
            selectablePositions.add(state.getPosition().getTarget(direction));
        }

    }

    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var tile = getTile(selectablePosition);
            tile.getChildren().add(addArrow(tile,selectablePosition));
        }
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var tile = getTile(selectablePosition);
            removeArrow(tile);
        }
    }

    private Color generateColor(){
        Random random=new Random();
        return Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255));
    }

    private Label addArrow(StackPane tile,Position position){
        int row=position.getRow()-state.getPosition().getRow();
        int col=position.getCol()-state.getPosition().getCol();
        int index=Direction.of(row,col).ordinal();
        Label text=new Label(arrows[index]);
        text.setTextFill(color);
        if(index%2==0)
            text.setFont(new Font(30));
        else
            text.setFont(new Font(50));
        return text;
    }
    private void removeArrow(StackPane tile){
        tile.getChildren().clear();
    }


    private void ballPositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition) {
        //Logger   System.out.println("Move:" + oldPosition + "->" + newPosition);
        StackPane oldTile = getTile(oldPosition);
        StackPane newTile = getTile(newPosition);
        newTile.getChildren().addAll(oldTile.getChildren());
        oldTile.getChildren().clear();
    }

    private void handleIsGoal(ObservableValue observableValue,boolean oldValue, boolean newValue){
        if(newValue){
            stopwatch.stop();
            System.out.println("The goal is reached");
            Alert isGoalAlert=new Alert(Alert.AlertType.CONFIRMATION);
            isGoalAlert.setHeaderText("Game over is dead");
            isGoalAlert.setContentText("Bottom text");
            isGoalAlert.showAndWait();
        }
    }

}
