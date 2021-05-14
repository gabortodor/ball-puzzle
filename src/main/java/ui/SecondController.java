package ui;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.tinylog.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import model.Direction;
import model.Position;
import model.State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SecondController {

    private final List<Position> selectablePositions = new ArrayList<>();
    private final State state = new State();
    private final Stopwatch stopwatch = new Stopwatch();
    Color color = generateColor();

    String[] arrows = new String[]{"\u25B2", "\u25B6", "\u25BC", "\u25C0"};

    @FXML
    private GridPane grid;

    @FXML
    private TextField numberOfMovesField;

    @FXML
    private TextField elapsedTime;

    @FXML
    private void initialize() {
        Logger.debug("Initializing puzzle");
        createGrid();
        createBall();
        setSelectablePositions();
        showSelectablePositions();
        stopwatch.start();
        createBindings();
    }

    private void createBindings() {
        numberOfMovesField.textProperty().bind(state.numberOfMovesProperty().asString());
        elapsedTime.textProperty().bind(stopwatch.hhmmssProperty());
        state.goalProperty().addListener(this::handleIsGoal);
    }


    private void createGrid() {
        grid.getStyleClass().add("grid");
        for (int i = 0; i < grid.getRowCount(); i++) {
            for (int j = 0; j < grid.getColumnCount(); j++) {
                var tile = createTile();
                grid.add(tile, j, i);
                setBorders(new Position(i, j));
            }
        }
        createGoal();
    }

    private void setBorders(Position position) {
        List<Position> rightBorders = Arrays.asList(state.getBoard().getRightBorders());
        List<Position> topBorders = Arrays.asList(state.getBoard().getTopBorders());
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

    private void createGoal(){
        StackPane tile=getTile(state.getBoard().getGoalPosition());
        Label text=new Label("Goal");
        text.setTextFill(color);
        text.setFont(new Font(30));
        tile.getChildren().add(text);
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
        Logger.debug("Click on the tile: {}", position);
        handleClickOnSquare(position);
    }

    private void handleClickOnSquare(Position position) {
        if (selectablePositions.contains(position)) {
            hideSelectablePositions();
            Direction direction = Direction.of(position.getRow() - state.getPosition().getRow(), position.getCol() - state.getPosition().getCol());
            hideSelectablePositions();
            Logger.debug("Moving the ball in the direction: {}",direction);
            state.move(direction);
            setSelectablePositions();
            showSelectablePositions();
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
            Label arrow=addArrow(selectablePosition);
            arrow.getStyleClass().add("arrow");
            tile.getChildren().add(arrow);

        }
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var tile = getTile(selectablePosition);
            removeArrow(tile);
        }
    }

    private Color generateColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(230), random.nextInt(230), random.nextInt(230));
    }

    private Label addArrow(Position position) {
        int row = position.getRow() - state.getPosition().getRow();
        int col = position.getCol() - state.getPosition().getCol();
        int index = Direction.of(row, col).ordinal();
        Label text = new Label(arrows[index]);
        text.setTextFill(color);
        if (index % 2 == 0)
            text.setFont(new Font(30));
        else
            text.setFont(new Font(50));
        return text;
    }

    private void removeArrow(StackPane tile) {
        tile.getChildren().clear();
    }


    private void ballPositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition) {
        Logger.debug("Move:{} -> {}",oldPosition,newPosition);
        StackPane oldTile = getTile(oldPosition);
        StackPane newTile = getTile(newPosition);
        newTile.getChildren().addAll(oldTile.getChildren());
        oldTile.getChildren().clear();
    }

    private void handleIsGoal(ObservableValue observableValue, boolean oldValue, boolean newValue){
        if (newValue) {
            stopwatch.stop();
            Logger.debug("The goal has been reached with the time of {} and the move count of {}",stopwatch.hhmmssProperty().get(),state.getNumberOfMoves());
            ButtonType menu=new ButtonType("Main menu");
            Alert isGoalAlert = new Alert(Alert.AlertType.CONFIRMATION,"Alert",menu,new ButtonType("Leaderboard"));
            isGoalAlert.setTitle("Puzzle solved");
            isGoalAlert.setHeaderText("The puzzle has been solved!");
            isGoalAlert.setContentText("Time: "+stopwatch.hhmmssProperty().get()+"\tMoves:"+state.getNumberOfMoves());
            isGoalAlert.showAndWait();
            if(isGoalAlert.getResult()==menu){
                switchScene("/fxml/firstScene.fxml");
            }else{
                switchScene("/fxml/thirdScene.fxml");
            }
        }
    }

    @FXML
    private void handleRestart() {
        Logger.debug("Puzzle restarted");
        state.positionProperty().set(state.getBoard().getStartingPosition());
        state.numberOfMovesProperty().set(0);
        hideSelectablePositions();
        stopwatch.reset();
        setSelectablePositions();
        showSelectablePositions();
    }

    private void switchScene(String path) {
        Logger.debug("Switching scenes...");
        Stage stage = (Stage) (grid.getScene().getWindow());
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource(path));
        }
        catch (IOException e){
            Logger.debug("Cannot load scene");
            Logger.debug("Exception caught:{}, {}",e.getMessage(),e.getCause());
            System.exit(1);
        }
        stage.setScene(new Scene(root));
        stage.show();
    }

}
