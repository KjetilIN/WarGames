package no.ntnu.wargames.frontend.gui.controllers;



import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import javafx.util.Duration;
import no.ntnu.wargames.backend.Battle;
import no.ntnu.wargames.backend.units.Army;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class simulationController implements Initializable {

    /*Fields for the simulation*/
    private Battle battle;
    private Army army1;
    private Army army2;


    @FXML
    private AreaChart<String,Number> unitGraph;

    @FXML
    private AreaChart<String,Number> healthGraph;

    @FXML
    private TextArea battleLog;

    public void sendArmyToSimulation(Army army1, Army army2){
        this.army1 = army1;
        this.army2 = army2;
        this.battle = new Battle(army1,army2);

    }

    public void logBattle(){

        unitGraph.getData().clear();
        healthGraph.getData().clear();

        //defining units in each army s
        XYChart.Series<String , Number> army1UnitsSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> army2UnitsSeries = new XYChart.Series<>();
        army1UnitsSeries.setName("Unit in " + this.army1.getName()+"'s army");
        army2UnitsSeries.setName("Units in " + this.army2.getName()+"'s army");

        XYChart.Series<String,Number> army1SumHealth = new XYChart.Series<>();
        XYChart.Series<String,Number> army2SumHealth = new XYChart.Series<>();
        army1SumHealth.setName("HP sum of " + this.army1.getName()+"'s army");
        army2SumHealth.setName("HP sum of " + this.army2.getName()+"'s army");


        String textLog = "";
        int attackCount = 1;
        while(army1.hasUnits() && army2.hasUnits()){
            textLog += battle.simulateStep() +"\n";
            army1UnitsSeries.getData().add(new XYChart.Data<>(String.valueOf(attackCount),this.army1.getAllUnits().size()));
            army2UnitsSeries.getData().add(new XYChart.Data<>(String.valueOf(attackCount),this.army2.getAllUnits().size()));

            army1SumHealth.getData().add(new XYChart.Data<>(String.valueOf(attackCount),this.army1.getAllUnitHealthSum()));
            army2SumHealth.getData().add(new XYChart.Data<>(String.valueOf(attackCount),this.army2.getAllUnitHealthSum()));
            battleLog.setText(textLog);
            attackCount++;
        }

        //Set information of the chart
        this.unitGraph.getData().addAll(army1UnitsSeries,army2UnitsSeries);
        this.unitGraph.setCreateSymbols(false);
        this.healthGraph.getData().addAll(army1SumHealth,army2SumHealth);
        this.healthGraph.setCreateSymbols(false);





    }


    public void notifyWinner(){
        String winnerArmyName = "";
        if(this.army1.hasUnits()){
            winnerArmyName = this.army1.getName();
        }else{
            winnerArmyName = this.army2.getName();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Winner is "+winnerArmyName);
        alert.setHeaderText("WINNER!");
        alert.showAndWait();


    }

    @FXML
    public void onSimulate(){
        logBattle();
        //notifyWinner();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Setting style for unit graph
        this.unitGraph.setTitle("Units in army");
        this.unitGraph.getYAxis().setLabel("Unit(s)");
        this.unitGraph.getXAxis().setLabel("Attack(s)");
        this.unitGraph.getXAxis().setTickLabelsVisible(false);
        this.unitGraph.setCreateSymbols(false);

        //Setting style for health graph
        this.healthGraph.setTitle("Total HP");
        this.healthGraph.getYAxis().setLabel("Sum of HP in Army");
        this.healthGraph.getXAxis().setLabel("Attack(s)");
    }
}
