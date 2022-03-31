package no.ntnu.wargames.frontend.gui.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import no.ntnu.wargames.backend.Battle;
import no.ntnu.wargames.backend.units.Army;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class simulationController implements Initializable {

    /*Methods for the simulation*/
    private Battle battle;
    private Army army1;
    private Army army2;


    @FXML
    private GridPane world;

    @FXML
    private TextArea battleLog;

    public void sendArmyToSimulation(Army army1, Army army2){
        this.army1 = army1;
        this.army2 = army2;
        this.battle = new Battle(army1,army2);

    }

    public void logBattle(){

        String textLog = "";
        while(army1.hasUnits() && army2.hasUnits()){
            textLog += battle.simulateStep() +"\n";
            battleLog.setText(textLog);
        }

    }
    @FXML
    public void onSimulate(){
        logBattle();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
