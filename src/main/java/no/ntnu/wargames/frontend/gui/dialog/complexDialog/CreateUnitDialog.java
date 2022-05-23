package no.ntnu.wargames.frontend.gui.dialog.complexDialog;


import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import no.ntnu.wargames.backend.designPattern.UnitFactory;
import no.ntnu.wargames.backend.units.Unit;
import no.ntnu.wargames.frontend.gui.dialog.simpleDialog.DialogWindow;
import no.ntnu.wargames.frontend.gui.dialog.WarGamesDialog;

import java.util.List;


/**
 * Dialog for creating a new Unit.
 * Extend WarGamesDialog.
 * Has the Unit class as return object.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class CreateUnitDialog extends WarGamesDialog<Unit> {

    //Fields
    private final Mode mode;
    private Unit unit;

    /*Enum of the mode. Either Edit a unit or create a new unit*/
    private enum Mode{
        EDIT,NEW
    }

    /**
     * Constructor for the Unit Dialog.
     * Used if the Mode.NEW (new unit to edit).
     */
    public CreateUnitDialog(){
        super();
        this.mode = Mode.NEW; //Set mode to new Unit
        createContent();
    }

    /**
     * Constructor for the Unit Dialog used for editing the unit.
     *
     * @param unit the given unit to be edited.
     */
    public CreateUnitDialog(Unit unit){
        /* Set up constructor for edit*/
        super();
        this.unit = unit;
        this.mode = Mode.EDIT; // Edit mode
        if(unit != null){
            createContent();
        }

    }

    /**
     * Method that adds content to the dialog.
     * Also add result listener
     */
    @Override
    public void createContent(){

        //Input prompt
        TextField name = new TextField();
        name.setPromptText("Enter unit name...");

        ChoiceBox<String> choiceBox = getChoiceBox(
                List.of("Infantry","Ranged","Commander","Cavalry")
        );

        TextField healthTextField = new TextField();
        healthTextField.setPromptText("Enter health...");

        // force the field to be numeric only
        setTextFieldNumeric(healthTextField);

        if((mode == Mode.EDIT)) {
            name.setText(this.unit.getName());
            healthTextField.setText(Integer.toString(this.unit.getHealth()));
            choiceBox.setValue(this.unit.getUnitType());

        }


        //Pane
        GridPane grid = getGrid();
        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Type:"), 0, 1);
        grid.add(choiceBox, 1, 1);
        grid.add(new Label("Health:"),0,2);
        grid.add(healthTextField,1,2);
        getDialogPane().setContent(grid);

        if(this.mode == Mode.EDIT){
            grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 1);
        }

        if (this.mode == Mode.EDIT) {
            getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        }else {
            getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        }

        if(this.mode == Mode.NEW) {
            setTitle("New Unit");
            setHeaderText("Create new unit!");
        }else if(this.mode == Mode.EDIT) {
            setTitle("Unit Edit");
            setHeaderText("Edit unit");
        }

        //Update icon
        updateIcon();

        setResultConverter((ButtonType type)->{
            if(type == ButtonType.OK){
                boolean checkboxSelected = choiceBox.getSelectionModel() != null
                        && !choiceBox.getSelectionModel().isEmpty();

                //Check if name is not empty and is only letters and numbers
                boolean nameGiven = !name.getText().isEmpty() && name.getText().matches("[a-zA-Z0-9]*");


                //Validate that health was given
                boolean healthGiven = !healthTextField.getText().isBlank();
                int health;
                try{
                    health = Integer.parseInt(healthTextField.getText());
                }catch (NumberFormatException e){
                    healthGiven = false;
                    health = -1; //this value will never be used
                }


                if(checkboxSelected && nameGiven && healthGiven){
                    if(mode == Mode.NEW){
                        //If all the requirements are correct, then we make a new unit.
                        return UnitFactory.createUnit(choiceBox.getValue(),name.getText(),health);
                    }else{
                        this.unit.setHealth(health);
                        this.unit.setName(name.getText());
                        return this.unit;
                    }

                }
                if(this.mode == Mode.NEW){
                    //Opens waring dialog based on missing input
                    //Only opened if the mode is new and to return statement previous has been reached.
                    String endString = "\n" + "Therefore no unit was added!";
                    if(!nameGiven && checkboxSelected && healthGiven){
                        DialogWindow.openWarningDialog("No name was given"
                                + endString);
                    }else if(!checkboxSelected && nameGiven && healthGiven){
                        DialogWindow.openWarningDialog("No unit choice was made" +
                                endString);
                    }else if (!healthGiven && checkboxSelected && nameGiven){
                        DialogWindow.openWarningDialog("No health was given."
                                + endString);

                    }else {
                        DialogWindow.openWarningDialog("Please enter name and unit type...");
                    }
                }
            }
            return null;
        });

    }


}
