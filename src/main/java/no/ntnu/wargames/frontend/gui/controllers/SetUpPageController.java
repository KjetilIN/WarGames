package no.ntnu.wargames.frontend.gui.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import no.ntnu.wargames.frontend.gui.dialog.CreateUnitDialog;
import no.ntnu.wargames.frontend.gui.dialog.DialogWindow;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SetUpPageController implements Initializable {
    /* Common fields */
    private Army army1;
    private Army army2;

    @FXML
    private BorderPane mainPage;

    /*
    * First army methods/fields
    *
    * Contains the fields for the first army.
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

    @FXML
    public void onEditNameArmy1(){
        String result = DialogWindow.openEditNameDialog();
        if(result != null){
            txtArmy1Name.setText(result);
            this.army1.setName(result);
        }else{
            DialogWindow.openWarningDialog("NO NAME FOUND");
        }
    }

    @FXML
    public void onAddUnitArmy1(){
        addUnitToTable(tableViewArmy1,observableListArmy1);
    }

    /*
    *  Second army fields
    *
    * All the fields and methods for the second army
    *
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

    @FXML
    public void onEditNameArmy2(){
        String result = DialogWindow.openEditNameDialog();
        if(result != null){
            txtArmy2Name.setText(result);
            this.army2.setName(result);
        }else{
            DialogWindow.openWarningDialog("NO NAME FOUND");
        }
    }

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
        }catch (Exception e){
            DialogWindow.openExceptionDialog(e);
        }
        icon.setVisible(true);
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

    public void addUnitToTable(TableView<Unit> tableView,ObservableList observableList){
        CreateUnitDialog dialog = new CreateUnitDialog();
        Optional<Unit> unit = dialog.showAndWait();

        if(unit.isPresent() && unit != null){
            Unit newUnit = unit.get();
            observableList.add(newUnit);
            tableView.refresh();
        }
    }

    @FXML
    public void onBack(){
        /* Go back to load screen page (switching scene) */
        System.exit(0);

        // TODO: 31.03.2022 Make scene switch(?)

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

        // TODO: 03.04.2022 error if both are selected.

        /*Clear selection history*/
        tableViewArmy1.getSelectionModel().clearSelection();
        tableViewArmy2.getSelectionModel().clearSelection();


        return result;
    }

    @FXML
    private void editUnit(ActionEvent actionEvent) {
        Unit selectedUnit = getSelectedUnit();

        if(selectedUnit != null){
            CreateUnitDialog createUnitDialog = new CreateUnitDialog(selectedUnit);
            createUnitDialog.showAndWait();

            //Refresh both tables
            tableViewArmy1.refresh();
            tableViewArmy2.refresh();
        }else{
            DialogWindow.openWarningDialog("No unit was selected!");
        }



    }

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

                // TODO: 31.03.2022 Find out why it doesn't remove from both
                observableListArmy1.remove(selectedUnit);
                observableListArmy2.remove(selectedUnit);
                tableViewArmy1.refresh();
                tableViewArmy2.refresh();
            }
        }else{
            DialogWindow.openWarningDialog("No unit was selected!");
        }


    }

    @FXML
    public void onGoToSimulationPane() throws IOException {
        Stage prevStage = (Stage)mainPage.getScene().getWindow();
        prevStage.close();

        //New scene opener
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/no/ntnu/wargames/simulation.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();

        // Set name of the army
        this.army1.setName(txtArmy1Name.getText());
        this.army2.setName(txtArmy2Name.getText());

        //Send information to the new frame.
        simulationController simulationController = loader.getController();
        simulationController.sendArmyToSimulation(this.army1,this.army2);

        //New style for the new stage
        stage.setTitle("WarGames");
        stage.initStyle(StageStyle.DECORATED);
        Image icon = new Image(getClass().getResourceAsStream("/no/ntnu/wargames/icon/logoIcon.PNG"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setFullScreen(false); //change value for fullscreen
        stage.show();
    }

    private void initTableview(TextField path,
                               ImageView icon,
                               ObservableList observableList,
                               TableView<Unit> tableView,
                               TableColumn<Unit,String> namecolumn,
                               TableColumn<Unit,String> typecolumn,
                               TableColumn<Unit,String> healthcolumn

    ) {
        path.setText("NONE");
        icon.setVisible(false);
        tableView.setItems(observableList);
        namecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typecolumn.setCellValueFactory(new PropertyValueFactory<>("unitType"));
        healthcolumn.setCellValueFactory(new PropertyValueFactory<>("health"));

        tableView.refresh();
    }





    /* Init method for the setup controller*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*Army 1 setup*/
        this.army1 = new Army("NO NAME GIVEN");
        observableListArmy1 = FXCollections.observableList(this.army1.getAllUnits());
        initTableview(pathArmy1,
                iconCheckArmy1,
                observableListArmy1,
                tableViewArmy1,
                nameColumnArmy1,
                typeColumnArmy1,
                healthColumnArmy1);

        /*Army 2 setup*/
        this.army2 = new Army("NO NAME GIVEN");
        observableListArmy2 = FXCollections.observableList(this.army2.getAllUnits());
        initTableview(pathArmy2,
                iconCheckArmy2,
                observableListArmy2,
                tableViewArmy2,
                nameColumnArmy2,
                typeColumnArmy2,
                healthColumnArmy2);

    }
}
