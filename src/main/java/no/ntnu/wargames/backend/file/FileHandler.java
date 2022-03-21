package no.ntnu.wargames.backend.file;

import no.ntnu.wargames.backend.units.Army;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileHandler {

    public Army getArmyFromFileCSV(Path path) throws IllegalArgumentException{
        Army returnArmy = new Army("",new ArrayList<>());
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

        return returnArmy;

    }
}
