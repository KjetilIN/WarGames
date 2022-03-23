package no.ntnu.wargames.frontend.gui.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;

import java.util.Optional;

/**
 * Dialog class with all the methods for opening a dialog pane.
 *
 * @author Kjetil Indrehus
 * @version 0.0.1
 */
public class DialogWindow {

    /* Methods that are common */
    public static String openEditNameDialog(){
        TextInputDialog d = new TextInputDialog();
        d.setTitle("ARMY NAME");
        d.setContentText("Enter army name:");
        d.initStyle(StageStyle.UNDECORATED);

        Optional<String> userResponse = d.showAndWait();

        return userResponse.orElse(null);
    }

    public static void openWarningDialog(String context){
        Alert waring = new Alert(Alert.AlertType.WARNING);
        waring.setContentText(context);
        waring.showAndWait();
    }
}
