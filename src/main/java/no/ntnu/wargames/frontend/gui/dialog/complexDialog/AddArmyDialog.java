package no.ntnu.wargames.frontend.gui.dialog.complexDialog;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import no.ntnu.wargames.backend.designPattern.UnitFactory;
import no.ntnu.wargames.backend.units.Army;
import no.ntnu.wargames.frontend.gui.dialog.simpleDialog.DialogWindow;
import no.ntnu.wargames.frontend.gui.dialog.WarGamesDialog;
import java.util.ArrayList;
import java.util.List;

/**
 * Dialog for adding an army to a chosen table.
 * Has the Army class as return object.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class AddArmyDialog extends WarGamesDialog<Army> {

    /*Unit types*/
    private static final List<String> UNIT_TYPES = List.of("Ranged","Infantry","Commander","Cavalry");
    private final List<Spinner<Integer>> unitSpinners;

    /**
     * Constructor that uses the SuperClass Dialog.
     * Also uses the private method to add content to dialog.
     */
    public AddArmyDialog(){
        super();
        unitSpinners = new ArrayList<>();
        createContent();
    }

    /**
     * Method that adds content to the dialog.
     * Also add result listener
     */
    @Override
    public void createContent() {
        /*Header text */
        getDialogPane().setHeaderText("Add army!");

        /*Name of the army*/
        TextField armyName = new TextField();
        armyName.setPromptText("Enter army name...");

        /*Checkbox for choosing what army to add to*/
        ChoiceBox<String> pickArmy = getArmyChoiceBox();

        /*Spinner for unit count*/
        Spinner<Integer> rangedSpinner = new Spinner<>(0,50,0);
        Spinner<Integer> commanderSpinner = new Spinner<>(0,50,0);
        Spinner<Integer> infantrySpinner = new Spinner<>(0,50,0);
        Spinner<Integer> cavalrySpinner = new Spinner<>(0,50,0);


        /* Field for a unit*/
        TextField unitNames = new TextField();
        unitNames.setPromptText("Unit(s) name...");

        TextField health = new TextField();
        health.setPromptText("Unit(s) health...");

       setTextFieldNumeric(health); // makes the text field numeric only

        /*Grid*/
        GridPane grid = getGrid();

        grid.add(new Label("Army name:"), 0, 0);
        grid.add(armyName, 1, 0);
        grid.add(new Label("Add army in:"), 0, 1);
        grid.add(pickArmy, 1, 1);
        grid.add(new Label("Ranged:"),0,2);
        grid.add(rangedSpinner,1,2);
        grid.add(new Label("Infantry:"),0,3);
        grid.add(infantrySpinner,1,3);
        grid.add(new Label("Commander:"),0,4);
        grid.add(commanderSpinner,1,4);
        grid.add(new Label("Cavalry:"),0,5);
        grid.add(cavalrySpinner,1,5);
        grid.add(new Label("Unit(s) name: "),0,6);
        grid.add(unitNames,1,6);
        grid.add(new Label("Set Unit(s) health: "),0,7);
        grid.add(health,1,7);

        getDialogPane().setContent(grid);

        /*Add all the spinners*/
        unitSpinners.add(rangedSpinner);
        unitSpinners.add(infantrySpinner);
        unitSpinners.add(commanderSpinner);
        unitSpinners.add(cavalrySpinner);

        for (Spinner<Integer> spinner:unitSpinners) {
            spinner.setEditable(true); // All spinner can be edited by typing
            setTextFieldNumeric(spinner.getEditor()); // Make text field numeric only

        }

        /*Buttons*/
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        //Add Icon
        updateIcon();

        /*Result converter*/
        setResultConverter((ButtonType buttonType)->{
            if(buttonType == ButtonType.OK) {
                Army result = null;

                if (armyName.getText().length() > 0 && !pickArmy.getSelectionModel().isEmpty()
                        && unitNames.getText().length() > 0 && health.getText().length() > 0 && Integer.parseInt(health.getText()) > 0) {


                    result = new Army(armyName.getText());


                    boolean hasUnitToAdd = false;
                    for (int i = 0; i < unitSpinners.size(); i++) { // for each unit-type, add units
                        try {
                            int spinnerValue = unitSpinners.get(i).getValue();
                            if (spinnerValue > 0) { //add only if there is
                                hasUnitToAdd = true;
                                result.addAll(UnitFactory.createListOfUnit(spinnerValue, UNIT_TYPES.get(i), unitNames.getText(), Integer.parseInt(health.getText())));
                            }
                        } catch (NumberFormatException e) {
                            DialogWindow.openWarningDialog(e.getMessage());}


                    }
                    if(!hasUnitToAdd){
                        DialogWindow.openWarningDialog("No unit(s) was added.\n" +
                                "Please use enter amount each type when adding the units!");
                        return null;
                    }


                    result.setName(pickArmy.getSelectionModel().getSelectedIndex()+armyName.getText());

                }else{
                    //Use String builder to make error message
                    StringBuilder messageBuilder = new StringBuilder();
                    messageBuilder.append("Wrong format: \n");
                    if(armyName.getText().length() == 0){
                        messageBuilder.append("- No army name given. \n");
                    }
                    if(pickArmy.getSelectionModel().isEmpty()){
                        messageBuilder.append("- No selection for were the army is set. \n");
                    }
                    if(unitNames.getText().length() == 0){
                        messageBuilder.append("- No unit name given. \n");
                    }
                    if( health.getText().length() == 0){
                        messageBuilder.append("- Health was not given.");
                    }else if(Integer.parseInt(health.getText()) <= 0){
                        messageBuilder.append("- Health was 0 or lower!");
                    }
                    DialogWindow.openWarningDialog(messageBuilder.toString());

                }
                return result;
            }

            return null;
        });


    }


}