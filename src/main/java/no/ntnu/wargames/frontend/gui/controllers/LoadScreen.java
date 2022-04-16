package no.ntnu.wargames.frontend.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        /* Load the setup page (switching scene) */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/no/ntnu/wargames/setUpPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen(); // Loads the stage in the middle
        Image icon = new Image(getClass().getResourceAsStream("/no/ntnu/wargames/icon/logoIcon.PNG"));
        stage.getIcons().add(icon);
        stage.show();

        /* Lambda methods so that the undecorated screen is movable*/
        root.setOnMousePressed(mousePressedEvent -> {
            /* When pressed we change the mouse position*/
            xOffset = mousePressedEvent.getSceneX();
            yOffset = mousePressedEvent.getSceneY();
        });
        root.setOnMouseDragged(dragEvent -> {
            /* Change the window position when the user drag the window*/
            stage.setX(dragEvent.getScreenX() - xOffset);
            stage.setY(dragEvent.getScreenY() - yOffset);
        });
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //As of now, nothing happens
    }
}
