package ui.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.tinylog.Logger;
import java.io.IOException;

public class InformationController {

    @FXML
    private Label label;

    @FXML
    private ImageView imageView;

    @FXML
    private void initialize(){
        label.setTextFill(GameController.getColor());
        imageView.setImage(new Image(getClass().getResourceAsStream("/images/puzzleProblem.png")));
    }

    @FXML
    private void switchToMenu(ActionEvent event) throws IOException{
        Logger.debug("Switching scenes to main menu");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menuScene.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleExit() {
        Logger.debug("Exiting...");
        Platform.exit();
    }
}
