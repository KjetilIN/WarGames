package no.ntnu.wargames.frontend.gui.dialog;


import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import no.ntnu.wargames.backend.units.Unit;
import no.ntnu.wargames.backend.units.UnitFactory;

// TODO: 30.03.2022 Refactor and clean up


public class CreateUnitDialog extends Dialog<Unit> {



    private Mode mode;
    private Unit unit;

    private enum Mode{
        EDIT,NEW
    }

    public  CreateUnitDialog(){
        super();
        this.mode = Mode.NEW; //Set mode to new player
        createContent();
    }

    public CreateUnitDialog(Unit unit){
        /* Set up constructor for edit*/
        this.unit = unit;
        this.mode = Mode.EDIT;
        if(unit != null){
            createContent();
        }

    }

    public void createContent(){

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

        if((mode == Mode.EDIT)) {
            name.setText(this.unit.getName());
            healthTextField.setText(Integer.toString(this.unit.getHealth()));
            choiceBox.setValue(this.unit.getUnitType());

        }


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

                // TODO: 30.03.2022 find best way to not have duplicated code 
                if(this.mode == Mode.NEW){
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


            }
            
            return null;
        });

    }


}
