package no.ntnu.wargames.backend.file;

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
     * Private Hidden FileHandler Constructor.
     * Should never be used.
     * @throws IllegalArgumentException Throws exception if used.
     */
    private FileHandler(){
        throw new IllegalArgumentException("FileHandler");
    }


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
            // File does not exist
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
     * @param file file to save army to
     * @return returns the path of the file created (absolute path).
     * @throws IllegalArgumentException throws exception if army is empty or could not write to file.
     */

    public static Path saveArmyToFile(Army army, File file)throws IllegalArgumentException{
        /*Army given must have units and not be null, else throw exception*/
        if(army == null || !army.hasUnits()){throw new IllegalArgumentException("Army for save was empty.");}

        /*Write to file in correct format*/
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
            writer.write(army.getName()+",\n");
            for (Unit unit : army.getAllUnits()) {
                writer.write(unit.getUnitType()+","+unit.getName()+","+unit.getHealth()+"\n");
            }
        } catch (IOException e) {throw new IllegalArgumentException("Could not write no file");}

        return file.toPath().toAbsolutePath();
    }

}
