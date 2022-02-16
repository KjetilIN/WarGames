import backend.units.*;
import backend.units.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfantryUnitTest {
    /**
     * Method to test the constructor with only two parameters.
     * Attack and armor should be assigned static values.
     */

    @Test
    void testDefaultAttackArmor(){
        /*
        ATTACK = 12
        ARMOR = 10
         */

        //Arrange
        Unit unit = new InfantryUnit("name",10);
        //Assert Positive
        assertEquals(12,unit.getAttack());
        assertEquals(10,unit.getArmor());
        //Assert Negative
        assertNotEquals(5, unit.getAttack());
        assertNotEquals(5,unit.getArmor());
    }

    /**
     * Method to test the getAttackBonus for the infantry unit.
     * After an attack, the attack bonus does not change.
     *
     */

    @Test
    void testGetAttackBonus() {
        //Arrange
        Unit unit = new InfantryUnit("name",10);
        Unit unit2 = new InfantryUnit("name2",10);
        //Assert positive
        assertEquals(3,unit.getAttackBonus());
        //Assert negative
        assertNotEquals(0, unit.getAttackBonus());

        //Act - We attack the unit once, but the bonus should not change
        unit2.attack(unit);

        //Assert Positive
        assertEquals(1, unit.getAttackCount());
        assertEquals(3,unit.getAttackBonus());
        //Assert negative
        assertNotEquals(0,unit.getAttackCount());
        assertNotEquals(6,unit.getAttackBonus());

    }

    /**
     * Method to test the getResistBonus for the infantry unit.
     * The
     *
     */

    @Test
    void testGetResistBonus() {
        //Arrange
        Unit unit = new InfantryUnit("name",10);
        Unit unit2 = new InfantryUnit("name2",10);
        //Assert positive
        assertEquals(1,unit.getResistBonus());
        //Assert negative
        assertNotEquals(0, unit.getResistBonus());

        //Act - We attack the unit once, and it should bonus should now change.
        unit2.attack(unit);

        //Assert Positive
        assertEquals(1, unit.getAttackCount());
        assertEquals(1,unit.getResistBonus());
        //Assert negative
        assertNotEquals(0,unit.getAttackCount());
        assertNotEquals(11,unit.getResistBonus());


    }
}