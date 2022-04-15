package no.ntnu.wargames;

import no.ntnu.wargames.backend.units.RangedUnit;
import no.ntnu.wargames.backend.units.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test the ranged unit and the methods.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 23.02.22
 */

class RangedUnitTest{
    /**
     * Method to test the constructor with only two parameters.
     * Attack and armor should be assigned static values.
     */

    @Test
    @DisplayName("Test that ranged default attack value is correct.")
    void testDefaultAttackArmor(){
        /*
        ATTACK = 15
        ARMOR = 8
         */

        //Arrange
        Unit unit = new RangedUnit("name",10);
        //Assert Positive
        assertEquals(15,unit.getAttack());
        assertEquals(8,unit.getArmor());
        //Assert Negative
        assertNotEquals(10, unit.getAttack());
        assertNotEquals(10,unit.getArmor());
    }

    /**
     * Method to test the getAttackBonus for the ranged unit.
     * After an attack, the attack bonus does not change.
     *
     */

    @Test
    @DisplayName("Test if infantry attack bonus changes after attack.")
    void testGetAttackBonus() {
        //Arrange
        Unit unit = new RangedUnit("name",10);
        Unit unit2 = new RangedUnit("name2",10);
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
     * Method to test the getResistBonus for the ranged unit.
     * After an attack, the resist bonus should be lowered to 4.
     * After a second attack, the resist bonus should be lowered to 2, and it should stay like this after every attack.
     *
     */

    @Test
    @DisplayName("Test infantry resist bonus changes.")
    void testGetResistBonus() {
        //Arrange
        Unit unit = new RangedUnit("name",10);
        Unit unit2 = new RangedUnit("name2",10);
        //Assert positive
        assertEquals(6,unit.getResistBonus());
        //Assert negative
        assertNotEquals(0, unit.getResistBonus());

        //Act - We attack the unit once, and it should bonus should now change.
        unit2.attack(unit);

        //Assert Positive
        assertEquals(1, unit.getAttackCount());
        assertEquals(4,unit.getResistBonus());
        //Assert negative
        assertNotEquals(0,unit.getAttackCount());
        assertNotEquals(6,unit.getResistBonus());

        //Act - we attack again and the bonus go even lower - 2
        unit2.attack(unit);

        //Assert Positive
        assertEquals(2, unit.getAttackCount());
        assertEquals(2,unit.getResistBonus());
        //Assert negative
        assertNotEquals(1,unit.getAttackCount());
        assertNotEquals(4,unit.getResistBonus());
    }

    @Test
    @DisplayName("Test Terrain Bonus for the Ranged Unit")
    void testTerrainBonus(){
        //Arrange
        Unit hillUnit = new RangedUnit("Temp1",10);
        Unit forestUnit = new RangedUnit("Temp1",10);
        Unit noBonusUnit = new RangedUnit("Temp1",10);

        //Act
        hillUnit.setTerrain("hill");
        forestUnit.setTerrain("forest");
        noBonusUnit.setTerrain("plains");

        //Assert

        /* Hill bonus */
        assertEquals(3,hillUnit.getTerrainBonusAttackDefence().getKey());
        assertEquals(0,hillUnit.getTerrainBonusAttackDefence().getValue());

        assertEquals(3+3,hillUnit.getAttackBonus());
        assertEquals(6,hillUnit.getResistBonus());

        /* Forest bonus */
        assertEquals(-2,forestUnit.getTerrainBonusAttackDefence().getKey());
        assertEquals(0,forestUnit.getTerrainBonusAttackDefence().getValue());

        assertEquals(3-2,forestUnit.getAttackBonus());
        assertEquals(6,forestUnit.getResistBonus());

        /* No bonus bonus */
        assertEquals(0,noBonusUnit.getTerrainBonusAttackDefence().getKey());
        assertEquals(0,noBonusUnit.getTerrainBonusAttackDefence().getValue());

        assertEquals(3,noBonusUnit.getAttackBonus());
        assertEquals(6,noBonusUnit.getResistBonus());

    }
}