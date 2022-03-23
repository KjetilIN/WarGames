package no.ntnu.wargames.frontend.gui.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import no.ntnu.wargames.backend.file.FileHandler;
import no.ntnu.wargames.backend.units.Army;
import no.ntnu.wargames.backend.units.Unit;
import no.ntnu.wargames.frontend.gui.dialog.DialogWindow;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SetUpPageController implements Initializable {
    /* Common fields */
    private Army army1;
    private Army army2;


    /*
    * First army methods/fields
    *
    * Contains the fields for the first army.
    *
    *
    *
    *
    * */
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
            setArmyFromFile(file,army1,pathArmy1,txtArmy1Name,observableListArmy1,iconCheckArmy1,tableViewArmy1);
        }
    }

    @FXML
    public void onEditNameArmy1(){
        String result = DialogWindow.openEditNameDialog();
        if(result != null){
            txtArmy1Name.setText(result);
            army1.setName(result);
        }else{
            DialogWindow.openWarningDialog("NO NAME FOUND");
        }
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
            setArmyFromFile(file,army2,pathArmy2,txtArmy2Name,observableListArmy2,iconCheckArmy2,tableViewArmy2);
        }
    }




    /*
    * COMMON METHODS FOR SETUP CONTROLLER
    *
    * Next section has common methods for both tableviews.
    * The method takes parameter from the javafx components and file.
    *
    *
    *
    * */

    public void setArmyFromFile(File file,
                                Army army,
                                TextField pathArmy,
                                Label txtArmyName,
                                ObservableList<Unit> observableList,
                                ImageView icon,
                                TableView<Unit> tabel){
        pathArmy.setText(file.getName());
        try{
            Army newArmy = FileHandler.getArmyFromFileCSV(file.toPath());
            army = new Army(newArmy.getName());
            army.addAll(newArmy.getAllUnits());
            txtArmyName.setText(army.getName().replaceAll(",",""));
            observableList.setAll(army.getAllUnits());
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.initStyle(StageStyle.DECORATED);
            alert.showAndWait();
        }
        icon.setVisible(true);
        tabel.refresh();

    }


    /* Init method for the setup controller*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*Army 1 setup*/
        army1 = new Army("Army 1", new ArrayList<>());
        pathArmy1.setText("NONE");
        iconCheckArmy1.setVisible(false);

        observableListArmy1 = FXCollections.observableList(army1.getAllUnits());
        tableViewArmy1.setItems(observableListArmy1);
        nameColumnArmy1.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumnArmy1.setCellValueFactory(new PropertyValueFactory<>("unitType"));
        healthColumnArmy1.setCellValueFactory(new PropertyValueFactory<>("health"));

        tableViewArmy1.refresh();

        /*Army 2 setup*/

        army2 = new Army("Army 2", new ArrayList<>());
        pathArmy2.setText("NONE");
        iconCheckArmy2.setVisible(false);

        observableListArmy2 = FXCollections.observableList(army2.getAllUnits());
        tableViewArmy2.setItems(observableListArmy2);
        nameColumnArmy2.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumnArmy2.setCellValueFactory(new PropertyValueFactory<>("unitType"));
        healthColumnArmy2.setCellValueFactory(new PropertyValueFactory<>("health"));

        tableViewArmy2.refresh();
    }
}
