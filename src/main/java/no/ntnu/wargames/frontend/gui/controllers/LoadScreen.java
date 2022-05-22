package no.ntnu.wargames.frontend.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import no.ntnu.wargames.frontend.gui.dialog.DialogWindow;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Load screen class.
 * Only shows logo and some buttons.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */
public class LoadScreen implements Initializable {

    // Load screen
    @FXML
    private AnchorPane mainPage;

    //defining the new window offset positions
    private double xOffset = 0;
    private double yOffset = 0;


    /* Methods used by buttons*/


    /**
     * Exit method.
     * Used by a button to exit the application.
     */
    @FXML
    public void onExit(){
        Optional<ButtonType> result = DialogWindow.openExitDialog();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }

    }

    /**
     * Methods that initializes the load screen.
     * Uses FXML loader to load the page.
     * Sets the window and edit changes.
     *
     * @param event the action event that is used by the button.
     * @throws IOException throws exception if the page was not found.
     */
    @FXML
    public void onStart(ActionEvent event) throws IOException {
        /* Load the setup page (switching scene) */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/no/ntnu/wargames/setUpPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen(); // Loads the stage in the middle
        Image icon = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(
                        "/no/ntnu/wargames/icon/logoIcon.PNG")));
        stage.getIcons().add(icon);
        stage.show();

        /* Lambda methods so that the undecorated screen is movable*/
        scene.setOnMousePressed(mousePressedEvent -> {
            /* When pressed we change the mouse position*/
            xOffset = mousePressedEvent.getSceneX();
            yOffset = mousePressedEvent.getSceneY();
        });
        scene.setOnMouseDragged(dragEvent -> {
            /* Change the window position when the user drag the window*/
            stage.setX(dragEvent.getScreenX() - xOffset);
            stage.setY(dragEvent.getScreenY() - yOffset);
        });
    }

    /**
     * Method that minimizes the window.
     * Called when user clicks top left icon of load screen.
     */
    @FXML
    public void onMinimize(){
        ((Stage) mainPage.getScene().getWindow()).setIconified(true);
    }


    // Empty init method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //As of now, nothing happens
        //Has to be implemented because of "Initializable".
    }
}
