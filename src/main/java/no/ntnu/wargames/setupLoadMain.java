package no.ntnu.wargames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main class to load the first page
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */
public class setupLoadMain extends Application {

    //Window positions
    double xOffset;
    double yOffset;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loadScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("WarGames");
        stage.initStyle(StageStyle.UNDECORATED);
        Image icon = new Image(getClass().getResourceAsStream("/no/ntnu/wargames/icon/logoIcon.PNG"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

        /* Lambda methods so that the undecorated screen is movable*/
        scene.setOnMousePressed(mousePressedEvent -> {
            // When pressed we change the mouse position
            xOffset = mousePressedEvent.getSceneX();
            yOffset = mousePressedEvent.getSceneY();
        });
        scene.setOnMouseDragged(dragEvent -> {
            // Change the window position when the user drag the window
            stage.setX(dragEvent.getScreenX() - xOffset);
            stage.setY(dragEvent.getScreenY() - yOffset);
        });
    }

    /**
     * Main method that starts the application
     *
     * @param args the given args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
