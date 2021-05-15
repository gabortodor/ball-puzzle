package ui;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

public class FirstController {

    @FXML
    private TextField usernameField;

    @FXML
    private void switchToGame(ActionEvent event) throws IOException {
        if(usernameField.getText().isEmpty())
            handleUsernameNull();
        else {
            Logger.debug("Switching scene to game");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/secondScene.fxml"));
            Parent root = loader.load();
            SecondController controller = loader.<SecondController>getController();
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
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/thirdScene.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleExit() {
        Logger.debug("Exiting...");
        Platform.exit();
    }

    private void handleUsernameNull(){
        usernameField.promptTextProperty().set("Enter your username!");
        usernameField.getStyleClass().add("empty");
    }

}