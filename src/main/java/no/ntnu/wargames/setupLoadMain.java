package no.ntnu.wargames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class setupLoadMain extends Application {


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
    }

    public static void main(String[] args) {
        launch(args);
    }

}
