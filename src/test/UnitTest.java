import backend.units.CavalryUnit;
import backend.units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test of the abstract unit class with its method.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 09.02.22
 */

class UnitTest{

    private Unit unit;

    @BeforeEach
    void setupUnitTest(){
        //Assert
        unit = new CavalryUnit("name",40,30,10);
    }
    /**
     * Testing if the getName method works.
     */
    @Test
    void testGetName() {
        //Assert positive
        assertEquals("name", unit.getName());
        //Assert negative
        assertNotEquals("notName", unit.getName());
    }

    /**
     * Test if the getHealth method works.
     */

    @Test
    void testGetHealth() {
        //Assert positive
        assertEquals(40, unit.getHealth());
        //Assert negative
        assertNotEquals(45, unit.getHealth());
    }

    /**
     * Testing if the getAttack method works.
     */

    @Test
    void testGetAttack() {
        //Assert positive
        assertEquals(30, unit.getAttack());
        //Assert negative
        assertNotEquals(45, unit.getAttack());
    }

    /**
     * Testing if the getArmour method works.
     */

    @Test
    void testGetArmor() {
        //Assert positive
        assertEquals(10, unit.getArmor());
        //Assert negative
        assertNotEquals(45, unit.getArmor());
    }

    /**
     * Testing if the setHealth method works.
     */

    @Test
    void testSetHealth() {
        //Act
        unit.setHealth(50);
        //Assert positive
        assertEquals(50, unit.getHealth());
        //Assert negative
        assertNotEquals(40, unit.getAttack());
    }

    /**
     * Testing if the getAttackCount method works.
     * Should increment by one after an attack.
     */

    @Test
    void testGetAttackCount() {
        //Assert positive
        assertEquals(30, unit.getAttack());
        //Assert negative
        assertNotEquals(45, unit.getAttack());
    }

    /**
     * Testing if the toString method works.
     */

    @Test
    void testToString() {
        //Assert positive
        assertEquals("Name: name::HP: 40::Attack: 30", unit.toString());
        //Assert negative
        assertNotEquals("String", unit.toString());

    }

    /**
     * Testing if the attack method works. If the armor is better than the attack, no change to the health should be done.
     * Else, change the attack correctly using the formula shown.
     */

    @Test
    void testAttack() {
        //Assert
        Unit attacker = new CavalryUnit("unit1",100,10,10);
        Unit weakUnit = new CavalryUnit("unit2",100,10,5);
        Unit armorUnit = new CavalryUnit("unit2",200,10,100);
        /*
        ACT:

        (1) For the weak unit attacked:

        Since attack is bigger than armor and defence:
        weakUnit.health = weakUnit.health - (attacker.attack+attacker.attackBonus)+(weakUnit.armor+weakUnit.resistBonus)
        weakUnit.health = 100 -(10+6)+(5+3)
        weakUnit.health = 100 - 8
        weakUnit.health = 92

        (2) For the armored unit attacked:

        In this case the health should not change. The armor is absorbs the attack. So no change is health:
        armorUnit.health = 200;


         */
        attacker.attack(weakUnit); // case 1
        attacker.attack(armorUnit);

        //Assert positive
        assertEquals(92, weakUnit.getHealth());
        assertEquals(200,armorUnit.getHealth());

        //Assert negative
        assertNotEquals(100,weakUnit.getHealth());
        assertNotEquals(287,armorUnit.getHealth());
    }
}