package no.ntnu.wargames.frontend.gui.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import no.ntnu.wargames.backend.designPattern.Facade;

public class SimulationSettingsDialog extends Dialog<Integer> {

    public SimulationSettingsDialog(){
        super();
        createContent();
    }

    private void createContent() {
        getDialogPane().setHeaderText("Set Terrain and Attack-Delay");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("Hill");
        choiceBox.getItems().add("Forest");
        choiceBox.getItems().add("Plains");

        TextField txtSimulationStep = new TextField();
        txtSimulationStep.setPromptText("Enter time between each attack....");

        // force the field to be numeric only
        txtSimulationStep.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtSimulationStep.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));
        grid.add(new Label("Terrain:"), 0, 0);
        grid.add(choiceBox, 1, 0);
        grid.add(new Label("Delay(milliseconds):"),0,2);
        grid.add(txtSimulationStep,1,2);
        getDialogPane().setContent(grid);


        getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);

        setResultConverter((ButtonType buttontype)->{
            if(buttontype == ButtonType.OK){
                if(!choiceBox.getSelectionModel().isEmpty() && txtSimulationStep.getText().length() > 0 && Integer.parseInt(txtSimulationStep.getText()) > 0){
                    Facade.getInstance().getBattle().setTerrainForALL(choiceBox.getSelectionModel().getSelectedItem());
                    return Integer.parseInt(txtSimulationStep.getText());
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
