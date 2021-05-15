package ui;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.Arrays;


public class ThirdController {

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<JsonHelper.JsonObject,String> username;

    @FXML
    private TableColumn<JsonHelper.JsonObject,Integer> numberOfMoves;

    @FXML
    private TableColumn<JsonHelper.JsonObject,String> time;

    @FXML
    private TextField textField;

    JsonHelper.JsonObject[] array;

    @FXML
    private void initialize(){
        tableView.setPlaceholder(new Label("No game records yet."));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        numberOfMoves.setCellValueFactory(new PropertyValueFactory<>("numberOfMoves"));
        time.setCellValueFactory(data->new ReadOnlyStringWrapper(Stopwatch.formatSeconds(data.getValue().getSeconds())));

        if(JsonHelper.getFile().exists()) {
            array = JsonHelper.load();
            JsonHelper.JsonObject[] currentArray = filterArray(JsonHelper.LEADERBOARDSIZE);
            loadArray(currentArray);
        }
    }


    private JsonHelper.JsonObject[] filterArray(int max){
        return Arrays.stream(array).filter(p->!p.getUsername().equals(JsonHelper.DEFAULTUSERNAME)).limit(max).toArray(JsonHelper.JsonObject[]::new);
    }

    @FXML
    private void switchToMenu(ActionEvent event) throws IOException{
        Logger.debug("Switching scenes to main menu");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/firstScene.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleExit() {
        Logger.debug("Exiting...");
        Platform.exit();
    }

    @FXML
    private void handleTextFieldEvent(ActionEvent event){
        try{
            int max=Integer.parseInt(textField.getText());
            JsonHelper.JsonObject[] currentArray= filterArray(max);
            loadArray(currentArray);
        }catch (NumberFormatException e){
            Logger.debug("Cannot convert input into Integer");
        }


    }

    private void loadArray(JsonHelper.JsonObject[] currentArray){
        ObservableList<JsonHelper.JsonObject> observableList= FXCollections.observableArrayList();
        observableList.addAll(currentArray);
        tableView.setItems(observableList);
    }

    @FXML
    private void handleDelete(){
        ButtonType deleteButton=new ButtonType("Delete");
        Alert deleteAlert=new Alert(Alert.AlertType.CONFIRMATION,"Delete",deleteButton,ButtonType.CANCEL);
        deleteAlert.setHeaderText("Are you sure you want to delete the leaderboard records?");
        deleteAlert.setContentText("This action cannot be undone.");
        deleteAlert.showAndWait();
        if(deleteAlert.getResult()==deleteButton){
            JsonHelper.deleteSave();
            Logger.debug("Deleted records");
            initialize();

        }
    }
}
