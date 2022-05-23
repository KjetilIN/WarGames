package no.ntnu.wargames.frontend.gui.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Dialog class to show the locations of saved armies class.
 * Shows both path and a button to take to location of the folder.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class SavedArmyDialog extends Dialog<String> {

    // List of paths
    private final ArrayList<Path> paths;

    /**
     * Constructor for the Saved Army Dialog.
     *
     * @param pathList list of paths to be shown to the user.
     */
    public SavedArmyDialog(List<Path> pathList){
        super();
        this.paths = (ArrayList<Path>) pathList;
        createContent();
    }

    /**
     * Method that adds the content of the display to the screen
     */

    private void createContent() {
        //Pane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));

        int rowNumber = 0; // init row count

        //Add a new row of information for each path received
        for(Path path: paths){
            //Label
            Label label = new Label();
            label.setText("Path: ");

            //Text  field
            TextField txtPath = new TextField();
            txtPath.setPrefWidth(300);
            txtPath.setMaxWidth(300);
            txtPath.setText(path.toAbsolutePath().toString());
            txtPath.setEditable(false);

            //Button
            Button buttonToFile = new Button();
            buttonToFile.setText("Show In Folder");

            //Add button event for opening explorer
            buttonToFile.setOnAction(event -> {
                try {
                    /*Try to open explorer */
                    Runtime.getRuntime().exec("explorer.exe /select," + path);
                } catch (IOException e) {
                    /*Opens exception dialog if it did not work */
                    DialogWindow.openExceptionDialog(e);
                }
            });

            grid.addRow(rowNumber,label,txtPath,buttonToFile);

            rowNumber++;
        }
        getDialogPane().setContent(grid);
        getDialogPane().setHeaderText("Army'(s) Successfully Saved!");
        getDialogPane().getButtonTypes().add(ButtonType.OK);
        ((Stage) getDialogPane().getScene().getWindow()).getIcons().add(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(
                        "/no/ntnu/wargames/icon/logoIcon.PNG"))));

    }
}
