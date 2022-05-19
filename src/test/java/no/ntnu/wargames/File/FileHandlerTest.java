package no.ntnu.wargames.File;


import no.ntnu.wargames.backend.designPattern.Facade;
import no.ntnu.wargames.backend.file.FileHandler;
import no.ntnu.wargames.backend.units.Army;
import no.ntnu.wargames.backend.units.RangedUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @Test
    @DisplayName("Test getArmyFromFileCSV() with file in correct format.")
    void testCorrectFile(){
        //Testing Negative, therefore no arrange or act needed.
        try{
            Army army = FileHandler.getArmyFromFileCSV(
                    Path.of("src\\test\\java\\no\\ntnu\\wargames\\File\\TEST_FILES\\correctFile.csv")
            );

            //Assert
            assertEquals(200,army.getAllUnits().size());
        }catch (Exception ex){
            fail();

        }

    }

    @Test
    @DisplayName("Test getArmyFromFileCSV() with file that does not exist.")
    void testWrongFile(){
        //Testing Negative, therefore no arrange or act needed.
        try{
            Army army = FileHandler.getArmyFromFileCSV(
                    Path.of("src\\aCsvThatDoesNotExist.csv")
            );
            fail();
        }catch (Exception ex){
            assertEquals("File not found",ex.getMessage());

        }

    }


    @Test
    @DisplayName("Test missing information for a unit is found.")
    void testMissingInformation(){
        try{
            Army army = FileHandler.getArmyFromFileCSV(
                    Path.of("src\\test\\java\\no\\ntnu\\wargames\\File\\TEST_FILES\\missingInfoFile.csv"));
            fail();
        }catch (Exception ex){
            assertEquals("Wrong file Format!",ex.getMessage());
        }
    }


    @Test
    @DisplayName("Test unknown unit type in File is found.")
    void testUnknownUnit(){
        try{
            Army army = FileHandler.getArmyFromFileCSV(
                    Path.of("src\\test\\java\\no\\ntnu\\wargames\\File\\TEST_FILES\\unknownUnit.csv"));
            fail();
        }catch (Exception ex){
            assertEquals("Unit type was not found!",ex.getMessage());
        }
    }

    @Test
    @DisplayName("Test save army without any units")
    void testSaveArmyHasUnits(){
        try {
            Army army = new Army("EmptyArmy");
            FileHandler.saveArmyToFile(army);
            fail();
        }catch (Exception ex){
            assertEquals("Army for save was empty.",ex.getMessage());
        }
    }
}
