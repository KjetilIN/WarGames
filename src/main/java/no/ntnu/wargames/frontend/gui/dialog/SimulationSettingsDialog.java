package no.ntnu.wargames.frontend.gui.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import no.ntnu.wargames.backend.designPattern.Facade;

/**
 * Dialog class for choosing settings for the simulation.
 * Used to return the delay time between each battle.
 * Also sets the terrain of the
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class SimulationSettingsDialog extends Dialog<Integer> {

    /**
     * Creates the window and adds the content for the window.
     */
    public SimulationSettingsDialog(){
        super();
        createContent();
    }

    /**
     * Add nodes to the window.
     * Also sets result converter.
     */

    private void createContent() {
        getDialogPane().setHeaderText("Set Terrain and Attack-Delay");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("Hill");
        choiceBox.getItems().add("Forest");
        choiceBox.getItems().add("Plains");

        TextField txtDelay = new TextField();
        txtDelay.setPromptText("Enter time between each attack....");

        Button buttonShowDelayInfo = new Button();
        buttonShowDelayInfo.setText("Delay (?)");

        buttonShowDelayInfo.setOnAction(event -> {
            String recommendedLabel = "Recommended delay: \n \n"
                    + "4-10 Units:             >100 ms\n"
                    + "10-100 Units:        100-150 ms\n"
                    + "100-400 Units:      150-1000 ms\n"
                    + "400-XXXX Units:           <1 s!\n \n"
                    + "IF NOT RUNNABLE ADD MORE DELAY!";

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Delay Information");
            alert.setContentText(recommendedLabel);
            alert.showAndWait();
        });

        // force the field to be numeric only
        DialogUtility.setTextFieldNumeric(txtDelay);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));
        grid.add(new Label("Terrain:"), 0, 0);
        grid.add(choiceBox, 1, 0);
        grid.add(new Label("Delay(milliseconds):"),0,2);
        grid.add(txtDelay,1,2);
        grid.add(buttonShowDelayInfo,1,3,2,2);
        getDialogPane().setContent(grid);


        getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);

        setResultConverter((ButtonType buttontype)->{
            if(buttontype == ButtonType.OK){
                if(!choiceBox.getSelectionModel().isEmpty()
                        && txtDelay.getText().length() > 0
                        && Integer.parseInt(txtDelay.getText()) > 0){

                    Facade.getInstance().getBattle().setTerrainForALL(choiceBox.getSelectionModel().getSelectedItem());
                    return Integer.parseInt(txtDelay.getText());
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Wrong input!");
                    alert.setContentText("Please enter terrain and delay time.");
                    alert.showAndWait();

                }
            }


            return null;
        });



    }

}
