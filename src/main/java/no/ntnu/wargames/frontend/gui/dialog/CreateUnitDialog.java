package no.ntnu.wargames.frontend.gui.dialog;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import no.ntnu.wargames.backend.units.Unit;
import no.ntnu.wargames.backend.units.UnitFactory;

import java.util.InputMismatchException;

public class CreateUnitDialog extends Dialog<Unit> {

    // TODO: 30.03.2022 Refactor and clean up

    private Unit unit;

    public  CreateUnitDialog(){
        super();
        createContent();
    }

    public void createContent(){

        //Setting the title
        setTitle("Add Unit Dialog");

        //Header
        getDialogPane().setHeaderText("Add a new Unit");

        //Input prompt
        TextField name = new TextField();
        name.setPromptText("Enter unit name...");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("Infantry");
        choiceBox.getItems().add("Ranged");
        choiceBox.getItems().add("Commander");
        choiceBox.getItems().add("Cavalry");

        TextField healthTextField = new TextField();
        healthTextField.setPromptText("Enter health...");

        // force the field to be numeric only
        healthTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                healthTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        //Pane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));
        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Type:"), 0, 1);
        grid.add(choiceBox, 1, 1);
        grid.add(new Label("Health:"),0,2);
        grid.add(healthTextField,1,2);
        getDialogPane().setContent(grid);

        getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);

        setResultConverter((ButtonType type)->{
            if(type == ButtonType.OK){
                boolean choiceboxSelected = choiceBox.getSelectionModel() != null
                        && !choiceBox.getSelectionModel().isEmpty();

                boolean nameGiven = !name.getText().isEmpty();

                int health = Integer.parseInt(healthTextField.getText());

                if(choiceboxSelected && nameGiven){
                    UnitFactory factory = new UnitFactory();
                    return factory.createUnit(choiceBox.getValue(),name.getText(),health);
                }

                //Opens waring dialog based on missing input
                if(!nameGiven){
                    DialogWindow.openWarningDialog("No name was given");
                }else if(!choiceboxSelected){
                    DialogWindow.openWarningDialog("No unit choice was made");
                }else{
                    DialogWindow.openWarningDialog("Please enter name and unit type...");
                }

            }
            
            return null;
        });

    }


}
