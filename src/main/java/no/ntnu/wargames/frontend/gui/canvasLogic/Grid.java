package no.ntnu.wargames.frontend.gui.canvasLogic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Grid {

    private GraphicsContext gc;

    private static int HIGHT = 16;
    private static int WIDTH = 16;


    /*Images */

    private GraphicsContext drawBackground(String terrain){
        Image tileImage;

        switch (terrain.toUpperCase()){
            case "HILL":
                //image hill
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



}
