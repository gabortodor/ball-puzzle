package ui.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;
import helper.JsonHelper;

public class MenuController {

    @FXML
    private Label label;

    @FXML
    private TextField usernameField;

    @FXML
    private void initialize(){
        label.setTextFill(GameController.getColor());
    }


    @FXML
    private void switchToGame(ActionEvent event) throws IOException {
        if(usernameField.getText().isEmpty() || usernameField.getText().equals(JsonHelper.DEFAULT_USERNAME))
            handleUsernameInvalid();
        else {
            Logger.debug("Switching scene to game");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameScene.fxml"));
            Parent root = loader.load();
            GameController controller = loader.getController();
            controller.setUsername(usernameField.getText());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    private void switchToLeaderboard(ActionEvent event) throws IOException{
        Logger.debug("Switching scenes to leaderboard");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/leaderboardScene.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void switchToInformation(ActionEvent event) throws IOException{
        Logger.debug("Switching scenes to information");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/informationScene.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleExit() {
        Logger.debug("Exiting...");
        Platform.exit();
    }

    private void handleUsernameInvalid(){
        usernameField.getStyleClass().add("invalid");
        if(usernameField.getText().isEmpty())
            usernameField.promptTextProperty().set("Enter your username!");
        else {
            usernameField.setText("");
            usernameField.promptTextProperty().set("Invalid username!");
        }

    }


}