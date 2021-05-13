package ui;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class FirstController {

    @FXML
    private void switchScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/secondScene.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.out.println("Exiting...");
        Platform.exit();
    }

}