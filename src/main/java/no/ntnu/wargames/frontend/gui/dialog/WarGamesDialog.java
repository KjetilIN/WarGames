package no.ntnu.wargames.frontend.gui.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import no.ntnu.wargames.backend.designPattern.Facade;
import java.util.List;
import java.util.Objects;

/**
 * Abstract class for creating a dialog.
 * Extends Dialog class.
 * Has common methods for all dialog panes.
 *
 * Main reason for implementation is to minimize duplication in code.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 * @param <T> The given generic type to be returned by the dialog.
 */
public abstract class WarGamesDialog<T> extends Dialog<T> {

    /**
     * Abstract class to add content to the dialog.
     */
    protected abstract void createContent();

    /**
     * Add icon to the dialog
     */
    public void updateIcon(){
        ((Stage) getDialogPane().getScene().getWindow()).getIcons().add(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(
                        "/no/ntnu/wargames/icon/logoIcon.PNG"))));
    }

    /**
     * Init a grid object with correct space.
     *
     * @return returns the Grid Pane initialized.
     */
    public GridPane getGrid(){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));
        return grid;
    }

    /**
     * Get a choice box with the given options.
     *
     * @param listOfChoices list of options to be added to the choice box.
     * @return returns the choice box with the given options.
     */
    public ChoiceBox<String> getChoiceBox(List<String> listOfChoices){
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        for(String choice : listOfChoices){
            choiceBox.getItems().add(choice);
        }
        return choiceBox;
    }

    /**
     * Makes the given text field numeric only.
     * All other input will be replaced with empty string.
     * Achieves this by adding a listeners to the text field.
     *
     * @param textField given text field to only receive numeric input.
     */

    public void setTextFieldNumeric(TextField textField){
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

    public ChoiceBox<String> getArmyChoiceBox(){
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
