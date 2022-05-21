package no.ntnu.wargames.frontend.gui.dialog;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import no.ntnu.wargames.backend.designPattern.Facade;


/**
 * Dialog utility.
 * Used by other Dialog classes to configure the dialog.
 * Main reason for implementation is no minimize duplication of code.
 * Leads to tighter coupling, but gives a faster software.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class DialogUtility {

    /**
     * Hidden constructor, should not be used.
     *
     * @exception IllegalArgumentException throws exception if used.
     */
    private DialogUtility(){
        throw new IllegalArgumentException("DialogUtility");
    }

    /**
     * Makes the given text field numeric only.
     * All other input will be replaced with empty string.
     * Achieves this by adding a listeners to the text field.
     *
     * @param textField given text field to only receive numeric input.
     */

    public static void setTextFieldNumeric(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            // force the field to be numeric only
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Method that returns a choice menu.
     * Changes army options if name is not NONE.
     * Default name get "Army X" as option.
     * Both is also added as an option
     *
     * @return returns a choice menu for the armies, with both included.
     */

    public static ChoiceBox<String> getArmyChoiceBox(){
        /*Checkbox for choosing what army to add to*/
        ChoiceBox<String> pickArmy = new ChoiceBox<>();
        if(Facade.getInstance().getArmyOne().getName().equalsIgnoreCase("none")){
            pickArmy.getItems().add("Army1");
        }else{
            pickArmy.getItems().add(Facade.getInstance().getArmyOne().getName());
        }

        if(Facade.getInstance().getArmyTwo().getName().equalsIgnoreCase("none")){
            pickArmy.getItems().add("Army2");
        }else{
            pickArmy.getItems().add(Facade.getInstance().getArmyTwo().getName());
        }

        pickArmy.getItems().add("Both");

        return pickArmy;

    }
}
