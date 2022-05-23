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
import javafx.stage.FileChooser;
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
import java.nio.file.Path;
import java.util.ArrayList;
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
    // Common fields
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
    @FXML private BorderPane mainPage;


    /*
    *
    *  ARMY ONE:
    *  - FXML Fields
    *  - Methods only used to change army one values
    *  - Uses the Common method Below!
    *
    */
    @FXML private TextField pathArmy1;
    @FXML private Label txtArmy1Name;
    @FXML private ImageView iconCheckArmy1;
    @FXML private TableView<Unit> tableViewArmy1;
    @FXML private TableColumn<Unit,String> nameColumnArmy1;
    @FXML private TableColumn<Unit,String> typeColumnArmy1;
    @FXML private TableColumn<Unit,String> healthColumnArmy1;

    private ObservableList<Unit> observableListArmy1;

    /**
     * Add from army 1 from file.
     * Button method.
     */

    @FXML
    public void onAddArmy1(){
        File file = getFile();
        if(file == null){
            /* Prompt user with Error alert. No feedback needed.*/
            DialogWindow.openWarningDialog("No file was found!");
            pathArmy1.setText("NONE");
            iconCheckArmy1.setVisible(false);
        }else{
            setArmyFromFile(file,pathArmy1,txtArmy1Name,observableListArmy1,iconCheckArmy1,tableViewArmy1);
        }
    }

    /**
     * Edit army 1 name
     * Button method.
     */
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

    /**
     * Add Unit to army 1
     * Button method.
     */
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

    @FXML private TableView<Unit> tableViewArmy2;
    @FXML private TableColumn<Unit,String> nameColumnArmy2;
    @FXML private TableColumn<Unit,String> typeColumnArmy2;
    @FXML private TableColumn<Unit,String> healthColumnArmy2;
    @FXML private TextField pathArmy2;
    @FXML private Label txtArmy2Name;
    @FXML private ObservableList<Unit> observableListArmy2;
    @FXML private ImageView iconCheckArmy2;

    /**
     * Add army 2 from file.
     * Button method.
     */

    @FXML
    public void onAddArmy2(){
        File file = getFile();
        if(file == null){
            /* Prompt user with Error alert. No feedback needed.*/
            DialogWindow.openWarningDialog("No file was found!");
            pathArmy2.setText("NONE");
            iconCheckArmy2.setVisible(false);
        }else{
            setArmyFromFile(file,pathArmy2,txtArmy2Name,observableListArmy2,iconCheckArmy2,tableViewArmy2);
        }
    }

    /**
     * Edit name of army 2.
     * Button method.
     */
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

    /**
     * Add unit in army 2.
     * Button method.
     */

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
     * Opens a window to open the file.
     * Prompts the user to select a cvs file.
     * (Only "csv" files)
     *
     * @return returns a file object from the file-chooser window.
     */
    private File getFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files","*.csv")
        );
        return fileChooser.showOpenDialog(null);
    }

    /**
     * Asks the user to select a folder and file name.
     *
     * @return returns file object created
     */
    private  File selectFile(){
        /* Open a window for choosing where the army is stored */
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files","*.csv")
        );
        return chooser.showSaveDialog(null); //Create a new file
    }


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
                                TextField pathArmy,
                                Label txtArmyName,
                                ObservableList<Unit> observableList,
                                ImageView icon,
                                TableView<Unit> table){
        pathArmy.setText(file.getName());
        try{
            Army army = FileHandler.getArmyFromFileCSV(file.toPath());
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/no/ntnu/wargames/loadScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.centerOnScreen(); // Loads the stage in the middle

        try{
            //Icon should be added correctly (Defencive programming)
            stage.getIcons().add(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(
                            "/no/ntnu/wargames/icon/logoIcon.PNG"))));
        }catch (NullPointerException ignored){ /* Program does not crash if icon not found */ }

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
     * Method used to save given armies by given options.
     * Option as integer.
     *  - 0 : Save army 1.
     *  - 1 : Save army 2.
     *  - 2 : Save both armies.
     * Adds the path of the files to the list of paths.
     *
     * @param option option received as integer
     * @param paths list of saved army files as path
     * @return returns true if successful
     */

    private Boolean saveByOption(int option, ArrayList<Path> paths){
        switch (option) {
            case 0:
                if (this.army1.hasUnits()) {
                    paths.add(FileHandler.saveArmyToFile(this.army1, selectFile()));
                    return true;
                } else{
                    DialogWindow.openWarningDialog(
                            "No Units in "+this.army1.getName()+"'s army");

                    return false;
                }
            case 1:
                if (this.army2.hasUnits()) {
                    paths.add(FileHandler.saveArmyToFile(this.army2,selectFile()));
                    return true;
                } else{
                    DialogWindow.openWarningDialog(
                            "No Units in "+this.army1.getName()+"'s army");
                    return false;
                }

            case 2:
                try{
                    paths.add(FileHandler.saveArmyToFile(this.army1,selectFile()));
                    paths.add(FileHandler.saveArmyToFile(this.army2,selectFile()));
                    return true;
                }catch (IllegalArgumentException e){
                    DialogWindow.openExceptionDialog(e);
                    return false;
                }
            default:
                DialogWindow.openWarningDialog("Save option is not added!");
                return false;
        }
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

            ArrayList<Path> paths = new ArrayList<>();
            boolean correctFormat = saveByOption(option,paths);
            if(correctFormat){
                SavedArmyDialog dialog = new SavedArmyDialog(paths);
                dialog.showAndWait();
            }

        }

        
    }

    /**
     * Opens an About Dialog to show user information about the page
     */

    @FXML
    public void onAbout(){
        String infoText = "Set Up Information: \n \n" +
                "H";
        // TODO: 23.05.2022 FIX 
        DialogWindow.openInformationDialog("ok","ok");
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

    @FXML
    public void onGuide(){

        StringBuilder builder = new StringBuilder();
        String longLine = "-----------------------------------------------------";
        String newLine = "\n";

        //Build chain for message
        String message =  builder.append(longLine).append(newLine)
                .append("           How Does The setup work!").append(newLine)
                .append(longLine).append(newLine).append(newLine)
                .append("Here you can setup each army for simulation!").append(newLine)
                .append("You need at least two units in each army").append(newLine)
                .append("The next thing you will asked to input is Delay").append(newLine)
                .append("Delay is the time between each attack.").append(newLine)
                .append("You will also be asked to enter a Terrain").append(newLine).append(newLine)
                .append(longLine).append(newLine)
                .append("           Terrain").append(newLine)
                .append(longLine).append(newLine).append(newLine)
                .append("Terrain affects how a unit are able to defend itself").append(newLine)
                .append("For example will a Ranged Unit struggle to fight in a forest").append(newLine)
                .append("However, a Infantry Unit has a lot of cover in a forest").append(newLine)
                .append("Therefore it is stronger in a forest").toString();

        AboutDialog dialog = new AboutDialog("Setup Information!", message);
        dialog.showAndWait();
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

    /**
     * Methods that gets called when the user clicks on add army.
     * The user gets the option to save both armies.
     */

    @FXML
    public void onAddRandomArmy(){
        AddArmyDialog addArmyDialog = new AddArmyDialog();
        Optional<Army> result = addArmyDialog.showAndWait();

        if(result.isPresent()){
            Army army = result.get();

            try{
                /*
                 * To make sure that the correct option is added.
                 * We make the first char of the army name be the option.
                 * We make sure to remove this later, so that the name does not start with integer.
                 * This is done because alerts does not allow returning multiple values.
                 * Could be refactored to use Pair Class.
                 */

                int option = Integer.parseInt(army.getName().substring(0,1));
                army.setName(army.getName().substring(1)); //set the correct name to the army

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
