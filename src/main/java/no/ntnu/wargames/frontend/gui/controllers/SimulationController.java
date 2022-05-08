package no.ntnu.wargames.frontend.gui.controllers;




import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import no.ntnu.wargames.backend.Battle;
import no.ntnu.wargames.backend.units.Army;
import no.ntnu.wargames.backend.designPattern.Facade;
import no.ntnu.wargames.backend.units.Unit;
import no.ntnu.wargames.frontend.gui.canvasLogic.Painter;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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


    /*Static Fields*/
    private static final Image PAUSE = new Image(String.valueOf(SimulationController.class.getResource("/no/ntnu/wargames/icon/pause.png")));
    private static final Image PLAY = new Image(String.valueOf(SimulationController.class.getResource("/no/ntnu/wargames/icon/play-button.png")));





    /*

    Main Page Info


     */



    /* Pane */
    @FXML
    private BorderPane mainPage;

    /*Images */
    @FXML
    private ImageView buttonIconPause;

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

    /*Buttons */
    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonPausePlay;



    /*

    Army-Information-tab labels and information
        - Below are labels for the Army Info tab.


     */

    @FXML
    private Label txtTotalArmy1;

    @FXML
    private Label txtTotalArmy2;

    @FXML
    private Label txtCommanderArmy1;

    @FXML
    private Label txtCommanderArmy2;

    @FXML
    private Label txtCavalryArmy1;

    @FXML
    private Label txtCavalryArmy2;

    @FXML
    private Label txtInfantryArmy1;

    @FXML
    private Label txtInfantryArmy2;

    @FXML
    private Label txtRangedArmy1;

    @FXML
    private Label txtRangedArmy2;





    /*

    Graphs-Information-tab label and fields.
        - Next section contains fields and labels for the graph information tab.


     */


    private XYChart.Series<String , Number> army1UnitsSeries;
    private XYChart.Series<String, Number> army2UnitsSeries;

    private XYChart.Series<String,Number> army1SumHealth;
    private XYChart.Series<String,Number> army2SumHealth;


    /* Charts */
    @FXML
    private AreaChart<String,Number> unitGraph;

    @FXML
    private AreaChart<String,Number> healthGraph;




    /*

    Attack Log Information tab label and fields


     */

    /*Input fields */
    @FXML
    private TextArea battleLog;



    /*Methods for simulation delay*/

    public int getDelay(){
        return this.delay;
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }



    /*Methods used in by Buttons etc */


    /**
     * Method that sets up the graph before simulation.
     * First removes any data that is in the graph.
     * Then defining a new XY Dataset for all the graphs.
     * At last, it sets graph text.
     * Method is called once on start.
     *
     */
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

    /**
     * Updates all labels for Information tab.
     * Sets the correct value of Units of each type
     */

    private void updateUnitCountTable(){
        //Update Army info
        txtTotalArmy1.setText(String.valueOf(army1.getAllUnits().size()));
        txtTotalArmy2.setText(String.valueOf(army2.getAllUnits().size()));
        txtCommanderArmy1.setText(String.valueOf(army1.getCommanderUnits().size()));
        txtCommanderArmy2.setText(String.valueOf(army2.getCommanderUnits().size()));
        txtCavalryArmy1.setText(String.valueOf(army1.getCavalryUnits().size()));
        txtCavalryArmy2.setText(String.valueOf(army2.getCavalryUnits().size()));
        txtInfantryArmy1.setText(String.valueOf(army1.getInfantryUnits().size()));
        txtInfantryArmy2.setText(String.valueOf(army2.getInfantryUnits().size()));
        txtRangedArmy1.setText(String.valueOf(army1.getRangedUnits().size()));
        txtRangedArmy2.setText(String.valueOf(army2.getRangedUnits().size()));
    }

    /**
     * Method that is called for each attack in the simulation.
     * - Add attack to the battle log.
     * - Calls the attack method and remove the units.
     * - Add the data from the attack to the graphs.
     * - Uses Painter class to set new positions for the units.
     * - Shows the winner when the battle is over, and stops the timeline.
     *
     * @param event the event type when the button is called.
     */

    private void simStep(ActionEvent event){
        String textLog = "";
        if (this.army1.hasUnits() && this.army2.hasUnits()){
            //Add temp winneer
            txtWinner.setText("......");

            //Add to log
            textLog += battle.simulateStep() +"\n";
            battleLog.setText(battleLog.getText()+ textLog);

            //Update table
            updateUnitCountTable();

            //Update Graphs
            army1UnitsSeries.getData().add(new XYChart.Data<>(String.valueOf(simulationStep),this.army1.getAllUnits().size()));
            army2UnitsSeries.getData().add(new XYChart.Data<>(String.valueOf(simulationStep),this.army2.getAllUnits().size()));

            army1SumHealth.getData().add(new XYChart.Data<>(String.valueOf(simulationStep),this.army1.getAllUnitHealthSum()));
            army2SumHealth.getData().add(new XYChart.Data<>(String.valueOf(simulationStep),this.army2.getAllUnitHealthSum()));

            simulationStep++;
            canvasDrawUtility.drawRandomAttackFrame(this.unitCanvas,this.army1,this.army2);
        }else{
            if(army1.hasUnits()){
                txtWinner.setText(this.army1.getName());
            }else{
                txtWinner.setText(this.army2.getName());
            }
            timeline.stop();
            buttonPausePlay.setDisable(true);
        }

    }

    private void initBattle(){
        //Init the fields from facade
        this.army1 = new Army(Facade.getInstance().getArmyOne());
        this.army2 = new Army(Facade.getInstance().getArmyTwo());
        this.battle = new Battle(this.army1,this.army2);

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

        // Update table
        updateUnitCountTable();

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

    /* Button Events */

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
            buttonPausePlay.setText("Pause");
        }else{
            timeline.play();
            isPlaying = true;
            buttonIconPause.setImage(PAUSE);
            buttonPausePlay.setText("Play");
        }

    }

    @FXML
    public void onBackToSetUp(ActionEvent event) throws IOException {
        Stage prevStage = (Stage)mainPage.getScene().getWindow();
        prevStage.close();

        //New scene opener
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/no/ntnu/wargames/setUpPage.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();

        //New style for the new stage
        stage.setTitle("WarGames");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/no/ntnu/wargames/icon/logoIcon.PNG")));
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void onRefresh(){
        //Stops a ongoing simulation
        this.timeline.stop();

        //Reset for new battle
        this.canvasDrawUtility.clearCanvas(this.unitCanvas);
        initBattle();
        setupGraphsBeforeSim();
        buttonStart.setDisable(false);
        buttonPausePlay.setDisable(true);
        battleLog.setText("");

    }

    @FXML
    public void onGuide(){
        Alert guideInformation = new Alert(Alert.AlertType.INFORMATION);
        guideInformation.setHeaderText("GUIDE");
        guideInformation.setContentText("You are now on the simulation page! Somewhere we can:\n" +
                "- Start a simulation by press START SIMULATION in the bottom right corner.\n" +
                "- Pause/Play the simulation\n" +
                "- Re-Run a simulation by picking File -> Refresh\n"+
                "- Go back to editing a army by pressing Edit -> Edit army(s)\n"+
                "- Use the Menu-Bar above for extra options.\n");
        guideInformation.showAndWait();
    }

    @FXML
    public void onClose(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Exit");
        alert.setContentText("You are about to close the app.\n" +
                "Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            System.exit(0);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initBattle();
    }
}
