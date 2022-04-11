package no.ntnu.wargames.frontend.gui.canvasLogic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import no.ntnu.wargames.backend.designPattern.RandomSingleton;
import no.ntnu.wargames.backend.units.Army;

import java.util.Random;

public class Grid {

    private GraphicsContext gc;

    private static int HEIGHT = 16;
    private static int WIDTH = 16;
    private final double canvasWidth;
    private final double canvasHeight;
    private final Random random;


    /*Map Images*/
    private final static Image HILL = new Image(String.valueOf(Grid.class.getResource("/no/ntnu/wargames/fxpixelart/world/HILL.png")));
    private final static Image PLAINS = new Image(String.valueOf(Grid.class.getResource("/no/ntnu/wargames/fxpixelart/world/PLAINS.png")));
    private final static Image BLANK = new Image(String.valueOf(Grid.class.getResource("/no/ntnu/wargames/fxpixelart/character/unvisible.png")));



    public Grid(Canvas canvas,double canvasWidth,double canvasHeight){
        this.gc = canvas.getGraphicsContext2D();
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.random = RandomSingleton.getInstance().getRandom();
    }

    public Image getCharacter(String color, String direction,String unit){
        return new Image(String.valueOf(Grid.class.getResource("/no/ntnu/wargames/fxpixelart/character/"+color+"/"+direction+"/"+unit+".png")));
    }
    public void drawBackground(String terrain){
        switch (terrain.toUpperCase()){
            case "HILL":
                gc.drawImage(HILL,0,0,canvasWidth,canvasHeight);
                break;
            case "FOREST":
                //image forest
                break;
            case "PLAINS":
                //image plains
                gc.drawImage(PLAINS,0,0,canvasWidth,canvasHeight);
                break;
            default:
                break;


        }
    }

    public void drawUnitLineUp(Canvas foreGroundCanvas, Army armyOne, Army armyTwo){
        GraphicsContext gc = foreGroundCanvas.getGraphicsContext2D();
        /* Units army one*/
        int rangedArmyOne = armyOne.getRangedUnits().size();
        int cavalryArmyOne = armyOne.getCavalryUnits().size();
        int commanderArmyOne = armyOne.getCommanderUnits().size();
        int infantryArmyOne = armyOne.getInfantryUnits().size();

        int totalUnitsArmyOne = armyOne.getAllUnits().size();

        /*Unit army two*/

        int rangedArmyTwo = armyTwo.getRangedUnits().size();
        int cavalryArmyTwo = armyTwo.getCavalryUnits().size();
        int commanderArmyTwo = armyTwo.getCommanderUnits().size();
        int infantryArmyTwo = armyTwo.getInfantryUnits().size();

        int totalUnitsArmyTwo = armyTwo.getAllUnits().size();

    }

    public void clearCanvas(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        /*
        for(int i = 0; i<canvasHeight/16;i++){
            for(int k = 0; k<canvasWidth/16; k++){
                gc.drawImage(BLANK,k*WIDTH,i*HEIGHT);
            }
        }

         */

    }

    public void drawRandomBackground(Canvas canvas,Army armyOne, Army armyTwo){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int armyOneUnits = armyOne.getAllUnits().size();
        int armyTwoUnits = armyTwo.getAllUnits().size();

        clearCanvas(canvas); //before beginning.
        while (armyOneUnits > 0){
            gc.drawImage(getCharacter("red","right","infantry"),random.nextInt(30)*WIDTH,random.nextInt(25)*HEIGHT);
            armyOneUnits--;
        }
    }



}
