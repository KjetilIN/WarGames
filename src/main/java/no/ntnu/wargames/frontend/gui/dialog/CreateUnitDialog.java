package no.ntnu.wargames.frontend.gui.dialog;


import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import no.ntnu.wargames.backend.units.Unit;
import no.ntnu.wargames.backend.units.UnitFactory;



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
                    //If all the requirements are correct, then we make a new unit.
                    UnitFactory factory = new UnitFactory();
                    return factory.createUnit(choiceBox.getValue(),name.getText(),health);
                }

                // TODO: 30.03.2022 find best way to not have duplicated code 
                
                //Opens waring dialog based on missing input
                if(!nameGiven && checkboxSelected && healthGiven){
                    DialogWindow.openWarningDialog("No name was given"
                            + "\n" + "Therefore now unit was added!");
                }else if(!checkboxSelected && nameGiven && healthGiven){
                    DialogWindow.openWarningDialog("No unit choice was made" +
                            "\n" + "Therefore now unit was added!");
                }else if (!healthGiven && checkboxSelected && nameGiven){
                    DialogWindow.openWarningDialog("No health was given."
                            + "\n" + "Therefore now unit was added!");

                }else {
                    DialogWindow.openWarningDialog("Please enter name and unit type...");
                }

            }
            
            return null;
        });

    }


}
