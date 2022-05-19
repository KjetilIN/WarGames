package no.ntnu.wargames.backend.file;

import javafx.stage.FileChooser;
import no.ntnu.wargames.backend.designPattern.UnitFactory;
import no.ntnu.wargames.backend.units.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Class that handles file input and output.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class FileHandler {

    /**
     * Method that returns the army from the .csv file.
     *
     * @param path path of the file as Path object.
     * @return returns an army object from the file.
     * @throws IllegalArgumentException throws exception if anything is wrong.
     */
    public static Army getArmyFromFileCSV(Path path) throws IllegalArgumentException{
        Army returnArmy = new Army(" ",new ArrayList<>());
        ArrayList<String> lines = new ArrayList<>();
        try(BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while((line = reader.readLine())!= null){
                lines.add(line);
            }
        } catch (IOException e) {
            /* File does not exist. */
            throw new IllegalArgumentException("File not found");
        }


        /* Check if file has no content*/
        if(lines.isEmpty()){throw new IllegalArgumentException("No content in file");}

        returnArmy.setName(lines.get(0).replace(",",""));
        for(int i = 1; i< lines.size();i++){
            String [] words = lines.get(i).split(",");
            /*Each unit lines will have: type,name,health == 3 words. If a line doesn't, throw exception */
            if(words.length < 3){throw new IllegalArgumentException("Wrong file Format!");}
            String type = words[0];
            String name = words[1];
            int health = Integer.parseInt(words[2]);
            Unit unitToAdd = UnitFactory.createUnit(type,name,health);
            /*Unit factory returns null, if an unknown type has been given.*/
            if(unitToAdd == null){throw new IllegalArgumentException("Unit type was not found!");}
            returnArmy.add(unitToAdd);

        }
        return returnArmy;

    }

    /**
     * Creates a new file that holds army object.
     * File name is the same as the name of the army.
     * The file type is "csv" with "," between information.
     * Output file fits the input format.
     *
     * @param army the given army
     * @return returns the path of the file created (absolute path).
     * @throws IllegalArgumentException throws exception if army is empty or could not write to file.
     */

    public static Path saveArmyToFile(Army army)throws IllegalArgumentException{
        /*Army given must have units and not be null, else throw exception*/
        if(army == null || !army.hasUnits()){throw new IllegalArgumentException("Army for save was empty.");}

        /* Open a window for choosing where the army is stored */
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files","*.csv")
        );

        File file = chooser.showSaveDialog(null); //Create a new file

        /*Write to file in correct format*/
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
            writer.write(army.getName()+",\n");
            for (Unit unit : army.getAllUnits()) {
                writer.write(unit.getUnitType()+","+unit.getName()+","+unit.getHealth()+"\n");
            }
        } catch (IOException e) {throw new IllegalArgumentException("Could not write no file");}

        return file.toPath().toAbsolutePath();
    }


    /**
     * Opens a window to open the file.
     * Prompts the user to select a cvs file.
     * (Only "csv" files)
     *
     * @return returns a file object from the file-chooser window.
     */
    public static File getFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files","*.csv")
        );
        return fileChooser.showOpenDialog(null);
    }
}
