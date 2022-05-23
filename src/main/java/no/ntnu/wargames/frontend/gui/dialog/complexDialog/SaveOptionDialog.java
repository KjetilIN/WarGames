package no.ntnu.wargames.frontend.gui.dialog.complexDialog;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import no.ntnu.wargames.frontend.gui.dialog.simpleDialog.DialogWindow;
import no.ntnu.wargames.frontend.gui.dialog.WarGamesDialog;

/**
 * Dialog for choosing what army to save.
 * Has the Integer class as return object.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class SaveOptionDialog extends WarGamesDialog<Integer> {


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
    @Override
    public void createContent(){
        // Header
        getDialogPane().setHeaderText("Save options");

        ChoiceBox<String> choiceBox = getArmyChoiceBox();

        GridPane grid = getGrid();
        grid.add(new Label("Army to save:"), 0, 0);
        grid.add(choiceBox, 1, 0);
        getDialogPane().setContent(grid);

        //Add icon
        updateIcon();

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
