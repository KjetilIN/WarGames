package no.ntnu.wargames.frontend.gui.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import no.ntnu.wargames.backend.units.Army;

public class AddArmyDialog extends Dialog<Army> {

    private Army returnArmy;

    public AddArmyDialog(){
        super();
        createContent();
    }

    private void createContent() {
        /*Name of the army*/
        TextField armyName = new TextField();
        armyName.setPromptText("Enter army name...");

        /*Checkbox for choosing what army to add to*/
        ChoiceBox<String> pickArmy = new ChoiceBox<>();
        pickArmy.getItems().add("Army 1");
        pickArmy.getItems().add("Army 2");
        pickArmy.getItems().add("Both");

        /*Name of all units*/
        TextField unitName = new TextField();
        unitName.setPromptText("Name for each unit..");

        /*Spinner for unit count*/
        Spinner<Integer> rangedSpinner = new Spinner(0,50,0);
        Spinner<Integer> commanderSpinner = new Spinner(0,50,0);
        Spinner<Integer> infantrySpinner = new Spinner(0,50,0);
        Spinner<Integer> cavalrySpinner = new Spinner(0,50,0);



        /*Grid*/
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));
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
        getDialogPane().setContent(grid);

        /*Buttons*/
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);





    }
}
