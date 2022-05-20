package no.ntnu.wargames.frontend.gui.dialog;

import javafx.scene.control.*;
import javafx.stage.StageStyle;
import java.util.Optional;

/**
 * Dialog class with all the methods for opening a dialog pane.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class DialogWindow {

    /**
     * Private Constructor for creating Window Dialog.
     * Constructor is hidden and should never be used.
     *
     * @throws IllegalArgumentException Throws exception if used.
     */
    private DialogWindow(){
        throw new IllegalArgumentException("DialogWindow");
    }

    /**
     * Dialog for editing the name of the Army.
     * Uses a TextInputDialog.
     * Used in setup page.
     *
     * @return returns the new name of the army, else returns null.
     */
    public static String openEditNameDialog(){
        TextInputDialog d = new TextInputDialog();
        d.setTitle("ARMY NAME");
        d.setContentText("Enter army name:");
        d.initStyle(StageStyle.UNDECORATED);

        Optional<String> userResponse = d.showAndWait();

        return userResponse.orElse(null);
    }

    /**
     * Opens a dialog to give the user a quick waring message.
     * Uses the Alert class of type Warning.
     *
     * @param context the warning message to give to users.
     */

    public static void openWarningDialog(String context){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(context);
        alert.showAndWait();
    }

    /**
     * Opens a dialog to display the message of an exception.
     * Uses the Alert class of type Error.
     *
     * @param e exception to be displayed as a message.
     */
    public static void openExceptionDialog(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.initStyle(StageStyle.DECORATED);
        alert.showAndWait();
    }

}
