package no.ntnu.wargames.frontend.gui.canvasLogic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import no.ntnu.wargames.backend.file.FileHandler;
import no.ntnu.wargames.backend.units.Army;

public class Grid {

    private GraphicsContext gc;

    private static int HEIGHT = 16;
    private static int WIDTH = 16;
    private final double canvasWidth;
    private final double canvasHeight;


    /*Images */
    private static Image HILL = new Image(String.valueOf(Grid.class.getResource("/no/ntnu/wargames/fxpixelart/world/HILL.png")));

    public Grid(double canvasWidth,double canvasHeight){
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
    }

    public Image getCharacter(String color, String direction,String unit){
        return new Image(String.valueOf(Grid.class.getResource("/no/ntnu/wargames/fxpixelart/character/"+color+"/"+direction+"/"+unit+".png")));
    }
    public GraphicsContext drawBackground(String terrain){
        Image tileImage;

        switch (terrain.toUpperCase()){
            case "HILL":
                gc.drawImage(HILL,0,0,canvasWidth,canvasHeight);
                break;
            case "FOREST":
                //image forest
                break;
            case "PLAINS":
                //image plains
                break;
            default:
                //default
                break;


        }

        return gc;
    }

    public GraphicsContext drawUnitLineUp(Canvas canvas, Army armyOne, Army armyTwo){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        /* Units */
        int rangedArmyOne = armyOne.getRangedUnits().size();
        int cavalryArmyOne = armyOne.getCavalryUnits().size();
        int commanderArmyOne = armyOne.getCommanderUnits().size();
        int infantryArmyOne = armyOne.getInfantryUnits().size();

        /*Row Column */
        for(int row = 0; row<canvas.getWidth()/16; row++){
            for(int col = 0; col<canvas.getHeight()/16;col++){
                gc.drawImage(getCharacter("red","right","ranged"),row*16,col*16);
            }
        }

        return gc;
    }



}
