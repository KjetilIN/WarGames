package no.ntnu.wargames.frontend.gui.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SaveOptionDialog extends Dialog<Integer> {

    public SaveOptionDialog(){
        super();
        createContent();
    }

    public void createContent(){
        // Header
        getDialogPane().setHeaderText("Save options");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("Army1");
        choiceBox.getItems().add("Army2");
        choiceBox.getItems().add("Both");


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
