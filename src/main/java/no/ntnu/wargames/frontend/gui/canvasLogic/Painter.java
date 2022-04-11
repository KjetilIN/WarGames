package no.ntnu.wargames.frontend.gui.canvasLogic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import no.ntnu.wargames.backend.designPattern.RandomSingleton;
import no.ntnu.wargames.backend.units.Army;
import no.ntnu.wargames.backend.units.Unit;

import java.util.Random;

/**
 * Painter class that paints content on given canvas.
 */

public class Painter {

    /*16x16 pixel images for each unit type */
    private static final int HEIGHT = 16;
    private static final int WIDTH = 16;

    /*Size of the canvas*/
    private final double canvasWidth;
    private final double canvasHeight;

    /*Random */
    private final Random random;


    /*Map Images*/
    private final static Image HILL = new Image(String.valueOf(Painter.class.getResource("/no/ntnu/wargames/fxpixelart/world/HILL.png")));
    private final static Image PLAINS = new Image(String.valueOf(Painter.class.getResource("/no/ntnu/wargames/fxpixelart/world/PLAINS.png")));

    /**
     * Constructor for the Painter class.
     * Also initialize the random object, by using the {@link RandomSingleton} class.
     *
     * @param canvasWidth the width of the canvas.
     * @param canvasHeight the height of the canvas.
     */
    public Painter(double canvasWidth, double canvasHeight){
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.random = RandomSingleton.getInstance().getRandom();
    }

    /**
     * Method that gets the Image of a unit character.
     * The unit has a color, direction and is of a specific.
     *
     * @param color color of the unit (Army 1 == Red , Army 2 == Blue).
     * @param direction the direction of the unit.
     * @param unitType the unit type
     * @return returns an Image of the character with the given color,direction and type.
     */
    private Image getCharacter(String color, String direction,String unitType){
        return new Image(String.valueOf(Painter.class.getResource("/no/ntnu/wargames/fxpixelart/character/"+color+"/"+direction+"/"+unitType+".png")));
    }
    public void drawBackground(Canvas background, String terrain){
        GraphicsContext graphicsContext = background.getGraphicsContext2D();
        switch (terrain.toUpperCase()){
            case "HILL":
                graphicsContext.drawImage(HILL,0,0,canvasWidth,canvasHeight);
                break;
            case "FOREST":
                //image forest
                break;
            case "PLAINS":
                graphicsContext.drawImage(PLAINS,0,0,canvasWidth,canvasHeight);
                break;
            default:
                break;
        }
    }

    /**
     * Method that set all the characters on a canvas.
     * The two army's face each other, but as far away as possible.
     * Army One has the color red and is on the left side.
     * Army Two has the color red and is on the left side.
     *
     * @param foreGroundCanvas the canvas that the unit is painted on.
     * @param armyOne the first army to be placed on the left side.
     * @param armyTwo the second army to be placed on the right side.
     */
    public void drawUnitLineUp(Canvas foreGroundCanvas, Army armyOne, Army armyTwo){
        /* The graphics context for the canvas*/
        GraphicsContext graphicsContext = foreGroundCanvas.getGraphicsContext2D();

        /* Units army one*/
        int totalUnitsArmyOne = armyOne.getAllUnits().size();

        /*Init some variables for holding the position of the current character */
        int row = 0;
        int col = 0;
        while(totalUnitsArmyOne > 0){
            Unit unit = armyOne.getRandomUnit();

            /*Draw the random unit on the canvas*/
            graphicsContext.drawImage(getCharacter("red","right",
                    unit.getUnitType().toLowerCase()),(double) col*WIDTH,(double) row*HEIGHT);


            if(row > 25){
                /*Max row count is 25. If the correct character is on the last row, we start on the top of the next column */
                col += 1;
                row = 0;
            }else{
                row+=1;
            }
            totalUnitsArmyOne--;

        }

        /*Reset row and column value to start from the left*/
        row = 0;
        col = (int)foreGroundCanvas.getWidth()/16-1;

        int totalUnitsArmyTwo = armyTwo.getAllUnits().size();
        while(totalUnitsArmyTwo > 0){
            Unit unitTwo = armyTwo.getRandomUnit();
            graphicsContext.drawImage(getCharacter("blue","left",
                    unitTwo.getUnitType().toLowerCase()),(double) col*HEIGHT,(double) row*WIDTH);
            if(row > 25){
                col -= 1;
                row = 0;
            }else{
                row+=1;
            }
            totalUnitsArmyTwo--;

        }


    }

    /**
     * A private method that removes all the graphic painted on the canvas.
     * Replaces all graphics with a transparent rectangle.
     *
     * @param canvas the given canvas to be cleared.
     */

    private void clearCanvas(Canvas canvas){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    }

    /**
     * Method that paint the battlefield of the units.
     * Puts all units in random position.
     *
     * (NB! Could paint over a unit, but still represents a good indication of unit count.)
     *
     * @param canvas the given canvas of
     * @param armyOne the first army with red uniforms.
     * @param armyTwo the second army with blue uniforms.
     */

    public void drawRandomAttackFrame(Canvas canvas,Army armyOne, Army armyTwo){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        int armyOneUnits = armyOne.getAllUnits().size();
        int armyTwoUnits = armyTwo.getAllUnits().size();

        clearCanvas(canvas); //Clear before painting the canvas.

        /*Paints all the army-one units to the canvas*/
        while (armyOneUnits > 0){
            Unit randomUnit = armyOne.getRandomUnit();
            graphicsContext.drawImage(getCharacter("red",getRandomDirection(),randomUnit.getUnitType()),
                    (double)random.nextInt(30)*WIDTH,(double)random.nextInt(25)*HEIGHT);
            armyOneUnits--;
        }

        /*Paints all the army-two units to the canvas*/
        while (armyTwoUnits > 0){
            Unit randomUnitTwo = armyTwo.getRandomUnit();
            graphicsContext.drawImage(getCharacter("blue",getRandomDirection(),randomUnitTwo.getUnitType()),
                    (double) random.nextInt(30)*WIDTH,(double)random.nextInt(25)*HEIGHT);
            armyTwoUnits--;
        }
    }

    /**
     * Uses the {@link Random} class to get a random direction as string.
     * Equal chance of both direction.
     *
     * @return returns either left or right as String.
     */

    private String getRandomDirection(){
        if(this.random.nextInt(2) == 0){
            return "left";
        }
        return "right";
    }



}
