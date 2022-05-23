package no.ntnu.wargames.backend.units;

import no.ntnu.wargames.backend.Battle;
import no.ntnu.wargames.backend.units.Army;
import no.ntnu.wargames.backend.units.CavalryUnit;
import no.ntnu.wargames.backend.units.InfantryUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BattleTest {
    Army armyOne;
    Army armyTwo;
    Battle battle;

    @BeforeEach
    void setup(){
        /*Default army setup before each test*/
        armyOne = new Army("ArmyOne");
        armyTwo = new Army("ArmyTwo");

        for(int i = 0; i<5;i++){
            armyOne.add(new InfantryUnit("Name",300));
            armyTwo.add(new InfantryUnit("Name",3));
        }
        battle = new Battle(armyOne,armyTwo);
    }

    @Test
    @DisplayName("Test empty army given is handled")
    void testBattleConstuctor(){
        //Arrange
        Army army1 = new Army("Empty1");
        Army army2 = new Army("Empty2");
        army1.add(new CavalryUnit("Test",9,9,9));

        //Assert
        try{
            Battle battle = new Battle(null,army2);
            fail();
        }catch (Exception e){
            assertEquals("Army(s) given are empty.",e.getMessage());
        }
    }


    @Test
    @DisplayName("Test setTerrain for expected and unexpected inputs")
    void testSetAllTerrain(){
        //Arrange
        Army armyTemp1 = new Army("Name");
        Army armyTemp2 = new Army("Army");
        Army armyTemp3 = new Army("Army");
        Army armyTemp4 = new Army("Army");
        Army armyTemp5 = new Army("Army");
        Army armyTemp6 = new Army("Army");
        Army armyTemp7 = new Army("Army");
        Army armyTemp8 = new Army("Army");



        for(int i = 0; i<3; i++){
            armyTemp1.add(new InfantryUnit("Name",3));
            armyTemp2.add(new InfantryUnit("Name",3));
            armyTemp3.add(new InfantryUnit("Name",3));
            armyTemp4.add(new InfantryUnit("Name",3));
            armyTemp5.add(new InfantryUnit("Name",3));
            armyTemp6.add(new InfantryUnit("Name",3));
            armyTemp7.add(new InfantryUnit("Name",3));
            armyTemp8.add(new InfantryUnit("Name",3));
        }


        Battle tempBattleOne = new Battle(armyTemp1,armyTemp2);
        Battle tempBattleTwo = new Battle(armyTemp3,armyTemp4);
        Battle tempBattleThree = new Battle(armyTemp5,armyTemp6);
        Battle tempBattleFour = new Battle(armyTemp7,armyTemp8);



        //Act
        tempBattleOne.setTerrainForALL("forest");
        tempBattleTwo.setTerrainForALL("plains");
        tempBattleThree.setTerrainForALL("hill");
        tempBattleFour.setTerrainForALL("Unknown terrain");

        //Assert

        /* Forest - TempBattle1*/
        assertEquals("FOREST",armyTemp1.getAllUnits().get(0).getTerrain());
        assertEquals("FOREST",armyTemp1.getAllUnits().get(1).getTerrain());

        assertEquals("FOREST",armyTemp2.getAllUnits().get(0).getTerrain());
        assertEquals("FOREST",armyTemp2.getAllUnits().get(1).getTerrain());

        /*Plains - TempBattle2*/
        assertEquals("PLAINS",armyTemp3.getAllUnits().get(0).getTerrain());
        assertEquals("PLAINS",armyTemp3.getAllUnits().get(1).getTerrain());

        assertEquals("PLAINS",armyTemp4.getAllUnits().get(0).getTerrain());
        assertEquals("PLAINS",armyTemp4.getAllUnits().get(1).getTerrain());

        /*Hill - TempBattle3*/
        assertEquals("HILL",armyTemp5.getAllUnits().get(0).getTerrain());
        assertEquals("HILL",armyTemp5.getAllUnits().get(1).getTerrain());

        assertEquals("HILL",armyTemp6.getAllUnits().get(0).getTerrain());
        assertEquals("HILL",armyTemp6.getAllUnits().get(1).getTerrain());

        /* Unknown terrain type given */
        assertEquals("NONE",armyTemp7.getAllUnits().get(0).getTerrain());
        assertEquals("NONE",armyTemp7.getAllUnits().get(1).getTerrain());

        assertEquals("NONE",armyTemp8.getAllUnits().get(0).getTerrain());
        assertEquals("NONE",armyTemp8.getAllUnits().get(1).getTerrain());


    }

    @Test
    @DisplayName("Test simulate battle")
    void testSimulateBattle(){
        //Arrange
        Battle battle = new Battle(armyOne,armyTwo);
        assertEquals(armyOne,battle.simulateBattle());
    }

    @Test
    @DisplayName("Test simulate step method")
    void testSimStep(){
        try{
            Battle battle = new Battle(armyOne,armyTwo);
            int totalHealthBeforeAttack = armyOne.getAllUnitHealthSum() + armyTwo.getAllUnitHealthSum();
            battle.simulateStep();
            assertTrue(totalHealthBeforeAttack > armyOne.getAllUnitHealthSum() + armyTwo.getAllUnitHealthSum());
        }catch (Exception e){
            fail();
        }
    }


    @Test
    @DisplayName("Test toString() method for Battle class")
    void testToString(){
        //Arrange
        Battle battle = new Battle(armyOne,armyTwo);
        String expectedString = "Battle{armyOne=Army{name='ArmyOne', units size =5}, armyTwo=Army{name='ArmyTwo', units size =5}}";

        //Assert
        assertEquals(expectedString,battle.toString());
    }

}
