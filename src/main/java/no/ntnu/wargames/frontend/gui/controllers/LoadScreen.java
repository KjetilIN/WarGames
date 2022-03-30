package no.ntnu.wargames.frontend.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadScreen implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //defining the new window offset positions
    private double xOffset = 0;
    private double yOffset = 0;


    /* Methods */

    @FXML
    public void onExit(){
        System.exit(0);
    }

    @FXML
    public void onStart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/no/ntnu/wargames/setUpPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen(); // Loads the stage in the middle 
        stage.show();

        /* Lambda methods so that the undecorated screen is movable*/
        root.setOnMousePressed(event12 -> {
            /* When pressed we change the mouse position*/
            xOffset = event12.getSceneX();
            yOffset = event12.getSceneY();
        });
        root.setOnMouseDragged(event1 -> {
            /* Change the window position when the user drag the window*/
            stage.setX(event1.getScreenX() - xOffset);
            stage.setY(event1.getScreenY() - yOffset);
        });
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
