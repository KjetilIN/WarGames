package no.ntnu.wargames.frontend.gui.dialog.complexDialog;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import no.ntnu.wargames.backend.designPattern.Facade;
import no.ntnu.wargames.frontend.gui.dialog.WarGamesDialog;

import java.util.List;

/**
 * Dialog class for choosing settings for the simulation.
 * Used to return the delay time between each battle.
 * Also sets the terrain of the
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class SimulationSettingsDialog extends WarGamesDialog<Integer> {

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
    @Override
    public void createContent() {
        getDialogPane().setHeaderText("Set Terrain and Attack-Delay");

        ChoiceBox<String> choiceBox = getChoiceBox(List.of("Hill","Forest","Plains"));

        TextField txtDelay = new TextField();
        txtDelay.setPromptText("Enter time between each attack....");

        Button buttonShowDelayInfo = new Button();
        buttonShowDelayInfo.setText("Delay (?)");

        buttonShowDelayInfo.setOnAction(event -> {
            String recommendedLabel = new StringBuilder()
                    .append("Recommended delay: \n\n")
                    .append("4-10 Units:             >100 ms\n")
                    .append("10-150 Units:        100-150 ms\n")
                    .append("150-400 Units:      150-1000 ms\n")
                    .append("400-XXXX Units:           <1 s!\n\n")
                    .append("IF NOT RUNNABLE ADD MORE DELAY!").toString();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Delay Information");
            alert.setContentText(recommendedLabel);
            alert.showAndWait();
        });

        // force the field to be numeric only
        setTextFieldNumeric(txtDelay);

        GridPane grid = getGrid();
        grid.add(new Label("Terrain:"), 0, 0);
        grid.add(choiceBox, 1, 0);
        grid.add(new Label("Delay(milliseconds):"),0,2);
        grid.add(txtDelay,1,2);
        grid.add(buttonShowDelayInfo,1,3,2,2);
        getDialogPane().setContent(grid);

        //Add icon to the page
        updateIcon();

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
