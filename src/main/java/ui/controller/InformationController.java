package ui.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.tinylog.Logger;


import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class InformationController {
    @FXML
    Hyperlink hyperlink=new Hyperlink();

    @FXML
    private Label label;

    @FXML
    private void initialize(){
        label.setTextFill(GameController.getColor());
    }


    public void handleHyperlink() {
        {
            try {
                Desktop.getDesktop().browse(new URI(hyperlink.getText()));
            } catch (Exception e) {
               Logger.debug(e.getStackTrace());
            }
        }
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
