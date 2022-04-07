package no.ntnu.wargames.backend.file;

import javafx.stage.FileChooser;
import no.ntnu.wargames.backend.units.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Class that handles file input and output.
 *
 * @author Kjetil Indrehus
 * @version 0.0.1
 */

public class FileHandler {

    /**
     * Method that returns the army from the .csv file.
     *
     * @param path path of the file as Path object.
     * @return returns a army object from the file.
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
            /* File does not exits. */
            throw new IllegalArgumentException("File not found");
        }


        /* Check if file has no content*/
        if(lines.isEmpty()){throw new IllegalArgumentException("No content in file");}

        returnArmy.setName(lines.get(0).replace(",",""));
        System.out.println(returnArmy.getName());
        for(int i = 1; i< lines.size();i++){
            String [] words = lines.get(i).split(",");
            if(words.length < 3){throw new IllegalArgumentException("Wrong file Format!");}
            String type = words[0];
            String name = words[1];
            int health = Integer.parseInt(words[2]);
            Unit unitToAdd = UnitFactory.createUnit(type,name,health);
            if(unitToAdd == null){throw new IllegalArgumentException("Unit type was not found!");}
            returnArmy.add(unitToAdd);

        }
        return returnArmy;

    }

    public static Path saveArmyToFile(Army army)throws IllegalArgumentException{
        if(army == null){throw new IllegalArgumentException("Army for save was empty.");}
        File file = new File(army.getName()+".csv");

        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
            writer.write(army.getName()+",\n");
            for (Unit unit : army.getAllUnits()) {
                writer.write(unit.getUnitType()+","+unit.getName()+","+unit.getHealth()+"\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return file.toPath().toAbsolutePath();
    }


    /**
     * Opens a window to open the file.
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
