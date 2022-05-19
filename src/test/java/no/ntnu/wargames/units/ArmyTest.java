package no.ntnu.wargames.units;

import no.ntnu.wargames.backend.units.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test for army class.
 * Test all the methods in this class.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 23.02.2022
 */

class ArmyTest {

    /**
     * Fields for the army unit.
     * defaultArmy: used single param constructor.
     * emptyArmy: used two param constructor.
     */

    private Army defaultArmy;
    private Army emptyArmy;
    private Unit unitToAdd;
    private Army armyWithFiveEach;

    @BeforeEach
    void setupArmyForTest(){
        defaultArmy = new Army("name");
        emptyArmy= new Army("name",new ArrayList<>());
        unitToAdd = new RangedUnit("ranged",19);
        armyWithFiveEach = new Army("ArmyWithFive");

        // TODO: 20.03.2022 Ask to put this in a for-loop (?)

        /* 5 infantry units */
        armyWithFiveEach.add(new InfantryUnit("Name",3));
        armyWithFiveEach.add(new InfantryUnit("Name",3));
        armyWithFiveEach.add(new InfantryUnit("Name",3));
        armyWithFiveEach.add(new InfantryUnit("Name",3));
        armyWithFiveEach.add(new InfantryUnit("Name",3));

        /* 5 cavalry units */
        armyWithFiveEach.add(new CavalryUnit("Name",3));
        armyWithFiveEach.add(new CavalryUnit("Name",3));
        armyWithFiveEach.add(new CavalryUnit("Name",3));
        armyWithFiveEach.add(new CavalryUnit("Name",3));
        armyWithFiveEach.add(new CavalryUnit("Name",3));

        /* 5 ranged units */
        armyWithFiveEach.add(new RangedUnit("Name",3));
        armyWithFiveEach.add(new RangedUnit("Name",3));
        armyWithFiveEach.add(new RangedUnit("Name",3));
        armyWithFiveEach.add(new RangedUnit("Name",3));
        armyWithFiveEach.add(new RangedUnit("Name",3));

        /* 5 commander units */
        armyWithFiveEach.add(new CommanderUnit("Name",3));
        armyWithFiveEach.add(new CommanderUnit("Name",3));
        armyWithFiveEach.add(new CommanderUnit("Name",3));
        armyWithFiveEach.add(new CommanderUnit("Name",3));
        armyWithFiveEach.add(new CommanderUnit("Name",3));

    }

    /**
     * Testing of the Army object was created and getName works for each constructor.
     */

    @Test
    @DisplayName("Test that constructor throws exception.")
    void testNegativeConstructors(){
        //Act contractor for army with null name
        try{
            Army army = new Army("");
            fail();
        }catch (IllegalArgumentException e){
            if(e.getMessage().equalsIgnoreCase("No name given")){
                assertTrue(true);
            }else{
                fail();
            }
        }

        try{
            List<Unit> units = new ArrayList<>();
            units.add(unitToAdd);
            Army army = new Army("",units);
            fail();
        }catch (IllegalArgumentException e){
            if(e.getMessage().equalsIgnoreCase("No name given")){
                assertTrue(true);
            }else{
                fail();
            }
        }




    }

    @Test
    @DisplayName("Test Copy-Constructor for Army Class")
    void testCopyConstrucktor(){
        //Arrange
        Army army = new Army("Cool");
        assertEquals("Cool", army.getName());

        //Act
        army = new Army(armyWithFiveEach);

        //Assert
        assertEquals(5*4, army.getAllUnits().size());
        assertEquals(3,army.getRandomUnit().getHealth());
        assertEquals("Name",army.getRandomUnit().getName());

    }

    @Test
    @DisplayName("Test getName() for Army Class.")
    void testGetName() {

        //Assert positive for both constructors
        assertEquals("name",defaultArmy.getName());
        assertEquals("name",emptyArmy.getName());

        //Assert negative
        assertNotEquals("ok",defaultArmy.getName());
        assertNotEquals("ok",emptyArmy.getName());
    }

    /**
     * Testing the method to add a unit to the army.
     */

    @Test
    @DisplayName("Test addUnit() to the Army class.")
    void testAddUnit() {
        //Act
        defaultArmy.add(unitToAdd);
        emptyArmy.add(unitToAdd);
        emptyArmy.add(unitToAdd);

        //Assert positive
        assertEquals(1,defaultArmy.getAllUnits().size());
        assertEquals(2,emptyArmy.getAllUnits().size());

        //Assert Negative
        assertNotEquals(0,defaultArmy.getAllUnits().size());
        assertNotEquals(1,emptyArmy.getAllUnits().size());



    }

    /**
     * Method to test if a list of units is added to the army.
     * Testing if both contractors add the list of units.
     */

    @Test
    @DisplayName("Test add list of units to army.")
    void testAddListOfUnits() {
        //Arrange
        List<Unit> unitsToAdd = new ArrayList<>();

        //Act - we add to units to the list and then add the list to the army.
        unitsToAdd.add(unitToAdd);
        unitsToAdd.add(unitToAdd);

        defaultArmy.addAll(unitsToAdd);
        emptyArmy.addAll(unitsToAdd);

        //Assert positive
        assertEquals(2,defaultArmy.getAllUnits().size());
        assertEquals(2,emptyArmy.getAllUnits().size());

        //Assert negative
        assertNotEquals(0,defaultArmy.getAllUnits().size());
        assertNotEquals(0,emptyArmy.getAllUnits().size());
    }

    /**
     * Method that checks if the units is removed from the army.
     */

    @Test
    @DisplayName("Test removeUnit() method.")
    void testRemoveUnit() {
        //Arrange
        defaultArmy.add(unitToAdd);
        defaultArmy.add(unitToAdd);


        //Act
        defaultArmy.remove(unitToAdd);

        //Assert positive
        assertEquals(1,defaultArmy.getAllUnits().size());

        //Assert negative
        assertNotEquals(2,defaultArmy.getAllUnits().size());


    }

    /**
     * Test if the method remove() throws the right exceptions.
     */

    @Test
    @DisplayName("Test remove() throws exception.")
    void testRemoveNegative(){
        try{
            emptyArmy.remove(null);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("No unit is given.", e.getMessage());
        }

        try{
            emptyArmy.remove(unitToAdd);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Empty army have no unit to remove", e.getMessage());
        }

    }

    /**
     * Method that test hasUnit() works.
     * Should return true when
     */

    @Test
    @DisplayName("Test boolean method that checks if there are units in the army.")
    void testHasUnitsInArmy() {
        //Arrange
        defaultArmy.add(unitToAdd);

        //Assert
        assertFalse(emptyArmy.hasUnits());
        assertTrue(defaultArmy.hasUnits());

    }

    /**
     * Methods that test if the getAllUnits method, returns the list of units from the army.
     */
    @Test
    @DisplayName("Test that getAllUnits returns list.")
    void getAllUnits() {
        //Act
        defaultArmy.add(unitToAdd);
        defaultArmy.add(unitToAdd);

        //Assert
        assertEquals(unitToAdd, defaultArmy.getAllUnits().get(0));
        assertEquals(new ArrayList<>(),emptyArmy.getAllUnits());
    }

    /**
     * Test if the method returns the correct information string for the army.
     */

    @Test
    @DisplayName("Test toString() method (Information sting for the army)")
    void testToString() {
        //Act
        emptyArmy.add(unitToAdd);
        //Assert
        assertEquals("Army{name='name', units size =0}",defaultArmy.toString());
        assertEquals("Army{name='name', units size =1}",emptyArmy.toString());
    }

    /**
     * Test of the equals-method is overwritten in army class, and works.
     */

    @Test
    @DisplayName("Test if equals() method is overwritten and works.")
    void testEquals() {
        //Act
        emptyArmy.add(unitToAdd);
        defaultArmy.add(unitToAdd);
        Army randomArmy = new Army("NotName");
        randomArmy.add(new RangedUnit("BowMan",83));
        //Assert
        assertTrue(defaultArmy.equals(emptyArmy));
        assertNotEquals(defaultArmy, randomArmy);
    }

    @Test
    @DisplayName("Test if getInfantryUnits works.")
    void testGetInfatryUnits() {
        assertEquals(0, emptyArmy.getInfantryUnits().size());
        assertEquals(5, armyWithFiveEach.getInfantryUnits().size());

    }

    @Test
    @DisplayName("Test if getCavalryUnits works.")
    void testGetCavalryUnit() {
        assertEquals(0, emptyArmy.getCavalryUnits().size());
        /* Should get all 5 cavalry units and 5 commander units, because of inheritance*/
        assertEquals(5, armyWithFiveEach.getCavalryUnits().size());

    }

    @Test
    @DisplayName("Test if getRangedUnits works.")
    void testGetRangedUnits() {
        assertEquals(0, emptyArmy.getRangedUnits().size());
        assertEquals(5, armyWithFiveEach.getRangedUnits().size());

    }

    @Test
    @DisplayName("Test if getCommanderUnits works.")
    void testGetCommanderUnits() {
        assertEquals(0, emptyArmy.getCommanderUnits().size());
        assertEquals(5, armyWithFiveEach.getCommanderUnits().size());

    }

    @Test
    @DisplayName("Test getHealthSum")
    void testHealthsum(){
        //Assert
        assertEquals(3*armyWithFiveEach.getAllUnits().size(),armyWithFiveEach.getAllUnitHealthSum());
    }




}