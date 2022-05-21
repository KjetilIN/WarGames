package no.ntnu.wargames.frontend.gui.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import no.ntnu.wargames.backend.designPattern.Facade;

/**
 * Dialog for choosing what army to save.
 * Has the Integer class as return object.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class SaveOptionDialog extends Dialog<Integer> {


    /**
     * Constructor that opens the dialog and adds content.
     */
    public SaveOptionDialog(){
        super();
        createContent();
    }

    /**
     * Add content to the dialog.
     */
    private void createContent(){
        // Header
        getDialogPane().setHeaderText("Save options");

        ChoiceBox<String> choiceBox = DialogUtility.getArmyChoiceBox();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));
        grid.add(new Label("Army to save:"), 0, 0);
        grid.add(choiceBox, 1, 0);
        getDialogPane().setContent(grid);


        getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);

        setResultConverter((ButtonType type) ->{
            if(type == ButtonType.OK){
                if(choiceBox.getSelectionModel().isEmpty()){
                    DialogWindow.openWarningDialog("No army was selected to store in a file");
                    return null;
                }else{
                    return choiceBox.getSelectionModel().getSelectedIndex();
                }

            }
            return null;

        });
    }
}
