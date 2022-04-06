package no.ntnu.wargames.frontend.gui.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import no.ntnu.wargames.backend.units.Unit;

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

    public static void openExceptionDialog(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.initStyle(StageStyle.DECORATED);
        alert.showAndWait();
    }

    public static void openAddUnitDialog(){
        Dialog<Unit> dialog = new Dialog<>();
        //Setting the title
        dialog.setTitle("Add Unit Dialog");

        //Header
        Label label = new Label("ADD UNIT");
        dialog.getDialogPane().setHeaderText("ADD UNIT");

        //Textfeilds
        TextField name = new TextField();
        name.setPromptText("Unit name");

        TextField type = new TextField();
        type.setPromptText("Unit type");

        //Pane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));
        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Type:"), 0, 1);
        grid.add(type, 1, 1);
        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        //Show
        dialog.show();


    }
}
