package no.ntnu.wargames.frontend.gui.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import no.ntnu.wargames.backend.file.FileHandler;
import no.ntnu.wargames.backend.units.Army;
import no.ntnu.wargames.backend.units.Unit;
import no.ntnu.wargames.backend.designPattern.Facade;
import no.ntnu.wargames.frontend.gui.dialog.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * SetUp Page Controller.
 * Controls all content on setup page.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class SetUpPageController implements Initializable {
    /* Common fields */
    private Army army1;
    private Army army2;

    //Version of the software.
    private static final String VERSION = "1.0-SNAPSHOT";

    //Icons for correct and incorrect files.
    private static final Image CORRECT_FILE_ICON = new Image(
            Objects.requireNonNull(
                    SetUpPageController.class.getResourceAsStream(
                    "/no/ntnu/wargames/icon/check.png")));

    private static final Image INCORRECT_FILE_ICON = new Image(
            Objects.requireNonNull(
                    SetUpPageController.class.getResourceAsStream(
                            "/no/ntnu/wargames/icon/wrong.png")));


    //Main Page
    @FXML
    private BorderPane mainPage;


    /*
    *
    *  ARMY ONE:
    *  - FXML Fields
    *  - Methods only used to change army one values
    *  - Uses the Common method Below!
    *
    */
    @FXML
    private TextField pathArmy1;

    @FXML
    private Label txtArmy1Name;

    private ObservableList<Unit> observableListArmy1;
    @FXML
    private ImageView iconCheckArmy1;

    @FXML
    private TableView<Unit> tableViewArmy1;
    @FXML
    private TableColumn<Unit,String> nameColumnArmy1;
    @FXML
    private TableColumn<Unit,String> typeColumnArmy1;
    @FXML
    private TableColumn<Unit,String> healthColumnArmy1;

    //Add army from file
    @FXML
    public void onAddArmy1(){
        File file = FileHandler.getFile();
        if(file == null){
            /* Prompt user with Error alert. No feedback needed.*/
            DialogWindow.openWarningDialog("No file was found!");
            pathArmy1.setText("NONE");
            iconCheckArmy1.setVisible(false);
        }else{
            setArmyFromFile(file,this.army1,pathArmy1,txtArmy1Name,observableListArmy1,iconCheckArmy1,tableViewArmy1);
        }
    }

    //Edit army 1 name
    @FXML
    public void onEditNameArmy1(){
        String result = DialogWindow.openEditNameDialog();
        if(result != null){
            txtArmy1Name.setText(result);
            this.army1.setName(result);
            Facade.getInstance().getArmyOne().setName(txtArmy1Name.getText());
        }else{
            DialogWindow.openWarningDialog("NO NAME FOUND");
        }
    }

    //Add a unit to army 1
    @FXML
    public void onAddUnitArmy1(){
        addUnitToTable(tableViewArmy1,observableListArmy1);
    }

    /*
     *
     *  ARMY TWO:
     *  - FXML Fields
     *  - Methods only used to change army two values
     *  - Uses the Common method Below!
     *
     */

    @FXML
    private TableView<Unit> tableViewArmy2;
    @FXML
    private TableColumn<Unit,String> nameColumnArmy2;
    @FXML
    private TableColumn<Unit,String> typeColumnArmy2;
    @FXML
    private TableColumn<Unit,String> healthColumnArmy2;
    @FXML
    private TextField pathArmy2;
    @FXML
    private Label txtArmy2Name;
    @FXML
    private ObservableList<Unit> observableListArmy2;
    @FXML
    private ImageView iconCheckArmy2;

    //Add army from file
    @FXML
    public void onAddArmy2(){
        File file = FileHandler.getFile();
        if(file == null){
            /* Prompt user with Error alert. No feedback needed.*/
            DialogWindow.openWarningDialog("No file was found!");
            pathArmy2.setText("NONE");
            iconCheckArmy2.setVisible(false);
        }else{
            setArmyFromFile(file,this.army2,pathArmy2,txtArmy2Name,observableListArmy2,iconCheckArmy2,tableViewArmy2);
        }
    }

    //Edit name of army 2
    @FXML
    public void onEditNameArmy2(){
        String result = DialogWindow.openEditNameDialog();
        if(result != null){
            txtArmy2Name.setText(result);
            this.army2.setName(result);
            Facade.getInstance().getArmyTwo().setName(txtArmy2Name.getText());
        }else{
            DialogWindow.openWarningDialog("NO NAME FOUND");
        }
    }

    //Add unit in army 2
    @FXML
    public void onAddUnitArmy2(){
        addUnitToTable(tableViewArmy2,observableListArmy2);
    }


    /*
    * COMMON METHODS FOR SETUP CONTROLLER
    *
    * Next section has common methods for both tableviews.
    * The method takes parameter from the javafx components and file.
    *
    */


    /**
     * Method to set an army in a given tableview.
     *
     * @param file file of the army.
     * @param pathArmy text field to output the file path of the army.
     * @param txtArmyName text field of the army name.
     * @param observableList the observable list to the table
     * @param icon the "check" icon for validating file.
     * @param table the table of the army
     */

    public void setArmyFromFile(File file,
                                Army army,
                                TextField pathArmy,
                                Label txtArmyName,
                                ObservableList<Unit> observableList,
                                ImageView icon,
                                TableView<Unit> table){
        pathArmy.setText(file.getName());
        try{
            army = FileHandler.getArmyFromFileCSV(file.toPath());
            txtArmyName.setText(army.getName().replace(",",""));
            observableList.setAll(army.getAllUnits());
            icon.setImage(CORRECT_FILE_ICON);
            icon.setVisible(true);
        }catch (Exception e){
            DialogWindow.openExceptionDialog(e);
            icon.setImage(INCORRECT_FILE_ICON);
            observableList.clear();

        }
        table.refresh();

    }



    /**
     * Add a new unit to the table.
     * Opens a new dialog pane and gets response.
     * Dialog pane handles wrong input.
     *
     * @param tableView table of the army.
     * @param observableList the observable list of the army
     */

    public void addUnitToTable(TableView<Unit> tableView,ObservableList<Unit> observableList){
        CreateUnitDialog dialog = new CreateUnitDialog();
        Optional<Unit> unit = dialog.showAndWait();

        if(unit.isPresent()){ //Check if we got
            Unit newUnit = unit.get();
            observableList.add(newUnit);
            tableView.refresh();
        }
    }

    /**
     * Method that switch the scene back to the setup page.
     *
     * @throws IOException throws exception if the page was not able to load.
     */
    @FXML
    public void onBack() throws IOException{
        /* Go back to load screen page (switching scene) */
        Stage currentPage = (Stage) mainPage.getScene().getWindow();
        currentPage.close();

        /* Clear all the army classes*/
        Facade.getInstance().resetAll();

        /* Load the setup page (switching scene) */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/no/ntnu/wargames/LoadScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.centerOnScreen(); // Loads the stage in the middle
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/no/ntnu/wargames/icon/logoIcon.PNG")));
        stage.getIcons().add(icon);
        stage.show();

    }

    /**
     * Gets the selected unit from the tables.
     * Goes through the list of table views.
     *
     * @return returns the selected unit, or null if no unit has been selected.
     */

    public Unit getSelectedUnit(){
        Unit result = null;
        if(tableViewArmy1.getSelectionModel().isEmpty()
                && !tableViewArmy2.getSelectionModel().isEmpty()){
            result = tableViewArmy2.getSelectionModel().getSelectedItem();
        }else if(!tableViewArmy1.getSelectionModel().isEmpty()
                && tableViewArmy2.getSelectionModel().isEmpty()){
            result = tableViewArmy1.getSelectionModel().getSelectedItem();
        }

        /*Clear selection history*/
        tableViewArmy1.getSelectionModel().clearSelection();
        tableViewArmy2.getSelectionModel().clearSelection();


        return result;
    }

    /**
     * Edit the selected unit.
     * If no unit is selected, then error dialog is shown.
     * Else it opens new edit dialog.
     */

    @FXML
    private void editUnit() {
        Unit selectedUnit = getSelectedUnit();

        if(selectedUnit != null){
            //Opens create dialog unit
            CreateUnitDialog createUnitDialog = new CreateUnitDialog(selectedUnit);
            createUnitDialog.showAndWait();

            //Refresh both tables
            tableViewArmy1.refresh();
            tableViewArmy2.refresh();
        }else{
            DialogWindow.openWarningDialog("No unit was selected!");
        }
    }

    /**
     * Method that saves army('s).
     * Asks user what army to save (both is also an options).
     * Creates an army file and saves it to the chosen path.
     */
    @FXML
    public void onSave(){
        SaveOptionDialog saveOptionDialog = new SaveOptionDialog();
        Optional<Integer> optionalInteger = saveOptionDialog.showAndWait();
        if(optionalInteger.isPresent()){
            int option = optionalInteger.get();
            String pathSavedTo = "The army(s) was saved here : \n \n";
            boolean correctFormat = true;
            switch (option) {
                case 0:
                    if (this.army1.hasUnits()) {
                        pathSavedTo += String.valueOf(FileHandler.saveArmyToFile(this.army1));
                    } else{
                        correctFormat = false;
                        DialogWindow.openWarningDialog(
                                "No Units in "+this.army1.getName()+"'s army");
                    }

                    break;
                case 1:
                    if (this.army2.hasUnits()) {
                        pathSavedTo += String.valueOf(FileHandler.saveArmyToFile(this.army2));
                    } else{
                        correctFormat = false;
                        DialogWindow.openWarningDialog(
                                "No Units in "+this.army1.getName()+"'s army");
                    }
                    break;
                case 2:
                    try{
                        pathSavedTo += FileHandler.saveArmyToFile(this.army1) +"\n";
                        pathSavedTo += FileHandler.saveArmyToFile(this.army2);
                    }catch (IllegalArgumentException e){
                        correctFormat = false;
                        DialogWindow.openExceptionDialog(e);
                    }

                    break;
                default:
                    correctFormat = false;
                    DialogWindow.openWarningDialog("Save option is not added!");
            }


            if(correctFormat){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Saved!");
                alert.setContentText(pathSavedTo);
                alert.setWidth(70); // Wide width to get more space
                alert.showAndWait();
            }

        }

        
    }

    /**
     * Deletes selected unit.
     */

    @FXML
    public void onDelete(){
        Unit selectedUnit = getSelectedUnit();
        if(selectedUnit != null){
            //Alert the user that a unit is about to be deleted
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Delete Unit");
            alert.setContentText("You are about to delete:"
                    + "\n" + selectedUnit.getName() + ","
                    + selectedUnit.getUnitType() + ","
                    + selectedUnit.getHealth()
                    + "\n" + "Are you sure?"
            );
            alert.showAndWait();

            if(alert.getResult() == ButtonType.OK){
                /* Removes unit and refreshes both tables */
                observableListArmy1.remove(selectedUnit);
                observableListArmy2.remove(selectedUnit);
                tableViewArmy1.refresh();
                tableViewArmy2.refresh();
            }
        }else{
            DialogWindow.openWarningDialog("No unit was selected!");
        }


    }

    /**
     * Saves both armies to Facade and then switch to simulation scene.
     *
     * @throws IOException throws exception if page not fond.
     */

    @FXML
    public void onGoToSimulationPane() throws IOException {
        if(this.army1.getAllUnits().size() < 2 || this.army2.getAllUnits().size() < 2){
            Alert alertNoUnits = new Alert(Alert.AlertType.ERROR);
            alertNoUnits.setHeaderText("UNIT(S) MISSING IN ARMY(S)");
            alertNoUnits.setContentText("One or more army need units.\n" +
                    "Please enter at least two unit for each army!");
            alertNoUnits.showAndWait();
        }else{
            SimulationSettingsDialog settingsDialog = new SimulationSettingsDialog();
            Optional<Integer> result = settingsDialog.showAndWait();
            if(result.isPresent()){
                // Set name of the army
                Facade.getInstance().getArmyOne().setName(txtArmy1Name.getText());
                Facade.getInstance().getArmyTwo().setName(txtArmy2Name.getText());
                Stage prevStage = (Stage)mainPage.getScene().getWindow();
                prevStage.close();

                //New scene opener
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/no/ntnu/wargames/simulation.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();

                SimulationController controller = loader.getController();
                controller.setDelay(result.get());

                //New style for the new stage
                stage.setTitle("WarGames");
                stage.initStyle(StageStyle.DECORATED);
                Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/no/ntnu/wargames/icon/logoIcon.PNG")));
                stage.getIcons().add(icon);
                stage.setResizable(false); //change for resizable
                stage.setScene(scene);
                stage.setFullScreen(false); //change value for fullscreen
                stage.show();
            }
        }


    }

    /**
     * Opens dialog window to display current version.
     */
    @FXML
    public void onVersion(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Version");
        alert.setContentText("Current version : " + VERSION);
        alert.showAndWait();
    }

    /**
     * Asks the user to exit the app.
     * Exit based on user response.
     */
    @FXML
    public void onClose(){
        Optional<ButtonType> result = DialogWindow.openExitDialog();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    /**
     * Method that set up the Table view.
     *
     * @param path path of the file as a text field.
     * @param icon imageview of the icon to validate correct file
     * @param observableList the tableviews list of units.
     * @param tableView table itself.
     * @param nameColumn column of the unit names.
     * @param typeColumn column of the unit types.
     * @param healthColumn column of unit healths (HP).
     */

    private void initTableview(TextField path,
                               ImageView icon,
                               ObservableList<Unit> observableList,
                               TableView<Unit> tableView,
                               TableColumn<Unit,String> nameColumn,
                               TableColumn<Unit,String> typeColumn,
                               TableColumn<Unit,String> healthColumn

    ) {
        path.setText("NONE");
        icon.setVisible(false);
        tableView.setItems(observableList);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("unitType"));
        healthColumn.setCellValueFactory(new PropertyValueFactory<>("health"));

        tableView.refresh();
    }

    @FXML
    public void onAddRandomArmy(){
        AddArmyDialog addArmyDialog = new AddArmyDialog();
        Optional<Army> result = addArmyDialog.showAndWait();

        if(result.isPresent()){
            Army army = result.get();

            //FIRST NUMBER IS OPTION
            try{
                int option = Integer.parseInt(army.getName().substring(0,1));
                army.setName(army.getName().substring(1));

                switch (option){
                    case 0:
                        army1.setName(army.getName());
                        observableListArmy1.setAll(army.getAllUnits());
                        txtArmy1Name.setText(army.getName());
                        break;
                    case 1:
                        army2.setName(army.getName());
                        observableListArmy2.setAll(army.getAllUnits());
                        txtArmy2Name.setText(army.getName());
                        break;
                    default:
                        army1.setName(army.getName());
                        observableListArmy1.setAll(army.getAllUnits());
                        txtArmy1Name.setText(army.getName());

                        army2.setName(army.getName());
                        observableListArmy2.setAll(army.getAllUnits());
                        txtArmy2Name.setText(army.getName());

                }

                tableViewArmy1.refresh();
                tableViewArmy2.refresh();
            }catch (NumberFormatException e){DialogWindow.openWarningDialog("Wrong format");}

        }
    }





    /* Init method for the setup controller*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*Army 1 setup*/
        this.army1 = Facade.getInstance().getArmyOne();
        txtArmy1Name.setText(Facade.getInstance().getArmyOne().getName());
        observableListArmy1 = FXCollections.observableList(this.army1.getAllUnits());
        initTableview(pathArmy1,
                iconCheckArmy1,
                observableListArmy1,
                tableViewArmy1,
                nameColumnArmy1,
                typeColumnArmy1,
                healthColumnArmy1);

        /*Army 2 setup*/
        this.army2 = Facade.getInstance().getArmyTwo();
        txtArmy2Name.setText(Facade.getInstance().getArmyTwo().getName());
        observableListArmy2 = FXCollections.observableList(this.army2.getAllUnits());
        initTableview(pathArmy2,
                iconCheckArmy2,
                observableListArmy2,
                tableViewArmy2,
                nameColumnArmy2,
                typeColumnArmy2,
                healthColumnArmy2);


        /*Table view listener
        *
        * Whenever a new selection is done in either table, the other selection is removed.
        * This is done in two methods. Both clearSelection() from the other table, when selected.
        */

        tableViewArmy1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tableViewArmy2.getSelectionModel().clearSelection();
            }
        });

        tableViewArmy2.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tableViewArmy1.getSelectionModel().clearSelection();
            }
        });
    }
}
