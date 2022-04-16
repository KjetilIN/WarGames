package no.ntnu.wargames.frontend.gui.controllers;




import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import no.ntnu.wargames.backend.Battle;
import no.ntnu.wargames.backend.units.Army;
import no.ntnu.wargames.backend.designPattern.Facade;
import no.ntnu.wargames.frontend.gui.canvasLogic.Painter;

import java.net.URL;
import java.util.ResourceBundle;


public class SimulationController implements Initializable {


    /*Fields for the simulation*/
    private Battle battle;
    private Army army1;
    private Army army2;
    private Timeline timeline;
    private int simulationStep;
    private int delay;
    private boolean isPlaying;
    private Painter canvasDrawUtility;


    /*Static Images*/
    private static final Image PAUSE = new Image(String.valueOf(SimulationController.class.getResource("/no/ntnu/wargames/icon/pause.png")));
    private static final Image PLAY = new Image(String.valueOf(SimulationController.class.getResource("/no/ntnu/wargames/icon/play-button.png")));


    private XYChart.Series<String , Number> army1UnitsSeries;
    private XYChart.Series<String, Number> army2UnitsSeries;

    private XYChart.Series<String,Number> army1SumHealth;
    private XYChart.Series<String,Number> army2SumHealth;

    /*Images */
    @FXML
    private ImageView buttonIconPause;


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
    private Label txtWinner;

    @FXML
    private Label txtTerrain;

    /*Canvas*/

    @FXML
    private Canvas backgroundCanvas;

    @FXML
    private Canvas unitCanvas;

    /* Charts */
    @FXML
    private AreaChart<String,Number> unitGraph;

    @FXML
    private AreaChart<String,Number> healthGraph;

    /*Input fields */
    @FXML
    private TextArea battleLog;

    /*Buttons */
    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonPausePlay;

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay(){
        return this.delay;
    }

    private void setupGraphsBeforeSim(){

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

    private void simStep(ActionEvent event){
        String textLog = "";
        if (army1.hasUnits() && army2.hasUnits()){
            txtWinner.setText("......");
            textLog += battle.simulateStep() +"\n";
            unitCountArmy1.setText(String.valueOf(army1.getAllUnits().size()));
            unitCountArmy2.setText(String.valueOf(army2.getAllUnits().size()));
            army1UnitsSeries.getData().add(new XYChart.Data<>(String.valueOf(simulationStep),this.army1.getAllUnits().size()));
            army2UnitsSeries.getData().add(new XYChart.Data<>(String.valueOf(simulationStep),this.army2.getAllUnits().size()));

            army1SumHealth.getData().add(new XYChart.Data<>(String.valueOf(simulationStep),this.army1.getAllUnitHealthSum()));
            army2SumHealth.getData().add(new XYChart.Data<>(String.valueOf(simulationStep),this.army2.getAllUnitHealthSum()));
            battleLog.setText(battleLog.getText()+ textLog);
            simulationStep++;
            canvasDrawUtility.drawRandomAttackFrame(this.unitCanvas,this.army1,this.army2);
        }else{
            if(army1.hasUnits()){
                txtWinner.setText(this.army1.getName());
            }else{
                txtWinner.setText(this.army2.getName());
            }

            timeline.stop();
        }

    }

    @FXML
    public void onSimulate(){

        setupGraphsBeforeSim();
        buttonPausePlay.setDisable(false);
        buttonStart.setDisable(true);
        timeline = new Timeline(new KeyFrame(Duration.millis(getDelay()),this::simStep));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        isPlaying = true;


    }

    @FXML
    public void onPause(){
        if(isPlaying){
            timeline.pause();
            isPlaying = false;
            buttonIconPause.setImage(PLAY);
        }else{
            timeline.play();
            isPlaying = true;
            buttonIconPause.setImage(PAUSE);
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Init the fields from facade
        this.battle = Facade.getInstance().getBattle();
        this.army1 = Facade.getInstance().getArmyOne();
        this.army2 = Facade.getInstance().getArmyTwo();

        //Setup canvas
        this.canvasDrawUtility = new Painter(backgroundCanvas.getWidth(),backgroundCanvas.getHeight());
        this.canvasDrawUtility.drawUnitLineUp(this.unitCanvas,this.army1,this.army2);
        this.canvasDrawUtility.drawBackground(this.backgroundCanvas,this.army1.getRandomUnit().getTerrain());

        //SetUp Information
        txtNameArmyOne.setText(this.army1.getName());
        txtNameArmyTwo.setText(this.army2.getName());
        txtTerrain.setText(this.army1.getAllUnits().get(0).getTerrain());

        //Button setup
        buttonIconPause.setImage(PAUSE);
        buttonPausePlay.setDisable(true);

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
