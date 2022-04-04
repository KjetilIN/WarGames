package no.ntnu.wargames.frontend.gui.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

    /*Fields for the simulation*/
    private Battle battle;
    private Army army1;
    private Army army2;



    @FXML
    private AreaChart<String,Number> unitGraph;


    @FXML
    private TextArea battleLog;

    public void sendArmyToSimulation(Army army1, Army army2){
        this.army1 = army1;
        this.army2 = army2;
        this.battle = new Battle(army1,army2);

    }

    public void logBattle(){

        unitGraph.getData().clear();

        //defining units in each army s
        XYChart.Series<String , Number> army1Units = new XYChart.Series<>();
        XYChart.Series<String, Number> army2Units = new XYChart.Series<>();
        army1Units.setName("Unit in " + this.army1.getName()+"'s army");
        army2Units.setName("Units in " + this.army2.getName()+"'s army");



        String textLog = "";
        int attackCount = 1;
        while(army1.hasUnits() && army2.hasUnits()){
            textLog += battle.simulateStep() +"\n";
            army1Units.getData().add(new XYChart.Data<>(String.valueOf(attackCount),this.army1.getAllUnits().size()));
            army2Units.getData().add(new XYChart.Data<>(String.valueOf(attackCount),this.army2.getAllUnits().size()));
            battleLog.setText(textLog);
            attackCount++;
        }

        //Set information of the chart
        this.unitGraph.getData().addAll(army1Units,army2Units);
        this.unitGraph.setCreateSymbols(false);
    }
    @FXML
    public void onSimulate(){
        logBattle();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.unitGraph.setTitle("Units in army");
        this.unitGraph.getYAxis().setLabel("Unit(s)");
        this.unitGraph.getXAxis().setLabel("Attack(s)");
        this.unitGraph.getXAxis().setTickLabelsVisible(false);
        this.unitGraph.setCreateSymbols(false);
    }
}
