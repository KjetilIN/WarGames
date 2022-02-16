import units.Army;
import units.RangedUnit;
import units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test for army class.
 * Test all the methods in this class.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 15.02.2022
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

    @BeforeEach
    void setupArmyForTest(){
        defaultArmy = new Army("name");
        emptyArmy= new Army("name",new ArrayList<>());
        unitToAdd = new RangedUnit("ranged",19);
    }

    /**
     * Testing of the Army object was created and getName works for each constructor.
     */

    @Test
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

    @Test
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

    @Test
    void testHasUnitsInArmy() {
        //Arrange
        defaultArmy.add(unitToAdd);

        //Assert
        assertFalse(emptyArmy.hasUnits());
        assertTrue(defaultArmy.hasUnits());

    }

    // TODO: 15.02.2022 Ask if you need to test this  
    /*
    @Test
    void getAllUnits() {
    }

    @Test
    void getRandomUnit() {
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
   */
}