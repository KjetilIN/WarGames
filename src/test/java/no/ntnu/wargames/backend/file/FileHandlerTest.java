package no.ntnu.wargames.backend.file;


import no.ntnu.wargames.backend.file.FileHandler;
import no.ntnu.wargames.backend.units.Army;
import no.ntnu.wargames.backend.units.CavalryUnit;
import no.ntnu.wargames.backend.units.Unit;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    private File emptyFile;


    @BeforeEach
    void initFile(){
        emptyFile = new File("Test.cvs");

    }

    @AfterEach
    void deleteFiles(){
        emptyFile.delete();
    }

    @Test
    @DisplayName("Test write army to file")
    void testWriteArmy(){
        //Arrange
        Army army = new Army("Cool Name");
        army.add(new CavalryUnit("cav",20));

        //Act
        FileHandler.saveArmyToFile(army,emptyFile);

        ArrayList<String> lines = new ArrayList<>();
        try(BufferedReader reader = Files.newBufferedReader(emptyFile.toPath())) {
            String line;
            while((line = reader.readLine())!= null){
                lines.add(line);
            }
        } catch (IOException e) {
            // File does not exist
            fail();
        }

        //Assert
        Unit unit = army.getRandomUnit();
        String unitLine = unit.getUnitType()+","+unit.getName()+","+unit.getHealth();


        assertEquals(army.getName()+",",lines.get(0));
        assertEquals(unitLine,lines.get(1));


    }

    @Test
    @DisplayName("Test getArmyFromFileCSV() with file in correct format.")
    void testCorrectFile(){
        //Testing Negative, therefore no arrange or act needed.
        try{
            Army army = FileHandler.getArmyFromFileCSV(
                    Path.of("src\\test\\java\\no\\ntnu\\wargames\\backend\\file\\TEST_FILES\\correctFile.csv")
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
                    Path.of("src\\test\\java\\no\\ntnu\\wargames\\backend\\file\\TEST_FILES\\missingInfoFile.csv"));
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
                    Path.of("src\\test\\java\\no\\ntnu\\wargames\\backend\\file\\TEST_FILES\\unknownUnit.csv"));
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
            FileHandler.saveArmyToFile(army, emptyFile);
            fail();
        }catch (Exception ex){
            assertEquals("Army for save was empty.",ex.getMessage());
        }
    }
}
