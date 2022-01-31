import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Unit class
 * Testing both positive and negative testing, in each method.
 */
class TestUnit {

    @Test
    void testGetName() {
        Unit unit = new Unit("name", 1, 1, 1);
        assertEquals("name", unit.getName());
        assertNotEquals("notname",unit.getName());
    }

    @Test
    void testGetHealth() {
        Unit unit = new Unit("name", 1, 1, 1);
        assertEquals(1,unit.getHealth());
        assertNotEquals(0,unit.getHealth());

    }

    @Test
    void testGetAttack() {
        Unit unit = new Unit("name", 1, 1, 1);
        assertEquals(1,unit.getAttack());
        assertNotEquals(0,unit.getAttack());
    }

    @Test
    void getArmor() {
        Unit unit = new Unit("name", 1, 1, 1);
        assertEquals(1,unit.getArmor());
        assertNotEquals(0,unit.getArmor());
    }

    @Test
    void setHealth() {
        Unit unit = new Unit("name", 1, 1, 1);
        assertEquals(1,unit.getHealth()); //testing before the change
        unit.setHealth(100);// changing health then testing
        assertEquals(100,unit.getHealth());
        assertNotEquals(1,unit.getHealth()); //health should be 100, not 1 (test if change is happened)
    }

    @Test
    void testToString() {
        Unit unit = new Unit("name", 1, 1, 1);
        assertEquals("Name: name::HP: 1::Attack: 1",unit.toString());
        assertNotEquals("Name: name::HP: 4::Attack: 3",unit.toString());
    }
}