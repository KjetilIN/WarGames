import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test for army class.
 * Test all the methods in this class.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 09.02.2022
 */

class ArmyTest {

    /**
     * Testing of the Army object was created and getName works for each constructor.
     */

    @Test
    void getName() {
        //Arrange - two objects with both of the constructor.
        Army army = new Army("name");
        Army army1 = new Army("name", new ArrayList<>());

        //Assert positive
        assertEquals("name",army.getName());
        assertEquals("name",army1.getName());

        //Assert negative
        assertNotEquals("ok",army.getName());
        assertNotEquals("ok",army1.getName());
    }

    /**
     * Testing the method to add a unit to the army.
     */

    @Test
    void add() {
        //Arrange
        Army army = new Army("army");
        Army army1 = new Army("army2",new ArrayList<>());
        Unit unitToAdd = new RangedUnit("ranged",19);

        //Act
        army.add(unitToAdd);
        army1.add(unitToAdd);
        army1.add(unitToAdd);

        //Assert positive
        assertEquals(1,army.getAllUnits().size());
        assertEquals(2,army1.getAllUnits().size());

        //Assert Negative
        assertNotEquals(0,army.getAllUnits().size());
        assertNotEquals(1,army1.getAllUnits().size());



    }

    @Test
    void addAll() {
        // TODO: 09.02.2022  Finish the last test class 
    }

    @Test
    void remove() {
    }

    @Test
    void hasUnits() {
    }

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
}