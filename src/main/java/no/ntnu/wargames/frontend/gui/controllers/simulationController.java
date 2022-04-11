package no.ntnu.wargames.frontend.gui.controllers;




import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.util.Duration;
import no.ntnu.wargames.backend.Battle;
import no.ntnu.wargames.backend.units.Army;
import no.ntnu.wargames.backend.designPattern.Facade;
import no.ntnu.wargames.frontend.gui.canvasLogic.Grid;

import java.net.URL;
import java.util.ResourceBundle;


public class simulationController implements Initializable {


    /*Fields for the simulation*/
    private Battle battle;
    private Army army1;
    private Army army2;
    private Timeline timeline;
    private int simulationStep;
    private int delay;
    private Grid canvasDrawUtility;

    private XYChart.Series<String , Number> army1UnitsSeries;
    private XYChart.Series<String, Number> army2UnitsSeries;

    private XYChart.Series<String,Number> army1SumHealth;
    private XYChart.Series<String,Number> army2SumHealth;

    /*Info labels */
    @FXML
    private Label unitCountArmy1;

    @FXML
    private Label unitCountArmy2;

    @FXML
    private Label txtNameArmyOne;

    @FXML
    private Label txtNameArmyTwo;

    @FXML
    private Canvas backgroundCanvas;

    @FXML
    private Canvas unitCanvas;

    @FXML
    private AreaChart<String,Number> unitGraph;

    @FXML
    private AreaChart<String,Number> healthGraph;

    @FXML
    private TextArea battleLog;

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setupGraphsBeforeSim(){

        unitGraph.getData().clear();
        healthGraph.getData().clear();

        //defining units in each army s
        army1UnitsSeries = new XYChart.Series<>();
        army2UnitsSeries = new XYChart.Series<>();
        army1UnitsSeries.setName("Unit in " + this.army1.getName()+"'s army");
        army2UnitsSeries.setName("Units in " + this.army2.getName()+"'s army");

        army1SumHealth = new XYChart.Series<>();
        army2SumHealth = new XYChart.Series<>();
        army1SumHealth.setName("HP sum of " + this.army1.getName()+"'s army");
        army2SumHealth.setName("HP sum of " + this.army2.getName()+"'s army");

        //Set information of the chart
        this.unitGraph.getData().addAll(army1UnitsSeries,army2UnitsSeries);
        this.unitGraph.setCreateSymbols(false);
        this.healthGraph.getData().addAll(army1SumHealth,army2SumHealth);
        this.healthGraph.setCreateSymbols(false);



    }

    public void simStep(ActionEvent event){
        String textLog = "";
        if (army1.hasUnits() && army2.hasUnits()){
            textLog += battle.simulateStep() +"\n";
            unitCountArmy1.setText(String.valueOf(army1.getAllUnits().size()));
            unitCountArmy2.setText(String.valueOf(army2.getAllUnits().size()));
            army1UnitsSeries.getData().add(new XYChart.Data<>(String.valueOf(simulationStep),this.army1.getAllUnits().size()));
            army2UnitsSeries.getData().add(new XYChart.Data<>(String.valueOf(simulationStep),this.army2.getAllUnits().size()));

            army1SumHealth.getData().add(new XYChart.Data<>(String.valueOf(simulationStep),this.army1.getAllUnitHealthSum()));
            army2SumHealth.getData().add(new XYChart.Data<>(String.valueOf(simulationStep),this.army2.getAllUnitHealthSum()));
            battleLog.setText(battleLog.getText()+ textLog);
            simulationStep++;
            Grid grid = new Grid(this.unitCanvas,this.unitCanvas.getWidth(),this.unitCanvas.getHeight());
            grid.drawRandomBackground(this.unitCanvas,this.army1,this.army2);
        }else{
            timeline.stop();
        }

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

        setupGraphsBeforeSim();
        timeline = new Timeline(new KeyFrame(Duration.millis(200),this::simStep));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setOnFinished(finish -> notifyWinner()); // TODO: 10.04.2022 Fix this 
        timeline.play();



        //Remove later
        Image tile = new Image("C:\\Users\\kjeti\\OneDrive\\Skrivebord\\HillBackground.png");
        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();

        gc.drawImage(tile,0,0,backgroundCanvas.getWidth(),backgroundCanvas.getHeight());
        /*
        for(int i = 0; i< backgroundCanvas.getHeight()/16;i++){
            for(int k = 0; k<backgroundCanvas.getWidth()/16;k++){
                gc.drawImage(tile,i*16,k*16);
            }
        }
         */

    }
    @FXML
    public void onStick(){
        Grid grid = new Grid(unitCanvas,unitCanvas.getWidth(),unitCanvas.getHeight());
        grid.drawRandomBackground(this.unitCanvas,this.army1,this.army2);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Init the fields from facade
        this.battle = Facade.getInstance().getBattle();
        this.army1 = Facade.getInstance().getArmyOne();
        this.army2 = Facade.getInstance().getArmyTwo();

        //Setup canvas
        this.canvasDrawUtility = new Grid(this.backgroundCanvas,backgroundCanvas.getWidth(),backgroundCanvas.getHeight());

        //SetUp Information
        txtNameArmyOne.setText(this.army1.getName());
        txtNameArmyTwo.setText(this.army2.getName());

        this.backgroundCanvas = new Canvas();
        this.canvasDrawUtility.drawBackground(Facade.getInstance().getArmyOne().getAllUnits().get(0).getTerrain());



        //Counter
        this.simulationStep = 0;
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
