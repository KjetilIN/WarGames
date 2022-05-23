package no.ntnu.wargames.frontend.gui.dialog.simpleDialog;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    /**
     * Method that opens an exit dialog.
     *
     * @return returns the result as a button-type.
     */

    public static Optional<ButtonType> openExitDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Exit");
        alert.setContentText("You are about to close the app.\n" +
                "Are you sure?");
        return alert.showAndWait();
    }


    /**
     * Method that opens a Confirmation Dialog.
     *
     * @param header header text
     * @param context alert context
     */

    public static void openInformationDialog(String header, String context){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(header);
        alert.setContentText(context);
    }

    /**
     * Shows the winner in a dialog.
     *
     * @param txtWinner the label of the winner.
     */
    public static void showWinnerDialog(Label txtWinner){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Image image = new Image(
                String.valueOf(DialogWindow.class.getResource("/no/ntnu/wargames/icon/winner.png")));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        alert.setGraphic(imageView);
        alert.setHeaderText("Winner!");
        alert.setContentText("The winner is : " + txtWinner.getText() + "'s army!");
        alert.show();
    }

}
