package no.ntnu.wargames.backend.file;

import no.ntnu.wargames.backend.units.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

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

        returnArmy.setName(lines.get(0));
        for(int i = 1; i< lines.size();i++){
            String [] words = lines.get(i).split(",");
            if(words.length < 3){throw new IllegalArgumentException("Wrong file Format!");}
            String type = words[0];
            String name = words[1];
            int health = Integer.parseInt(words[2]);
            Unit unitToAdd;
            switch (type){
                case "Infantry":
                    unitToAdd = new InfantryUnit(name,health);
                    break;
                case "Ranged":
                    unitToAdd = new RangedUnit(name,health);
                    break;
                case "Commander":
                    unitToAdd = new CommanderUnit(name,health);
                    break;
                case "Cavalry":
                    unitToAdd = new CavalryUnit(name,health);
                    break;
                default:
                    throw new IllegalArgumentException("Unit type not found");
            }
            returnArmy.add(unitToAdd);

        }
        return returnArmy;

    }
}
