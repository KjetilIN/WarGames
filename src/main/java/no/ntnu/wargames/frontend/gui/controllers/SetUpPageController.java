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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SetUpPageController implements Initializable {
    /* Common fields */
    private Army army1;
    private Army army2;


    /* First army methods/fields*/
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
        File file = getFile();
        if(file == null){
            /* Prompt user with Error alert. No feedback needed.*/
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No file chosen. Please add a csv file!");
            alert.initStyle(StageStyle.DECORATED);
            alert.showAndWait();
            pathArmy1.setText("NONE");
        }else{
            pathArmy1.setText(file.getName());
            try{
                Army newArmy = FileHandler.getArmyFromFileCSV(file.toPath());
                army1 = new Army(newArmy.getName());
                army1.addAll(newArmy.getAllUnits());
                observableListArmy1.setAll(army1.getAllUnits());
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.initStyle(StageStyle.DECORATED);
                alert.showAndWait();
            }
            iconCheckArmy1.setVisible(true);
            tableViewArmy1.refresh();
        }
    }

    @FXML
    public void onEditNameArmy1(){
        String result = openEditNameDialog();
        if(result != null){
            txtArmy1Name.setText(result);
            army1.setName(result);
        }else{
            Alert waring = new Alert(Alert.AlertType.WARNING);
            waring.setContentText("NO NAME WAS GIVEN");
            waring.showAndWait();
        }
    }
    /* Methods that are common */

    @FXML
    public File getFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files","*.csv")
        );
        return fileChooser.showOpenDialog(null);
    }

    public String openEditNameDialog(){
        TextInputDialog d = new TextInputDialog();
        d.setTitle("ARMY NAME");
        d.setContentText("Enter army name:");
        d.initStyle(StageStyle.UNDECORATED);

        Optional<String> userResponse = d.showAndWait();

        return userResponse.orElse(null);
    }


    /* Init method for the */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        army1 = new Army("Army 1", new ArrayList<>());
        pathArmy1.setText("NONE");
        iconCheckArmy1.setVisible(false);

        observableListArmy1 = FXCollections.observableList(army1.getAllUnits());
        tableViewArmy1.setItems(observableListArmy1);
        nameColumnArmy1.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumnArmy1.setCellValueFactory(new PropertyValueFactory<>("unitType"));
        healthColumnArmy1.setCellValueFactory(new PropertyValueFactory<>("health"));

        tableViewArmy1.refresh();
    }
}
